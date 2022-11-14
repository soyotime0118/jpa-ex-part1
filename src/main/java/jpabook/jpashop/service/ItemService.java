package jpabook.jpashop.service;

import jpabook.jpashop.controller.BookCreateApiRequest;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService
{
    private final ItemRepository itemRepository;

    @Transactional
    public long saveItem(BookCreateApiRequest item)
    {
        Book book = Book.build(item.getName(), item.getPrice(), item.getStockQuantity(), item.getAuthor(), item.getIsbn());
        //mason dto mapper
        itemRepository.save(book);
        return book.getId();
    }

    public List<Item> findItems()
    {
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId)
    {
        return itemRepository.findOne(itemId);
    }
}
