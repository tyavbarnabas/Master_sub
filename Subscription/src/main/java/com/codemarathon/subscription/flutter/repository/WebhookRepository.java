package com.codemarathon.subscription.flutter.repository;

import com.codemarathon.subscription.flutter.entity.WebhookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WebhookRepository extends JpaRepository<WebhookEntity, Long> {
}
