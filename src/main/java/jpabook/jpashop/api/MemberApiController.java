package jpabook.jpashop.api;


import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/members")
@RestController
@RequiredArgsConstructor
public class MemberApiController
{
    private final MemberService memberService;

    @PostMapping("/new")
    public ResponseEntity<Long> create(@RequestBody @Valid MemberForm form)
    {

        Address address = Address.builder()
                .city(form.getCity())
                .street(form.getStreet())
                .zipcode(form.getZipcode())
                .build();

        Member member = Member.build(form.getName(), address);
        return ResponseEntity.ok(memberService.join(member));

    }

    @GetMapping("")
    public ResponseEntity<List<MembersResponse>> list()
    {
        return ResponseEntity.ok(memberService.findMembers());
    }
}
