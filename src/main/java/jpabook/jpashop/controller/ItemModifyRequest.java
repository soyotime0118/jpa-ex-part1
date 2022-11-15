package jpabook.jpashop.controller;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemModifyRequest
{
    private String author;
    private String isbn;
    private String name;
    private int price;
    private int stockQuantity;
}
