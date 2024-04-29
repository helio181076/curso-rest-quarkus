package io.github.dougllasfps.quarkussocial.domain.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "posts")
@Data
@ApplicationScoped
public class Post extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "post_text")
    private String text;
    @Column(name = "dateTime")
    private LocalDateTime dataTime;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @PrePersist
    public void prePersist(){
        setDataTime(LocalDateTime.now());
    }
}
