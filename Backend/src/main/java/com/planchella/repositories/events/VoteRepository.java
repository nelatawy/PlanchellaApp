package com.planchella.repositories.events;

import com.planchella.entities.VoteEntity;
import com.planchella.enums.VoteType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<VoteEntity, Long> {
    Optional<VoteEntity> findByUserIdAndEventId(Long userId, Long eventId);

    @Transactional
    void deleteByUserIdAndEventId(Long userId, Long eventId);
}
