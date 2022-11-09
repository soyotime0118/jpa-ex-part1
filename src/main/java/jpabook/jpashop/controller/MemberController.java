package jpabook.jpashop.controller;


import jakarta.validation.Valid;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/members")
@RestController
@RequiredArgsConstructor
public class MemberController
{
    private final MemberService memberService;

    @PostMapping("/new")
    public void create(@Valid MemberForm form)
    {

        //mason MemberFormDTO 변환
        Address address = Address.builder().city(form.getCity()).street(form.getStreet()).zipcode(form.getZipcode()).build();

        Member member = Member.build(form.getName(), address);
        memberService.join(member);

    }
}
