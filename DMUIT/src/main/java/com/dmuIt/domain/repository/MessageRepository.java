package com.dmuIt.domain.repository;

import com.dmuIt.domain.entity.Member;
import com.dmuIt.domain.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findAllByReceiver(Member member);
    @Query(
            value = "SELECT * FROM (\n" +
                    "         SELECT *,\n" +
                    "               ROW_NUMBER() OVER(PARTITION BY receiver_name ORDER BY created_at DESC) AS rn\n" +
                    "           FROM message\n" +
                    "       ) tt WHERE rn = 1", nativeQuery = true

    )
    List<Message> findAllBySender(Member member);

    List<Message> findByReceiver(String sender);
}
