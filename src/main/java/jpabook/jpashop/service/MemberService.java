package jpabook.jpashop.service;

import jpabook.jpashop.api.MembersResponse;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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
    public List<MembersResponse> findMembers()
    {
        return memberRepository.findAll().stream()
                .map(member ->
                {
                    //mason mapper 적용
                    MembersResponse response = new MembersResponse();
                    response.setId(member.getId());
                    response.setName(member.getName());
                    response.setCity(member.getAddress().getCity());
                    response.setStreet(member.getAddress().getStreet());
                    response.setZipcode(member.getAddress().getZipcode());
                    return response;
                })
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Member findOne(Long memberId)
    {
        return memberRepository.findOne(memberId);
    }

    @Transactional
    public void update(Long id, String name)
    {
        //mason 테스트코드 작성
        Member member = memberRepository.findOne(id);
        member.changeName(name);
    }
}
