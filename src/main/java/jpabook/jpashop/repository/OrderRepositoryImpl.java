package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

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

    @Override
    public List<Order> findAll(OrderSearch orderSearch)
    {
        return entityManager.createQuery("select o from Order o join o.member", Order.class)

                .getResultList();
    }

    @Override
    public List<Order> findAllWithMemberDelivery(OrderSearch orderSearch)
    {
        return entityManager.createQuery("select o from Order o " +
                " join fetch o.member m " +
                " join fetch o.delivery d", Order.class).getResultList();
    }

    @Override
    public List<OrderSimpleQueryDto> findOrderDtos()
    {
        return entityManager.createQuery("select new jpabook.jpashop.repository.OrderSimpleQueryDto(o.id,m.name,o.orderDate,o.status, d.address) from Order o " +
                        " join o.member m " +
                        " join o.delivery d", OrderSimpleQueryDto.class)
                .getResultList();
    }

    @Override
    public List<Order> findAllWithItem()
    {
        return entityManager.createQuery(
                "select distinct o " +
                        "from Order o " +
                        " join fetch o.member m " +
                        " join fetch o.delivery d " +
                        " join fetch o.orderItems oi " +
                        " join fetch oi.item i", Order.class).getResultList();
    }

}
