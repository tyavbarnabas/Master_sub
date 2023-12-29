package com.codemarathon.subscription.flutter.repository;

import com.codemarathon.subscription.flutter.entity.WebhookEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WebhookEventRepository extends JpaRepository<WebhookEventEntity, Long> {
}
