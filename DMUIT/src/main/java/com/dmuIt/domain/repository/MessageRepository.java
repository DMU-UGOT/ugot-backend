package com.dmuIt.domain.repository;

import com.dmuIt.domain.dto.MessageDto;
import com.dmuIt.domain.entity.Member;
import com.dmuIt.domain.entity.Message;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    @Query(
            value = "SELECT * FROM message p WHERE p.room LIKE :room OR p.room LIKE :k", nativeQuery = true
    )
    List<Message> findAllByRoom(@Param("k") Member member, @Param("room") Integer room);
    @Query(
            value = "SELECT * FROM (\n" +
                    "         SELECT *,\n" +
                    "               ROW_NUMBER() OVER(PARTITION BY room ORDER BY created_at DESC) AS rn\n" +
                    "           FROM message\n" +
                    "       ) tt WHERE rn = 1", nativeQuery = true
    )
    List<Message> findAllBySender(Member member);

    @Query(
            value = "SELECT room FROM message p WHERE p.receiver_name LIKE :recvName AND p.sender_name Like :sendName", nativeQuery = true
    )
    Integer findRoomNum(@Param("recvName") String recvName, @Param("sendName") String sendName);
    List<Message> findByReceiver(String sender);
}
