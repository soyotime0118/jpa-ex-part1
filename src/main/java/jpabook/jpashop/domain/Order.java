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

    @ManyToOne
    @JoinColumn(name = "member_id"
            , foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT)
            , nullable = false)
    private Member member;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "delivery_id",
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT),
            nullable = false)
    private Delivery delivery;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

}
