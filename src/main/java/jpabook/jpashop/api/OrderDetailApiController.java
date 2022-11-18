package jpabook.jpashop.api;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderDetailApiController
{
    private final OrderRepository orderRepository;

    /**
     *
     */
    @Deprecated(since = "엔티티 직접노출")
    @GetMapping("/api/v1/orders")
    public List<Order> ordersV1()
    {
        return orderRepository.findAll(new OrderSearch());
    }

    @Getter
    @Setter
    static class OrderDto
    {
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;

        private List<OrderItemDto> orderItems;

        public OrderDto(Order o)
        {
            this.orderId = o.getId();
            this.name = o.getMember().getName();
            this.orderDate = o.getOrderDate();
            this.address = o.getMember().getAddress();
            this.orderStatus = o.getStatus();
            this.orderItems = o.getOrderItems().stream()
                    .map(OrderItemDto::new)
                    .collect(Collectors.toList());
        }
    }

    @Getter
    @Setter
    static class OrderItemDto
    {
        private String itemName;
        private int orderPrice;
        private int count;

        public OrderItemDto(OrderItem orderItem)
        {
            this.itemName = orderItem.getItem().getName();
            this.orderPrice = orderItem.getOrderPrice();
            this.count = orderItem.getCount();
        }
    }

    @GetMapping("/api/v2/orders")
    public List<OrderDto> ordersV2()
    {
        return orderRepository.findAll(new OrderSearch())
                .stream().map(OrderDto::new)
                .collect(Collectors.toList());
    }
}
