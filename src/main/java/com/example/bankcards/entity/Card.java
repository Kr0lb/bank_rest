package com.example.bankcards.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.Version;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@Table(name = "cards")
@AllArgsConstructor
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(length = 19)
    private String number;

    private LocalDate validityPeriod;

    @Enumerated(EnumType.ORDINAL)
    private CardStatus status;

    @Column(precision = 13, scale = 3)
    private BigDecimal balance;

    @Version
    private Integer version;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

}
