package com.dmuIt.domain.service;

import com.dmuIt.domain.dto.ApiResponseDto;
import com.dmuIt.domain.dto.MessageDto;
import com.dmuIt.domain.entity.Community;
import com.dmuIt.domain.entity.Member;
import com.dmuIt.domain.entity.Message;
import com.dmuIt.domain.entity.Room;
import com.dmuIt.domain.repository.CommunityRepository;
import com.dmuIt.domain.repository.MemberRepository;
import com.dmuIt.domain.repository.MessageRepository;
import com.dmuIt.domain.repository.RoomRepository;
import com.dmuIt.global.auth.jwt.JwtTokenProvider;
import com.dmuIt.global.exception.BusinessLogicException;
import com.dmuIt.global.exception.ExceptionCode;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RequiredArgsConstructor
@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final CommunityRepository communityRepository;
    private final RoomRepository roomRepository;
    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;


    //방 번호 구분
    public void roomChecking(Message message, MessageDto messageDto, Member receiver, Room room) {

        if (messageRepository.findRoomNum(receiver.getNickname(), messageDto.getSenderName()) == null) {
            //신규방 설정
            roomRepository.save(room);
            message.setRoom(room);
        } else {
            //기존방
            room.setRoom(messageRepository.findRoomNum(receiver.getNickname(), messageDto.getSenderName()).get(0));
            message.setRoom(room);
        }
    }


    @Transactional
    public Object write(MessageDto messageDto, Long comId) {

        Optional<Member> optionalMember2 = memberRepository.findByNickname(messageDto.getSenderName());
        Member sender = optionalMember2
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));

        Community community = communityRepository.findById(comId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.COMMUNITY_NOT_FOUND));

        Member receiver = community.getMember();
        Message message = new Message();
        Room room = new Room();
        if (sender.getNickname().equals(receiver.getNickname())) {
            return "자신에게는 쪽지를 보낼 수 없습니다.";
        } else {
            roomChecking(message, messageDto, receiver, room);

            message.setReceiver(receiver);
            message.setSender(sender);
            message.setSenderName(sender.getNickname());
            message.setReceiverName(receiver.getNickname());

            message.setContent(messageDto.getContent());
            messageRepository.save(message);

            return MessageDto.toDto(message);
        }
    }

    @Transactional
    public MessageDto writeInRoom(MessageDto messageDto, Integer roomId) {
        Optional<Member> optionalMember2 = memberRepository.findByNickname(messageDto.getSenderName());
        Member sender = optionalMember2
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
        List<Message> m = messageRepository.findByRoom(roomId);
        Member receiver;
        if (m.get(m.size() - 1).getSender() != sender) //연속으로 보내는 게 아니라면
        {
            receiver = m.get(m.size() - 1).getSender(); //이전의 보낸이가 받는이
        } else {
            receiver = m.get(m.size() - 1).getReceiver(); //받는이 유지
        }

        Message message = new Message();

        Room room = new Room();
        room.setRoom(roomId);
        message.setRoom(room);

        message.setReceiver(receiver);
        message.setSender(sender);
        message.setSenderName(sender.getNickname());
        message.setReceiverName(receiver.getNickname());

        message.setContent(messageDto.getContent());
        messageRepository.save(message);

        return MessageDto.toDto(message);
    }

    //쪽지함
    public List<MessageDto> receivedMessage(Member member) {
        List<Message> messages = messageRepository.findAllBySender(member.getNickname());
        List<MessageDto> messageDtos = new ArrayList<>();

        for (Message message : messages) {
            if (message.getSenderName().equals(member.getNickname()) //내가 보낸 쪽지를
                    && message.getSenderDelete() == 0) // 삭제하지 않았을 때
            {
                messageDtos.add(MessageDto.toDto(message));
            } else if (message.getReceiverName().equals(member.getNickname()) //내가 받은 쪽지를
                    && message.getReceiverDelete() == 0) { // 삭제하지 않았을 때
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
            if(message.getSenderName().equals(member.getNickname())
                    && message.getSenderDelete() == 0) // 삭제하지 않았을 때
            {
                messageDtos.add(MessageDto.toDto(message));
            }else if(message.getReceiverName().equals(member.getNickname()) //내가 받은 쪽지를
                    && message.getReceiverDelete() == 0) { // 삭제하지 않았을 때
                messageDtos.add(MessageDto.toDto(message));
            }
        }
        return messageDtos;
    }

    //방 삭제
    @Transactional
    public Object deleteRoom(Member member, Integer room){
        List<Message> messages = messageRepository.findByRoom(room);

        for (Message message : messages) {
            if(member.getNickname().equals(message.getSenderName())) // 내가 보낸 메세지 삭제
            {
                message.setSenderDelete(1);
            }else if(member.getNickname().equals(message.getReceiverName())) //내가 받은 메세지 삭제
            {
                message.setReceiverDelete(1);
            }
            if(message.isAllMessageDeleted(messages)){
                roomRepository.delete(message.getRoom());
            }
            if(message.isMessagePresent() == false){
                messageRepository.delete(message);
            }
        }


        return "방 나가기";
    }

    //편지 삭제
    @Transactional
    public Object deleteMessage(long id, Member member) {
        Message message = messageRepository.findById(id)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MESSAGE_NOT_FOUND));
        List<Message> messages = messageRepository.findByRoom(message.getRoom().getRoom());

        if(member.getNickname().equals(message.getSenderName())) // 내가 보낸 메세지 삭제
        {
            message.setSenderDelete(1);
        }else if(member.getNickname().equals(message.getReceiverName())) //내가 받은 메세지 삭제
        {
            message.setReceiverDelete(1);
        }
        if(message.isMessagePresent() == false){
            messageRepository.delete(message);
        }
        if(message.isAllMessageDeleted(messages)){
            roomRepository.delete(message.getRoom());
        }
        return "삭제 완료";
    }

    public Member verifiedCurrentMember(HttpServletRequest request) {
        String accessToken = request.getHeader("Authorization").substring(7);
        Claims claims = jwtTokenProvider.parseClaims(accessToken);
        String email = claims.getSubject();
        return memberService.findVerifiedMemberByEmail(email);
    }
}