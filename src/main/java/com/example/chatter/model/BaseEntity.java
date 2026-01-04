package com.example.chatter.model;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.Date;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    @CreatedDate
    @JdbcTypeCode(SqlTypes.BIGINT)
    private Instant createdAt;

    @CreatedBy
    @ManyToOne
    private User createdBy;

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Date getCreatedAtDate() {
        return createdAt != null ? Date.from(createdAt) : null;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }
}
