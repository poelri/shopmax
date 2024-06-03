package com.shopmax.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EntityListeners(value = {AuditingEntityListener.class})
@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity extends BaseTimeEntity {

    @CreatedBy // 최초로 등록한 사람의 id
    @Column(updatable = false) // 해당 컬럼에 대한 값은 업데이트 X
    private String createdBy; //등록자

    @LastModifiedBy // 게시물을 수정한 사람의 id를 저장 및 감지
    private String modifiedBy; //수정자
}