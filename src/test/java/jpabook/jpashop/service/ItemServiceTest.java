package jpabook.jpashop.service;

import jpabook.jpashop.controller.ItemModifyRequest;
import jpabook.jpashop.domain.item.Book;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;


@SpringBootTest
@Transactional
class ItemServiceTest
{

    @Autowired
    ItemService itemService;

    @Autowired
    EntityManager entityManager;

    private static Book getBook()
    {
        return Book.build("시골 JPA", 10000, 10, "저자", "191920-1029");
    }

    @DisplayName("책 정보수정 성공")
    @Test
    void modifyBook()
    {
        Book book = getBook();
        entityManager.persist(book);
        entityManager.flush();

        ItemModifyRequest request = new ItemModifyRequest();
        request.setName("new book1");
        request.setIsbn("11122");

        int actualPrice = 1000;
        int actualStockQuantity = 100;

        request.setPrice(actualPrice);
        request.setStockQuantity(actualStockQuantity);
        itemService.modify(book.getId(), request);

        Book expected = entityManager.find(Book.class, book.getId());
        Assertions.assertEquals(expected.getPrice(), actualPrice);
        Assertions.assertEquals(expected.getStockQuantity(), actualStockQuantity);

    }
}