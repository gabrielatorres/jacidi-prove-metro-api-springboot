package com.example.metropolitanapi.members.application;

import com.example.metropolitanapi.members.domain.model.Member;
import com.example.metropolitanapi.members.domain.port.MemberRepositoryPort;
import com.example.metropolitanapi.sharedkernel.exception.NotFoundException;
import org.springframework.stereotype.Service;


@Service
public class GetMemberUseCase {
    private final MemberRepositoryPort repo;

    public GetMemberUseCase(MemberRepositoryPort repo) {
        this.repo = repo;
    }

    public Member execute(Long id){
        return repo.findById(id).orElseThrow(() -> new NotFoundException("Miembro no encontrado: "+id));
    }
}