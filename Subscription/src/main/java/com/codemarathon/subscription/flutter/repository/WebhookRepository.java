package com.codemarathon.subscription.flutter.repository;

import com.codemarathon.subscription.flutter.entity.WebhookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WebhookRepository extends JpaRepository<WebhookEntity, Long> {
    @Query("SELECT w FROM WebhookEntity w WHERE w.data.customer.email = :email ORDER BY w.received_at DESC")
    List<WebhookEntity> findLatestWebhooksByEmail(@Param("email") String email);


    @Query("SELECT w FROM WebhookEntity w WHERE w.data.customer.email = :email ORDER BY w.received_at DESC")
    WebhookEntity findTopByEmailOrderByReceivedAtDesc(@Param("email") String email);
}
