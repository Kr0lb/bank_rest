package com.example.bankcards.repository.specifications;

import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.CardStatus;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public class CardSpecification {

    public static Specification<Card> hasStatus(CardStatus status) {
        return (root, query, cb) -> status == null ?
                null : cb.equal(root.get("status"), status);
    }

    public static Specification<Card> likeNumber(String number) {
        return (root, query, cb) -> number == null ?
                null : cb.like(root.get("number"), number.concat("%"));
    }

    public static Specification<Card> hasUserId(UUID userId) {
        return (root, query, cb) -> userId == null ?
                null : cb.equal(root.get("user").get("id"), userId);
    }

}
