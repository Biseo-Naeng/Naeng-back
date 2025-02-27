package com.naeng_biseo.naeng_biseo.domain.entities;

import com.naeng_biseo.naeng_biseo.domain.entities.compositedId.FollowerId;
import jakarta.persistence.*;

@Entity
@Table(name = "Followers")
public class Follower {

    @EmbeddedId
    private FollowerId id;

    @ManyToOne(fetch=FetchType.LAZY)
    @MapsId("followerId")
    @JoinColumn(name = "followerId")
    private User follower;

    @ManyToOne(fetch=FetchType.LAZY)
    @MapsId("followingId")
    @JoinColumn(name = "followingId")
    private User following;

}
