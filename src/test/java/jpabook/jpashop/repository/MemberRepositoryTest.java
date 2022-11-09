package jpabook.jpashop.repository;

import jpabook.jpashop.domain.*;
import jpabook.jpashop.domain.item.Book;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberRepositoryTest
{

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EntityManager em;

    @DisplayName("객체-관계 불일치 확인")
    @Test
    void assignOrder()
    {
        Address address = new Address("서울", "rudrl", "12323");
        Delivery delivery = Delivery.build(address);
        Member member = Member.build("kim", address);
        em.persist(member);
        int itemPrice = 10000;
        Book book = Book.build("newbook", itemPrice, 10, "author", "aaaabb");
        em.persist(book);

        int orderItemPrice = 10000;
        int orderItemCount = 1;
        OrderItem orderItem = OrderItem.create(book, orderItemPrice, orderItemCount);

        Order order = Order.create(member, delivery, orderItem);
//        member.getOrders().add(order);
        em.persist(order);

        em.flush();

        em.clear();


        Member expected = em.find(Member.class, member.getId());
        Assertions.assertNotNull(expected);
        Assertions.assertEquals(member, expected);
        Assertions.assertEquals(orderItemPrice * orderItemCount, expected.orderPriceAmount());
    }
}