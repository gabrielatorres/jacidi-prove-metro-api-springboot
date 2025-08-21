package com.example.metropolitanapi.members.application;

import com.example.metropolitanapi.members.domain.model.Member;
import com.example.metropolitanapi.members.domain.port.MemberRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UpdateMemberUseCase {
    private final MemberRepositoryPort repo;
    private final GetMemberUseCase get;

    public UpdateMemberUseCase(MemberRepositoryPort repo, GetMemberUseCase get) {
        this.repo = repo;
        this.get = get;
    }


    @Transactional
    public Member execute(Long id, Member patch){
        Member db = get.execute(id);
        if (patch.getName()!=null) db.setName(patch.getName());
        if (patch.getDni()!=null) db.setDni(patch.getDni());
        if (patch.getCity()!=null) db.setCity(patch.getCity());
        return repo.save(db);
    }
}