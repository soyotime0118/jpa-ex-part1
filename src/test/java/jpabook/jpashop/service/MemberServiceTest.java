package jpabook.jpashop.service;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
@Transactional
class MemberServiceTest
{
    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Test
    void 회원가입()
    {
        Address address = Address.builder().city("city").street("street").zipcode("zipcode").build();
        Member member = Member.build("kim", address);

        Long savedId = memberService.join(member);

        Assertions.assertEquals(member, memberRepository.findOne(savedId));
    }

    @Test
    void 회원가입_예외()
    {
        Address address = Address.builder().city("city").street("street").zipcode("zipcode").build();
        Member member = Member.build("kim", address);

        memberRepository.save(member);

        Assertions.assertThrows(IllegalStateException.class, () ->
        {
            Member newMember = Member.build("kim", address);
            memberService.join(newMember);
        });
    }
}