package jpabook.jpashop.controller;

import jpabook.jpashop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping("/items")
    public List<ItemsResponse> list()
    {
        //mason ItemResponse 작성
        return itemService.findItems().stream()
                .map(item ->
                {
                    ItemsResponse response = new ItemsResponse();
                    return response;
                })
                .collect(Collectors.toList());
    }
}
