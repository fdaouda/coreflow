package com.coreflow.order.repository;

import com.coreflow.order.domain.ProcessedEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProcessedEventRepository extends JpaRepository<ProcessedEvent, UUID> {
    // String correspond au type de @Id (eventId)
    boolean existsByEventId(String eventId);
}
