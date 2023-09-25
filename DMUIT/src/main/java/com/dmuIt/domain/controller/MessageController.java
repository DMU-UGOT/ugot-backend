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

import static io.lettuce.core.pubsub.PubSubOutput.Type.message;

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;
    private final ApiResponseDto apiResponseDto;


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/send")
    public ResponseEntity<?> sendMessage(HttpServletRequest request, @RequestBody MessageDto messageDto) {
        Member currentMember = messageService.verifiedCurrentMember(request);
        messageDto.setSenderName(currentMember.getNickname());
        return apiResponseDto.success("쪽지를 보냈습니다.", messageService.write(messageDto));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping()
    public ResponseEntity<?> getReceivedMessage(HttpServletRequest request) {
        Member currentMember = messageService.verifiedCurrentMember(request);
        return apiResponseDto.success("쪽지함입니다.", messageService.receivedMessage(currentMember));
    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{room}")
    public ResponseEntity<?> getAllReceivedMessage(HttpServletRequest request, @PathVariable("room") Integer room) {
        Member currentMember = messageService.verifiedCurrentMember(request);
        return apiResponseDto.success("쪽지를 불러왔습니다.", messageService.allMessage(currentMember, room));
    }


    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/delete/{message-id}")
    public ResponseEntity<?> deleteReceivedMessage(HttpServletRequest request, @PathVariable("message-id") long messageId) {
        Member currentMember = messageService.verifiedCurrentMember(request);

        return apiResponseDto.success( messageId + "번 쪽지를 삭제했습니다.", messageService.deleteMessage(messageId, currentMember));
    }



}
