package com.planchella.repositories.events;

import com.planchella.entities.StarEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface StarRepository extends JpaRepository<StarEntity, Long> {
    Optional<StarEntity> findByUserIdAndEventId(Long userId, Long eventId);

    @Transactional
    void deleteByUserIdAndEventId(Long userId, Long eventId);
}
