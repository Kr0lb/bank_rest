package com.example.bankcards.repository;

import com.example.bankcards.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CardRepository extends JpaRepository<Card, UUID>, JpaSpecificationExecutor<Card> {

    @Query("""
            select c.balance
            from Card c
            where c.id=:cardId
                and c.user.id=:userId""")
    Optional<BigDecimal> findCardBalanceByUser(UUID userId, UUID cardId);

}
