package jpabook.jpashop.api;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class CreateMemberRequest
{
    @NotEmpty(message = "이름은 필수값 입니다")
    private String name;
    private String city;
    private String street;
    private String zipcode;
}
