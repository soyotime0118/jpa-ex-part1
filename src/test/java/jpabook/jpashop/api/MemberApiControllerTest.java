package jpabook.jpashop.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@AutoConfigureMockMvc
@SpringBootTest
class MemberApiControllerTest
{

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;


    @DisplayName("회원 가입")
    @Test
    void name() throws Exception
    {
        long memberId = 0L;
        CreateMemberRequest request = new CreateMemberRequest();
        request.setName("홍길동");
        request.setCity("newCity");
        request.setStreet("newStreet");
        request.setZipcode("newZipcode");
        mockMvc.perform(post("/members/new")
                        .content(new ObjectMapper().writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());


    }

    @DisplayName("회원 가입 - 이름없는경우")
    @Test
    void emptyName() throws Exception
    {
        CreateMemberRequest request = new CreateMemberRequest();
        request.setCity("newCity");
        request.setStreet("newStreet");
        request.setZipcode("newZipcode");
        mockMvc.perform(post("/members/new")
                        .content(new ObjectMapper().writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andDo(print());


    }
}