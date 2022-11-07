package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService
{
    private final MemberRepository memberRepository;


    @Transactional
    public Long join(Member member)
    {
        validateDuplicateMember(member);
        return memberRepository.save(member).getId();
    }

    /**
     * 중복회원 검증
     */
    private void validateDuplicateMember(Member member)
    {
        List<Member> members = memberRepository.findByName(member.getName());
        if (!members.isEmpty())
        {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    @Transactional(readOnly = true)
    public List<Member> findMembers()
    {
        return memberRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Member findOne(Long memberId)
    {
        return memberRepository.findOne(memberId);
    }
}
