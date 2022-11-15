package jpabook.jpashop.api;

import jpabook.jpashop.exception.NotExistItemException;
import jpabook.jpashop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/items/{itemId}/edit")
    public void updateItem(
            @PathVariable("itemId") Long itemId,
            @RequestBody ItemModifyRequest modifyRequest)
    {
        itemService.modify(itemId, modifyRequest);
    }

    @ExceptionHandler(NotExistItemException.class)
    public ResponseEntity<String> errorHandler(NotExistItemException e)
    {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(e.getMessage());
    }

}
