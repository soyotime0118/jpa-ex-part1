package jpabook.jpashop.api;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import jpabook.jpashop.repository.OrderSimpleQueryDto;
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

    /**
     * @deprecated v1보다는 좋아졌지만, 객체그래프 탐색시 N+1 쿼리 발생
     */
    @Deprecated
    @GetMapping("/api/v2/simple-orders")
    public List<SimpleOrderDto> ordersV2()
    {
        return orderRepository.findAll(new OrderSearch())
                .stream().map(SimpleOrderDto::new)
                .collect(Collectors.toList());
    }

    /**
     * jpql의 join fetch 로 N+1은 제거, 하지만 조회쿼리에서 불필요한 컬럼이 남아있다.
     */
    @GetMapping("/api/v3/simple-orders")
    public List<SimpleOrderDto> ordersV3()
    {
        return orderRepository.findAllWithMemberDelivery(new OrderSearch())
                .stream().map(SimpleOrderDto::new)
                .collect(Collectors.toList());
    }

    /**
     * V3의 불필요한 컬럼을 제거한다. 이 경우 반환하는 DTO가 Repository의 의존성에 추가된다
     */
    @GetMapping("/api/v4/simple-orders")
    public List<OrderSimpleQueryDto> ordersV4()
    {
        return orderRepository.findOrderDtos();
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
            orderStatus = order.getStatus();
            orderDate = order.getOrderDate();
            // Lazy 로드로 쿼리 호출
            address = order.getDelivery().getAddress();
        }
    }

}
