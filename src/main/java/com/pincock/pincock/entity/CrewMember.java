package com.pincock.pincock.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name="crew_member")
@NoArgsConstructor
@AllArgsConstructor
public class CrewMember {

    @EmbeddedId
    private CrewMemberId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("crewId")
    @JoinColumn(name = "crew_id")
    private Crew crew;

    @Enumerated(EnumType.STRING)
    @Column(name = "crew_status")
    private CrewStatus crewStatus;
}