package cz.muni.fi.pa165.projects.library.persistence.dao;

import cz.muni.fi.pa165.projects.library.persistence.entity.Member;
import java.util.List;
import java.util.Objects;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

/**
 * Implementation of DAO for library members
 * @author David Formanek
 */
@Repository
public class MemberDaoImpl implements MemberDao {

    @PersistenceContext
    private EntityManager em;
    
    @Override
    public void create(Member member) {
        Objects.requireNonNull(member, "null argument member");
        Objects.requireNonNull(member.getGivenName(), "given name not specified");
        Objects.requireNonNull(member.getSurname(), "surname not specified");
        Objects.requireNonNull(member.getEmail(), "email not specified");
        em.persist(member);
    }

    @Override
    public void delete(Member member) {
        Objects.requireNonNull(member, "null argument member");
        Objects.requireNonNull(member.getId(), "deleting member with null id");
        em.remove(member);
    }

    @Override
    public Member findById(Long id) {
        Objects.requireNonNull(id, "id is null");
        return em.find(Member.class, id);
    }

    @Override
    public Member findByEmail(String email) {
        Objects.requireNonNull(email, "email is null");
        if (email.isEmpty()) {
            throw new IllegalArgumentException("email is empty");
        }
        if (!email.contains("@")) {
            throw new IllegalArgumentException("email is not valid");
        }
        List<Member> resultList = em.createQuery("SELECT m FROM Member m where m.email = :email", Member.class)
                .setParameter("email", email).getResultList();
        if (resultList == null || resultList.isEmpty()) {
            return null;
        }
        if (resultList.size() > 1) {
            throw new IllegalStateException("duplicit email: " + email);
        }
        return resultList.get(0);
    }
    
    @Override
    public List<Member> findAll() {
        return em.createQuery("SELECT m FROM Member m", Member.class).getResultList();
    }

    @Override
    public void update(Member member) {
        Objects.requireNonNull(member, "null argument member");
        Objects.requireNonNull(member.getGivenName(), "given name not specified");
        Objects.requireNonNull(member.getSurname(), "surname not specified");
        Objects.requireNonNull(member.getEmail(), "email not specified");
        em.merge(member);
    }
}
