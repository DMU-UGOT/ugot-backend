package com.dmuIt.domain.mapper;

import com.dmuIt.domain.dto.ConversationDto;
import com.dmuIt.domain.entity.Conversation;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ConversationMapper {
    Conversation conversationPostDtoToConversation(ConversationDto.Post postConversationDto);

    default ConversationDto.Response conversationToResponse(Conversation conversation) {
        if (conversation == null) {
            return null;
        } else {
            ConversationDto.Response response = new ConversationDto.Response();
            response.setConversationId(conversation.getConversationId());
            response.setNickname(conversation.getMember().getNickname());
            response.setContent(conversation.getContent());
            response.setCreatedAt(conversation.getCreatedAt());
            return response;
        }
    }
    List<ConversationDto.Response> conversationsToResponse(List<Conversation> conversations);
}
