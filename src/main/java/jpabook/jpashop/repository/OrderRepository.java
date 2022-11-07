package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;

public interface OrderRepository
{
    void save( Order order);

    Order findById(Long id);
}
