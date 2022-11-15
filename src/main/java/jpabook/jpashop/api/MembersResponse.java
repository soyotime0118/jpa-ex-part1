package jpabook.jpashop.api;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MembersResponse
{
    private long id;

    private String name;

    private String city;

    private String street;

    private String zipcode;
}
