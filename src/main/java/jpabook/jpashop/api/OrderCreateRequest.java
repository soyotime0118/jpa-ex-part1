package jpabook.jpashop.api;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderCreateRequest
{
    private long memberId;
    private long itemId;
    private int count;
}
