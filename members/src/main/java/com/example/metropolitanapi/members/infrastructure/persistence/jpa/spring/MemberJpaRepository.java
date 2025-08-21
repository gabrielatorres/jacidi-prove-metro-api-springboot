package com.example.metropolitanapi.members.infrastructure.persistence.jpa.spring;

import com.example.metropolitanapi.members.infrastructure.persistence.jpa.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MemberJpaRepository extends JpaRepository<MemberEntity, Long> {}