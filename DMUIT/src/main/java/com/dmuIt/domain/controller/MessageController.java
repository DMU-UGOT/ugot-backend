package com.dmuIt.domain.controller;

import com.dmuIt.domain.dto.ApiResponseDto;
import com.dmuIt.domain.dto.MessageDto;
import com.dmuIt.domain.entity.Member;
import com.dmuIt.domain.repository.MemberRepository;
import com.dmuIt.domain.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;
    private final MemberRepository memberRepository;
    private final ApiResponseDto apiResponseDto;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<?> sendMessage(HttpServletRequest request, @RequestBody MessageDto messageDto) {
        Member currentMember = messageService.verifiedCurrentMember(request);
        messageDto.setSenderName(currentMember.getNickname());

        return apiResponseDto.success("쪽지를 보냈습니다.", messageService.write(messageDto));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/received")
    public ResponseEntity<?> getReceivedMessage(HttpServletRequest request) {
        Member currentMember = messageService.verifiedCurrentMember(request);

        return apiResponseDto.success("받은 쪽지를 불러왔습니다.", messageService.receivedMessage(currentMember));
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/received/{message-id}")
    public ResponseEntity<?> deleteReceivedMessage(HttpServletRequest request, @PathVariable("message-id") long messageId) {
        Member currentMember = messageService.verifiedCurrentMember(request);

        Object o = messageService.deleteMessageByReceiver(messageId, currentMember);
        return apiResponseDto.success("받은 쪽지인, " + messageId + "번 쪽지를 삭제했습니다.", o);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/sent")
    public ResponseEntity<?> getSentMessage(HttpServletRequest request) {
        Member currentMember = messageService.verifiedCurrentMember(request);

        return apiResponseDto.success("보낸 쪽지를 불러왔습니다..", messageService.sentMessage(currentMember));
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/sent/{message-id}")
    public ResponseEntity<?> deleteSentMessage(HttpServletRequest request, @PathVariable("message-id") long messageId) {
        Member currentMember = messageService.verifiedCurrentMember(request);

        return apiResponseDto.success("보낸 쪽지인, " + messageId + "번 쪽지를 삭제했습니다.", messageService.deleteMessageBySender(messageId, currentMember));
    }
}
