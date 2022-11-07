package jpabook.jpashop.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order
{
    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id"
            , foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id",
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Delivery delivery;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;


    public void cancel()
    {
        if (delivery.getStatus() == DeliveryStatus.COMP)
        {
            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.");
        }
        this.status = OrderStatus.CANCEL;
        for (OrderItem orderItem : orderItems)
        {
            orderItem.cancel();
        }
    }

    public int getTotalPrice()
    {
        return getOrderItems().stream()
                .mapToInt(OrderItem::getTotalPrice)
                .sum();
    }

    private void addItem(OrderItem orderItem)
    {
        orderItem.order(this);
        orderItems.add(orderItem);
    }

    public static Order create(Member member, Delivery delivery, OrderItem... orderItems)
    {
        Order order = new Order(member, delivery, OrderStatus.ORDER, LocalDateTime.now());
        for (OrderItem orderItem : orderItems)
        {
            order.addItem(orderItem);
        }
        return order;
    }

    private Order(Member member, Delivery delivery, OrderStatus status, LocalDateTime orderDate)
    {
        this.member = member;
        this.delivery = delivery;
        this.status = status;
        this.orderDate = orderDate;
    }
}
