package jpabook.jpashop.api;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

@Getter
@Setter
public class ItemModifyRequest
{
    @NotEmpty
    private String author;

    @NotEmpty
    private String isbn;

    @NotEmpty
    @Size(max = 30)
    private String name;

    @Positive
    private int price;

    @PositiveOrZero
    private int stockQuantity;
}
