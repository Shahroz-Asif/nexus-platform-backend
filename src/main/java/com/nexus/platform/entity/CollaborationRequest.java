package com.nexus.platform.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "collaboration_requests")
public class CollaborationRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "investor_id", nullable = false)
    private User investor;

    @ManyToOne
    @JoinColumn(name = "entrepreneur_id", nullable = false)
    private User entrepreneur;

    private String message;

    @Enumerated(EnumType.STRING)
    private CollaborationStatus status;
}
