package com.dmuIt.domain.repository;

import com.dmuIt.domain.entity.Member;
import com.dmuIt.domain.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findAllByReceiver(Member member);
/*    @Query(
            value = "SELECT p FROM Message p WHERE p.title LIKE %:keyword% OR p.content LIKE %:keyword%",
    )*/
    List<Message> findBySender(String sender);
    List<Message> findAllBySender(Member member);
}
