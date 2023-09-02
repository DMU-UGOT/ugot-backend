package com.dmuIt.domain.repository;

import com.dmuIt.domain.entity.Member;
import com.dmuIt.domain.entity.Study;
import com.dmuIt.domain.entity.StudyBookmark;
import org.springframework.data.jpa.repository.JpaRepository;


public interface StudyBookmarkRepository extends JpaRepository<StudyBookmark, Long> {
    StudyBookmark findByStudyAndMember(Study study, Member member);
    StudyBookmark findBookmarkByStudy(Study study);
}
