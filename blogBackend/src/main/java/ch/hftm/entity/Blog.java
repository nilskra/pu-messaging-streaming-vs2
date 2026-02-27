package ch.hftm.entity;

import io.smallrye.common.constraint.NotNull;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Blog {
    @Id 
    @GeneratedValue
    private Long id;

    private String title;

    @NotNull
    private String content;


    public Long getId() {
        return this.id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return this.content;
    }

    public Blog(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Blog() {}
}
