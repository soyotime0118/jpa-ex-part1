package jpabook.jpashop.domain;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;

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

}
