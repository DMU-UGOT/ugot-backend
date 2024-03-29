package com.dmuIt.domain.dto;

import com.dmuIt.domain.entity.Message;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDto {
    private long id;
    private Integer room;
    private String content;
    private String senderName;
    private String receiverName;
    private LocalDateTime created_at;

    public static MessageDto toDto(Message message) {
        return new MessageDto(
                message.getMessageId(),
                message.getRoom().getRoom(),
                message.getContent(),
                message.getSender().getNickname(),
                message.getReceiver().getNickname(),
                message.getCreatedAt()
        );

    }
}