package jpabook.jpashop.api;


import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.*;
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
    public ResponseEntity<Long> create(@RequestBody @Valid CreateMemberRequest createMemberRequest)
    {

        Address address = Address.builder()
                .city(createMemberRequest.getCity())
                .street(createMemberRequest.getStreet())
                .zipcode(createMemberRequest.getZipcode())
                .build();

        Member member = Member.build(createMemberRequest.getName(), address);
        return ResponseEntity.ok(memberService.join(member));

    }

    @PutMapping("/api/v2/members/{id}")
    public ResponseEntity<UpdateMemberResponse> updateMember(
            @PathVariable("id") Long id,
            @RequestBody UpdateMemberRequest request)
    {
        //mason 테스트코드 작성
        memberService.update(id, request.getName());
        Member findMember = memberService.findOne(id);
        return ResponseEntity.ok(new UpdateMemberResponse(findMember.getId(), findMember.getName()));
    }

    @GetMapping("")
    public ListApiResponse<List<MembersResponse>> list()
    {
        return new ListApiResponse<>(memberService.findMembers());
    }


    @Data
    static class UpdateMemberRequest
    {
        private String name;

    }

    @Data
    @AllArgsConstructor
    static class UpdateMemberResponse
    {
        private Long id;
        private String name;
    }
}
