package com.example.metropolitanapi.members.application;

import com.example.metropolitanapi.members.domain.model.Member;
import com.example.metropolitanapi.members.domain.port.MemberRepositoryPort;
import com.example.metropolitanapi.sharedkernel.paging.PageResult;
import org.springframework.stereotype.Service;


@Service
public class ListMembersPagedUseCase {
    private final MemberRepositoryPort repo;
    public ListMembersPagedUseCase(MemberRepositoryPort repo){ this.repo = repo; }
    public PageResult<Member> execute(int limit, int offset){ return repo.findAll(limit, offset); }
}