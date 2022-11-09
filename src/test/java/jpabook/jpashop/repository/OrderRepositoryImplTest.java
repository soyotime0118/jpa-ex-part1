package jpabook.jpashop.repository;

import jpabook.jpashop.domain.*;
import jpabook.jpashop.domain.item.Item;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@SpringBootTest
@Transactional
@Rollback(value = false)
class OrderRepositoryImplTest
{

    @Autowired
    OrderRepositoryImpl orderRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    void assignOrder()
    {
        Delivery delivery = null;
        Address address = Address.builder().city("city").street("street").zipcode("zipcode").build();
        Member member = Member.build("kim", address);
        Item item = null;
        OrderItem orderItem = OrderItem.create(item, 1000, 1);
        Order order = Order.create(member, delivery, orderItem);
        entityManager.persist(order);
        entityManager.flush();
    }
}