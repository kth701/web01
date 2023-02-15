package com.example.web01.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

@EntityListeners(value={AuditingEntityListener.class})
@MappedSuperclass // 공통 매핑 정보가 필요할 때 사용되는 어노테이션
@Getter
public abstract class BaseEntity extends BaseTimeEntity {
    // 등록일, 수정일, 등록자, 수정자
    @CreatedBy
    @Column(updatable = false)
    private String createdBy; // 등록자
    @LastModifiedBy
    private String modifiedBy;// 수정자
}
