package jpabook.jpashop.domain.item;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Getter
@DiscriminatorValue("B")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Book extends Item
{
    private String author;
    private String isbn;

    protected Book(String name, int price, int stockQuantity, String author, String isbn)
    {
        super(name, price, stockQuantity);
        this.author = author;
        this.isbn = isbn;
    }

    public static Book build(String name, int price, int quantity, String author, String isbn)
    {
        return new Book(name, price, quantity, author, isbn);
    }

    public void changeBook(String author, String isbn)
    {
        this.author = author;
        this.isbn = isbn;
    }
}
