package com.example.metropolitanapi.members.infrastructure.persistence.jpa.adapter;

import com.example.metropolitanapi.members.domain.model.Member;
import com.example.metropolitanapi.members.domain.port.MemberRepositoryPort;
import com.example.metropolitanapi.members.infrastructure.persistence.jpa.entity.MemberEntity;
import com.example.metropolitanapi.members.infrastructure.persistence.jpa.spring.MemberJpaRepository;
import com.example.metropolitanapi.sharedkernel.paging.PageResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Component
public class MemberRepositoryAdapter implements MemberRepositoryPort {
    private final MemberJpaRepository repo;
    public MemberRepositoryAdapter(MemberJpaRepository repo){ this.repo = repo; }


    private Member toDomain(MemberEntity e){
        return new Member(e.getId(), e.getName(), e.getDni(), e.getCity(), e.getCalendar());
    }
    private MemberEntity toEntity(Member m){
        MemberEntity e = new MemberEntity();
        e.setId(m.getId()); e.setName(m.getName()); e.setDni(m.getDni()); e.setCity(m.getCity()); e.setCalendar(m.getCalendar());
        return e;
    }


    @Override public Member save(Member m){ return toDomain(repo.save(toEntity(m))); }
    @Override public Optional<Member> findById(Long id){ return repo.findById(id).map(this::toDomain); }
    @Override public void deleteById(Long id){ repo.deleteById(id); }


    // NEW: pagination using limit+offset (translated to Spring Data page)
    @Override public PageResult<Member> findAll(int limit, int offset){
        int safeLimit = (limit <= 0) ? 20 : limit;
        int safeOffset = Math.max(0, offset);
        int page = safeOffset / safeLimit;
        Page<MemberEntity> p = repo.findAll(PageRequest.of(page, safeLimit));
        List<Member> items = p.getContent().stream().map(this::toDomain).collect(Collectors.toList());
        return new PageResult<>(items, safeLimit, safeOffset, p.getTotalElements());
    }
}