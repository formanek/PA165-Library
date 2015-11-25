package cz.muni.fi.pa165.projects.library.service;

import cz.muni.fi.pa165.projects.library.persistence.dao.MemberDao;
import cz.muni.fi.pa165.projects.library.persistence.entity.Member;
import java.util.List;
import java.util.Objects;
import javax.inject.Inject;
import org.springframework.stereotype.Service;

/**
 *
 * @author David Formanek
 */
@Service
public class MemberServiceImpl implements MemberService {

    @Inject
    MemberDao memberDao;

    @Override
    public void create(Member member) {
        Objects.requireNonNull(member, "null member");
        memberDao.create(member);
    }

    @Override
    public void delete(Member member) {
        Objects.requireNonNull(member, "null member");
        memberDao.delete(member);
    }

    @Override
    public Member findById(Long id) {
        Objects.requireNonNull(id, "null member id");
        return memberDao.findById(id);
    }

    @Override
    public Member findByEmail(String email) {
        Objects.requireNonNull(email, "null member email");
        return memberDao.findByEmail(email);
    }

    @Override
    public List<Member> findAll() {
        return memberDao.findAll();
    }

    @Override
    public void update(Member member) {
        Objects.requireNonNull(member, "null member");
        memberDao.update(member);
    }
}
