package com.dmuIt.domain.repository;

import com.dmuIt.domain.entity.Conversation;
import com.dmuIt.domain.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {
    List<Conversation> findConversationByGroup(Group group);
}
