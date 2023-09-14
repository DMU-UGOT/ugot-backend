package com.dmuIt.domain.repository;

import com.dmuIt.domain.entity.Favorite;
import com.dmuIt.domain.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteRepository  extends JpaRepository<Favorite, Long> {
}
