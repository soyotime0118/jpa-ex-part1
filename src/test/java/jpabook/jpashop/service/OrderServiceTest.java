package jpabook.jpashop.service;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@SpringBootTest
@Transactional
class OrderServiceTest
{

    @Autowired
    EntityManager entityManager;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;

    private static Book getBook()
    {
        return Book.build("시골 JPA", 10000, 10, "저자", "191920-1029");
    }

    private static Member getMember()
    {
        return Member.build("kim", new Address("서울", "rudrl", "12323"));
    }

    @Test
    void 상품주문()
    {
        Member member = getMember();
        entityManager.persist(member);
        Book book = getBook();
        entityManager.persist(book);

        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        Order expected = orderRepository.findById(orderId);

        Assertions.assertEquals(OrderStatus.ORDER, expected.getStatus());
        Assertions.assertEquals(1, expected.getOrderItems().size());
        Assertions.assertEquals(10000 * orderCount, expected.getTotalPrice());
        Assertions.assertEquals(8, book.getStockQuantity());


    }

    @Test
    void 재고수량초과()
    {
        Member member = getMember();
        entityManager.persist(member);
        Book book = getBook();
        entityManager.persist(book);

        int orderCount = 11;
        Assertions.assertThrows(NotEnoughStockException.class, () ->
                orderService.order(member.getId(), book.getId(), orderCount));
    }


    @Test
    @Rollback(value = false)
    void 주문취소()
    {
        Member member = getMember();
        entityManager.persist(member);
        Book book = getBook();
        entityManager.persist(book);

        Long orderId = orderService.order(member.getId(), book.getId(), 10);

        orderService.cancel(orderId);

//        Order expected = orderRepository.findById(orderId);

//        Assertions.assertEquals(OrderStatus.CANCEL, expected.getStatus());
        Assertions.assertEquals(10, book.getStockQuantity());
        Assertions.assertEquals(1, member.getOrders().stream()
                .filter(order -> order.getStatus() == OrderStatus.ORDER).count());
    }

}