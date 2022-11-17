package jpabook.jpashop.api;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import jpabook.jpashop.service.ItemService;
import jpabook.jpashop.service.MemberService;
import jpabook.jpashop.service.OrderService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderApiController
{

    private final OrderService orderService;
    private final MemberService memberService;
    private final ItemService itemService;

    private final OrderRepository orderRepository;

    @PostMapping("/orders")
    public ResponseEntity<Long> create(@RequestBody OrderCreateRequest orderCreateRequest)
    {
        return ResponseEntity.ok(orderService.order(
                orderCreateRequest.getMemberId(),
                orderCreateRequest.getItemId(),
                orderCreateRequest.getCount()));
    }

    /**
     * @deprecated 사용하면 안됨, Entity 가 외부에 노출됨
     */
    @Deprecated
    @GetMapping("/api/v1/simple-orders")
    public List<Order> ordersV1()
    {
        return orderRepository.findAll(new OrderSearch());
    }

    @GetMapping("/api/v2/simple-orders")
    public List<SimpleOrderDto> orders()
    {
        return orderRepository.findAll(new OrderSearch())
                .stream().map(SimpleOrderDto::new)
                .collect(Collectors.toList());
    }


    @Getter
    @Setter
    static class SimpleOrderDto
    {
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;

        public SimpleOrderDto(Order order)
        {
            orderId = order.getId();
            // Lazy 로드로 쿼리 호출
            name = order.getMember().getName();
            orderDate = order.getOrderDate();
            // Lazy 로드로 쿼리 호출
            address = order.getDelivery().getAddress();
        }
    }

}
