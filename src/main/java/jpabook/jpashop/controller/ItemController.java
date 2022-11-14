package jpabook.jpashop.controller;

import jpabook.jpashop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ItemController
{
    private final ItemService itemService;

    @PostMapping("/items/new")
    public long create(BookCreateApiRequest bookCreateApiRequest)
    {
        return itemService.saveItem(bookCreateApiRequest);
    }
}
