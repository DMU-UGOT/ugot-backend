package com.dmuIt.domain.service;

import com.dmuIt.domain.dto.MessageDto;
import com.dmuIt.domain.entity.Member;
import com.dmuIt.domain.entity.Message;
import com.dmuIt.domain.repository.MemberRepository;
import com.dmuIt.domain.repository.MessageRepository;
import com.dmuIt.global.auth.jwt.JwtTokenProvider;
import com.dmuIt.global.exception.BusinessLogicException;
import com.dmuIt.global.exception.ExceptionCode;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@RequiredArgsConstructor
@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;
    
    //방 번호 구분
    public void roomChecking(Message message, MessageDto messageDto){
        if(messageRepository.findRoomNum(messageDto.getReceiverName(), messageDto.getSenderName()) == null){
            //신규방 설정
            //랜덤 방 숫자를 주되 중복되지 않게
            Integer a;
            do{
               a = (int) (Math.random()*100+1);
            }while(messageRepository.findRoomNum(messageDto.getReceiverName(), messageDto.getSenderName()) == a);
            message.setRoom(a);
        }else{
            //기존방!
            message.setRoom(messageRepository.findRoomNum(messageDto.getReceiverName(), messageDto.getSenderName()));
        }
    }


    @Transactional
    public MessageDto write(MessageDto messageDto) {
        Optional<Member> optionalMember1 = memberRepository.findByNickname(messageDto.getReceiverName());
        Member receiver = optionalMember1
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
        Optional<Member> optionalMember2 = memberRepository.findByNickname(messageDto.getSenderName());
        Member sender = optionalMember2
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));

        Message message = new Message();

        roomChecking(message,messageDto);

        message.setReceiver(receiver);
        message.setSender(sender);
        message.setSenderName(sender.getNickname());
        message.setReceiverName(receiver.getNickname());
        message.setTitle(messageDto.getTitle());
        message.setContent(messageDto.getContent());
        message.setDeletedByReceiver(false);
        message.setDeletedBySender(false);
        messageRepository.save(message);

        return MessageDto.toDto(message);
    }

    //쪽지함
    public List<MessageDto> receivedMessage(Member member){
        List<Message> messages = messageRepository.findAllBySender(member.getNickname());
        List<MessageDto> messageDtos = new ArrayList<>();
        for (Message message : messages) {
            // message 에서 받은 편지함에서 삭제하지 않았으면 보낼 때 추가해서 보내줌
            if (!message.isDeletedByReceiver()) {
                messageDtos.add(MessageDto.toDto(message));
            }
        }
        return messageDtos;
    }

    //쪽지 상세보기
    @Transactional(readOnly = true)
    public List<MessageDto> allMessage(Member member, Integer room) {
        List<Message> messages = messageRepository.findAllByRoom(member, room);
        List<MessageDto> messageDtos = new ArrayList<>();

        for (Message message : messages) {
            // message 에서 받은 편지함에서 삭제하지 않았으면 보낼 때 추가해서 보내줌
            if (!message.isDeletedByReceiver()) {
                messageDtos.add(MessageDto.toDto(message));
            }
        }
        return messageDtos;
    }



    // 받은 편지 삭제
    @Transactional
    public Object deleteMessageByReceiver(long id, Member member) {
        Message message = messageRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("메시지를 찾을 수 없습니다."));

        if (member == message.getReceiver()) {
            message.deletedByReceiver(); // 받은 사람에게 메시지 삭제
            if (message.isDeleted()) {
                // 받은 사람과 보낸 사람 모두 삭제했으면, 데이터베이스에서 삭제 요청
                messageRepository.delete(message);
                return "양쪽 모두 삭제";
            }
            return "한쪽만 삭제";
        } else {
            return new IllegalArgumentException("유저 정보가 일치하지 않습니다.");
        }
    }

    @Transactional
    public Object deleteMessageBySender(long id, Member member) {
        Message message = messageRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("메시지를 찾을 수 없습니다."));

        if (member == message.getSender()) {
            message.deletedBySender(); // 받은 사람에게 메시지 삭제
            if (message.isDeleted()) {
                // 받은 사람과 보낸 사람 모두 삭제했으면, 데이터베이스에서 삭제 요청
                messageRepository.delete(message);
                return "양쪽 모두 삭제";
            }
            return "한쪽만 삭제";
        } else {
            return new IllegalArgumentException("유저 정보가 일치하지 않습니다.");
        }
    }

    public Member verifiedCurrentMember(HttpServletRequest request) {
        String accessToken = request.getHeader("Authorization").substring(7);
        Claims claims = jwtTokenProvider.parseClaims(accessToken);
        String email = claims.getSubject();
        return memberService.findVerifiedMemberByEmail(email);
    }
}
