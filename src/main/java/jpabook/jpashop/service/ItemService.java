package jpabook.jpashop.service;

import jpabook.jpashop.api.BookCreateApiRequest;
import jpabook.jpashop.api.ItemModifyRequest;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.exception.NotExistItemException;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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

    @Transactional
    public void modify(long itemId, ItemModifyRequest modifyRequest)
    {
        Book book = Optional.ofNullable(itemRepository.findOne(itemId))
                .map(item -> (Book) item)
                .orElseThrow(NotExistItemException::new);

        book.changeBook(modifyRequest.getAuthor(), modifyRequest.getIsbn());
        book.changeInfo(modifyRequest.getName(), modifyRequest.getStockQuantity(), modifyRequest.getPrice());
    }
}
