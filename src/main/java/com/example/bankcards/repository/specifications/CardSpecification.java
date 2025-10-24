package com.example.bankcards.repository.specifications;

import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.Status;
import org.springframework.data.jpa.domain.Specification;

public class CardSpecification {

    public static Specification<Card> hasStatus(Status status) {
        return (root, query, cb) -> status == null ?
                null : cb.equal(root.get("status"), status);
    }

    public static Specification<Card> likeNumber(String number) {
        return (root, query, cb) -> number == null ?
                null : cb.like(root.get("number"), number.concat("%"));
    }

}
