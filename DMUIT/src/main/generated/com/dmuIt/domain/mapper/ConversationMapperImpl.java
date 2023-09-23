package com.dmuIt.domain.mapper;

import com.dmuIt.domain.dto.ConversationDto;
import com.dmuIt.domain.entity.Conversation;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-09-24T01:42:04+0900",
    comments = "version: 1.5.1.Final, compiler: javac, environment: Java 11.0.15 (Oracle Corporation)"
)
@Component
public class ConversationMapperImpl implements ConversationMapper {

    @Override
    public Conversation conversationPostDtoToConversation(ConversationDto.Post postConversationDto) {
        if ( postConversationDto == null ) {
            return null;
        }

        Conversation conversation = new Conversation();

        conversation.setContent( postConversationDto.getContent() );

        return conversation;
    }

    @Override
    public List<ConversationDto.Response> conversationsToResponse(List<Conversation> conversations) {
        if ( conversations == null ) {
            return null;
        }

        List<ConversationDto.Response> list = new ArrayList<ConversationDto.Response>( conversations.size() );
        for ( Conversation conversation : conversations ) {
            list.add( conversationToResponse( conversation ) );
        }

        return list;
    }
}
