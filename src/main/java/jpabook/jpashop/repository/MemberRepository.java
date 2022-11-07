package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class MemberRepository
{

    @PersistenceContext
    private final EntityManager entityManager;

    @Autowired
    public MemberRepository(EntityManager entityManager)
    {
        this.entityManager = entityManager;
    }

    public Member save(Member member)
    {
        entityManager.persist(member);
        return member;
    }

    public Member findOne(Long id)
    {
        return entityManager.find(Member.class, id);
    }

    public List<Member> findAll()
    {
        return entityManager.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public List<Member> findByName(String name)
    {
        return entityManager.createQuery("select m from Member  m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }


}
