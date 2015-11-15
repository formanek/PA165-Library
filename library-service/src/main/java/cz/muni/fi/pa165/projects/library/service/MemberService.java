package cz.muni.fi.pa165.projects.library.service;


import cz.muni.fi.pa165.projects.library.persistence.entity.Member;

import java.util.List;

/**
 * Created by lajci on 15.11.2015.
 */
public interface MemberService {

    void create(Member member);

    void delete(Member member);

    Member findById(Long id);

    Member findByEmail(String email);

    List<Member> findAll();

    void update(Member member);
}
