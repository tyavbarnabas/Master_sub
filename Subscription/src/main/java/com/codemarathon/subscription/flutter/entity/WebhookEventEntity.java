package com.codemarathon.subscription.flutter.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "webhook_event_entities")
public class WebhookEventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String event;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "data_id")
    private WebhookData data;
    @Column(name = "received_At")
    private LocalDateTime receivedAt;


}
