package com.example.metropolitanapi.members.application;

import com.example.metropolitanapi.members.domain.port.MemberRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service

public class DeleteMemberUseCase {
    private final MemberRepositoryPort repo;

    public DeleteMemberUseCase(MemberRepositoryPort repo){ this.repo=repo; }

    @Transactional
    public void execute(Long id){
        repo.deleteById(id);
    }
}