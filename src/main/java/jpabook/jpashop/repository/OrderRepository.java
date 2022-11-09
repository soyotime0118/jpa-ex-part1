package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;

import java.util.List;

public interface OrderRepository
{
    void save( Order order);

    Order findById(Long id);

    List<Order> findAll(OrderSearch orderSearch);
}
