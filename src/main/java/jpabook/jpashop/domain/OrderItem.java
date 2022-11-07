package jpabook.jpashop.domain;

import jpabook.jpashop.domain.item.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "order_item")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem
{
    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id",
            nullable = false,
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id",
            nullable = false,
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Item item;

    private int orderPrice;

    private int count;

    protected OrderItem(Item item, int orderPrice, int count)
    {
        this.item = item;
        this.orderPrice = orderPrice;
        this.count = count;
    }

    public void order(Order order)
    {
        this.order = order;
    }

    public void cancel()
    {
        //재고수량 증가
        getItem().addStock(count);
    }

    public int getTotalPrice()
    {
        return orderPrice * count;
    }


    public static OrderItem create(Item item, int orderPrice, int count)
    {
        item.removeStock(count);
        return new OrderItem(item, orderPrice, count);
    }
}
