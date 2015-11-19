package cz.muni.fi.pa165.projects.library.service;

import cz.muni.fi.pa165.projects.library.persistence.dao.MemberDao;
import cz.muni.fi.pa165.projects.library.persistence.entity.Member;
import java.util.List;
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
        memberDao.create(member);
    }

    @Override
    public void delete(Member member) {
        memberDao.delete(member);
    }

    @Override
    public Member findById(Long id) {
        return memberDao.findById(id);
    }

    @Override
    public Member findByEmail(String email) {
        return memberDao.findByEmail(email);
    }

    @Override
    public List<Member> findAll() {
        return memberDao.findAll();
    }

    @Override
    public void update(Member member) {
        memberDao.update(member);
    }
}
