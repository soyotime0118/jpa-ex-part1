package jpabook.jpashop.api;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class MemberForm
{
    @NotEmpty
    private String name;
    private String city;
    private String street;
    private String zipcode;
}
