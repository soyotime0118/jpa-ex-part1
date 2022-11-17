package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class OrderSimpleQueryDto
{
    private Long orderId;
    private String name;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private Address address;

    public OrderSimpleQueryDto(Long orderId, String name, LocalDateTime orderDate, OrderStatus orderStatus, Address address)
    {
        this.orderId = orderId;
        // Lazy 로드로 쿼리 호출
        this.name = name;
        this.orderStatus = orderStatus;
        this.orderDate = orderDate;
        // Lazy 로드로 쿼리 호출
        this.address = address;
    }

}
