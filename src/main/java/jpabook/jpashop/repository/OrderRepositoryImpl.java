package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository
{
    private final EntityManager entityManager;

    @Override
    public void save(Order order)
    {
        entityManager.persist(order);
    }

    @Override
    public Order findById(Long id)
    {
        return entityManager.find(Order.class, id);
    }

}
