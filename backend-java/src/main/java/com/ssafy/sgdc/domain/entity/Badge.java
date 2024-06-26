package com.ssafy.sgdc.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "badge")
public class Badge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto increment
    @Column(name = "badge_id")
    private long badgeId;

    @Column(name = "badge_name", length = 20)
    private String badgeName;

    @Column(name = "badge_detail", length = 200)
    private String badgeDetail;

    @Column(name = "badge_img", length = 200)
    private String badgeImg;
}
