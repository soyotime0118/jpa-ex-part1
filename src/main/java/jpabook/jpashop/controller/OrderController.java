package jpabook.jpashop.controller;

import jpabook.jpashop.service.ItemService;
import jpabook.jpashop.service.MemberService;
import jpabook.jpashop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderController
{

    private final OrderService orderService;
    private final MemberService memberService;
    private final ItemService itemService;

    @PostMapping("/orders")
    public ResponseEntity<Long> create(@RequestBody OrderCreateRequest orderCreateRequest)
    {
        return ResponseEntity.ok(orderService.order(
                orderCreateRequest.getMemberId(),
                orderCreateRequest.getItemId(),
                orderCreateRequest.getCount()));
    }
}
