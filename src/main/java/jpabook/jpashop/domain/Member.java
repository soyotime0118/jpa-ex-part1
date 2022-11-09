package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"name"}))
@NoArgsConstructor
public class Member
{
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

    public int orderPriceAmount()
    {
        return orders.stream()
                .filter(order -> order.getStatus() == OrderStatus.ORDER)
                .mapToInt(Order::getTotalPrice)
                .sum();
    }

    protected Member(String name, Address address)
    {
        this.name = name;
        this.address = address;
    }

    public static Member build(String name, Address address)
    {
        return new Member(name, address);
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return Objects.equals(getId(), member.getId());
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(getId());
    }
}
