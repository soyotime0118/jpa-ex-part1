package jpabook.jpashop.controller;


import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/members")
@RestController
@RequiredArgsConstructor
public class MemberController
{
    private final MemberService memberService;

    @PostMapping("/new")
    public ResponseEntity<Long> create(@RequestBody Map<String, String> parameters)
    {

        Address address = Address.builder()
                .city(parameters.get("city"))
                .street(parameters.get("street"))
                .zipcode(parameters.get("zipcode"))
                .build();

        Member member = Member.build(
                parameters.get("name"), address);
        return ResponseEntity.ok(memberService.join(member));

    }

    @GetMapping("/members")
    public ResponseEntity<List<MembersResponse>> list()
    {
        return ResponseEntity.ok(memberService.findMembers());
    }
}
