package ch.hftm.blog.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Entity
public class Blog extends PanacheEntity {
    public String title;
    public String content;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public BlogStatus status = BlogStatus.PENDING;
}
