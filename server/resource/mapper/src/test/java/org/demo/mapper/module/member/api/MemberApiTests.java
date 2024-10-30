package org.demo.mapper.module.member.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.demo.mapper.module.member.domain.Member;
import org.demo.mapper.module.member.domain.MemberRole;
import org.demo.mapper.module.member.domain.MemberStatus;
import org.demo.mapper.module.member.request.MemberRequest;
import org.demo.mapper.module.member.request.MemberUpdateRequest;
import org.demo.mapper.module.member.response.MemberResponse;
import org.demo.mapper.module.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class MemberApiTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private MemberService memberService;

    @Test
    @DisplayName("Save Member With No Duplicated Email")
    void saveMemberWithNoDuplicatedEmail() throws Exception {
        MemberRequest memberRequest = new MemberRequest("a1234@demo.com", "a1234", "1234");

        mockMvc.perform(post("/api/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(memberRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is("a1234@demo.com")))
                .andExpect(jsonPath("$.role", is(MemberRole.USER.toString())))
                .andDo(print());
    }

    @Test
    @DisplayName("Save Member With Duplicated Email")
    void saveMemberWithDuplicatedEmail() throws Exception {
        MemberRequest memberRequest = new MemberRequest("a1234@demo.com", "a1234", "1234");
        memberService.save(memberRequest);

        mockMvc.perform(post("/api/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(memberRequest)))
                .andExpect(status().isConflict())
                .andDo(print());
    }

    @Test
    @DisplayName("Find Member With Exists Email")
    void findMemberWithExistsEmail() throws Exception {
        String email = "a1234@demo.com";
        String password = "1234";
        MemberRequest memberRequest = new MemberRequest(email, "a1234", password);
        Member saveMember = memberService.save(memberRequest);
        MemberResponse memberResponse = new MemberResponse(saveMember);

        mockMvc.perform(get("/api/members/" + email))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is(email)))
                .andExpect(jsonPath("$.username", memberResponse.getUsername()).exists())
                .andExpect(jsonPath("$.password", is(password)))
                .andExpect(jsonPath("$.role", is(MemberRole.USER.toString())))
                .andExpect(jsonPath("$.regDate", memberResponse.getRegDate()).exists())
                .andExpect(jsonPath("$.modDate", memberResponse.getModDate()).exists())
                .andDo(print());
    }

    @Test
    @DisplayName("Find Member With Not Exists Email")
    void findMemberWithNotExistsEmail() throws Exception {
        String email = "a1234@demo.com";

        mockMvc.perform(get("/api/members/" + email))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    @DisplayName("Find Members")
    void findMembers() throws Exception {
        for (int i = 1; i <= 255; i++) {
            MemberRequest memberRequest = new MemberRequest("a" + i + "@demo.com", "a" + i, "1234");
            memberService.save(memberRequest);
        }

        mockMvc.perform(get("/api/members"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count", is(255)))
                .andExpect(jsonPath("$.members", hasSize(255)))
                .andDo(print());
    }

    @Test
    @DisplayName("Update Member Information")
    void updateMemberInformation() throws Exception {
        String email = "a1234@demo.com";
        MemberRequest memberRequest = new MemberRequest(email, "a1234", "1234");
        memberService.save(memberRequest);
        MemberUpdateRequest updateRequest = new MemberUpdateRequest("a0000", "0000");

        mockMvc.perform(patch("/api/members/" + email)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    // 회원 상태 수정
    @Test
    @DisplayName("Update Member Status")
    void updateMemberStatus() throws Exception {
        String email = "a1234@demo.com";
        MemberRequest memberRequest = new MemberRequest(email, "a1234", "1234");
        memberService.save(memberRequest);
        MemberStatus updateStatus = MemberStatus.ONLINE;

        mockMvc.perform(patch("/api/members/" + email + "/status/" + updateStatus))
                .andExpect(status().isOk())
                .andDo(print());
    }

    // 회원 권한 수정
    @Test
    @DisplayName("Update Member Role")
    void updateMemberRole() throws Exception {
        String email = "a1234@demo.com";
        MemberRequest memberRequest = new MemberRequest(email, "a1234", "1234");
        memberService.save(memberRequest);
        MemberRole updateRole = MemberRole.ADMIN;

        mockMvc.perform(patch("/api/members/" + email + "/role/" + updateRole))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("Delete Member By Email")
    void deleteMemberByEmail() throws Exception {
        String email = "a1234@demo.com";
        MemberRequest memberRequest = new MemberRequest(email, "a1234", "1234");
        memberService.save(memberRequest);

        mockMvc.perform(delete("/api/members/" + email))
                .andExpect(status().isOk())
                .andDo(print());
    }
}