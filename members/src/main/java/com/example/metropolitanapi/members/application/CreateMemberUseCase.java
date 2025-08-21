package com.example.metropolitanapi.members.application;

import com.example.metropolitanapi.members.domain.model.Member;
import com.example.metropolitanapi.members.domain.port.MemberRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class CreateMemberUseCase {
    private final MemberRepositoryPort repo;

    public CreateMemberUseCase(MemberRepositoryPort repo){ this.repo = repo; }

    @Transactional
    public Member execute(Member m){
        return repo.save(m);
    }
}