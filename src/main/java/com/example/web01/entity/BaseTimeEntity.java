package com.example.web01.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

// Auding을 적용
@EntityListeners(value={AuditingEntityListener.class})
@MappedSuperclass // 공통 매핑 정보가 필요할 때 사용되는 어노테이션
@Getter@Setter
public abstract class BaseTimeEntity {
    // 공동 속성 : 등록일자, 수정일자
    @CreatedDate
    @Column(updatable = false) // 엔티티가 생성되어 저장될 때 시간을 자동으로 저장
    private LocalDateTime regTime;

    // 엔티티 값을 변경할 때 시간을 자동으로 저장
    @LastModifiedDate
    private LocalDateTime updateTime;
}
