package com.example.metropolitanapi.members.domain.port;

import com.example.metropolitanapi.members.domain.model.Member;
import com.example.metropolitanapi.sharedkernel.paging.PageResult;


import java.util.Optional;


public interface MemberRepositoryPort {
    Member save(Member m);
    Optional<Member> findById(Long id);
    void deleteById(Long id);


    // Paginación
    PageResult<Member> findAll(int limit, int offset);
}