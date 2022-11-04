package jpabook.jpashop.domain;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "orders")
public class Order
{
    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id"
            , foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT)
            , nullable = false)
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id",
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT),
            nullable = false)
    private Delivery delivery;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;


    //연관관계 method
    public void assignMember(Member member)
    {
        this.member = member;
        member.getOrders().add(this);
    }

    public void addItem(OrderItem orderItem)
    {
        orderItem.order(this);
        orderItems.add(orderItem);
    }

    public void delivery(Delivery delivery)
    {
        this.delivery = delivery;
        delivery.order(this);
    }

}
