package com.planchella.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "stars", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "user_id", "event_id" })
})
@Getter
@Setter
@NoArgsConstructor
public class StarEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    private EventEntity event;

    public StarEntity(UserEntity user, EventEntity event) {
        this.user = user;
        this.event = event;
    }
}
