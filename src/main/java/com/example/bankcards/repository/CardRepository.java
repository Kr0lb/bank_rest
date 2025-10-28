package com.example.bankcards.repository;

import com.example.bankcards.entity.Card;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CardRepository extends JpaRepository<Card, UUID>, JpaSpecificationExecutor<Card> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
            select c
            from Card c
                where c.id = :cardId
                    and c.user.id = :userId""")
    Optional<Card> findByCardIdAndUserId(UUID cardId, UUID userId);

}
