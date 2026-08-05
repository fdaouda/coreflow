package com.coreflow.order.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "orders")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "customer_id", nullable = false)
    private String customerId;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 30)
    private OrderStatus status;


    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    public void transitionTo(OrderStatus newStatus) {
        if (!isValidTransition(this.status, newStatus)) {
            throw new IllegalStateException("Transition de statut illégale : " + this.status + " -> " + newStatus);
        }
        this.status = newStatus;
    }

    private boolean isValidTransition(OrderStatus current, OrderStatus next) {
        return switch (current) {
            // PENDING peut passer en traitement ou être annulée/échouée immédiatement
            case PENDING -> next == OrderStatus.PROCESSING
                    || next == OrderStatus.FAILED
                    || next == OrderStatus.CANCELLED;

            // PROCESSING peut se terminer avec succès, échouer ou être annulée
            case PROCESSING -> next == OrderStatus.COMPLETED
                    || next == OrderStatus.FAILED
                    || next == OrderStatus.CANCELLED;

            // COMPLETED, FAILED et CANCELLED sont des états finaux
            case COMPLETED, FAILED, CANCELLED -> false;
        };
    }
}
