package com.example.samplespringboot2javagradle.controller.member;

import com.example.samplespringboot2javagradle.config.security.UserAuthentication;
import com.example.samplespringboot2javagradle.config.security.UserDetailsImpl;
import com.example.samplespringboot2javagradle.dto.member.MemberChangePasswordReqDto;
import com.example.samplespringboot2javagradle.dto.member.MemberRspDto;
import com.example.samplespringboot2javagradle.dto.member.MemberSaveReqDto;
import com.example.samplespringboot2javagradle.service.member.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Member Controller for test db CRUD
 *
 * @author dongyoung.kim
 * @since 1.0
 */
@RequestMapping("api/members/v1")
@RequiredArgsConstructor
@RestController
public class MemberRestController {

    private final MemberService memberService;

    @ApiResponses(
            value = {
                @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true),
            })
    @Operation(summary = "회원 저장", description = "회원 데이터 저장 API")
    @PostMapping
    public MemberRspDto saveMember(@Valid @RequestBody MemberSaveReqDto memberReqDto) {
        return memberService.saveMember(memberReqDto);
    }

    @ApiResponses(
            value = {
                @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true),
            })
    @Operation(summary = "회원 조회", description = "회원 단건 조회(상태 구분X)")
    @GetMapping("{id}")
    public MemberRspDto findMember(@PathVariable Long id) {
        return memberService.findMember(id);
    }

    @ApiResponses(
            value = {
                @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true),
            })
    @Operation(summary = "회원 전체 조회", description = "회원 전체 조회(상태 구분X)")
    @GetMapping
    public List<MemberRspDto> findAllMembers() {
        return memberService.findAllMembers();
    }

    @ApiResponses(
            value = {
                @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true),
            })
    @Operation(summary = "비밀번호 수정", description = "활성화된 회원만 수정 가능")
    @PutMapping("password")
    public MemberRspDto changePassword(
            @Parameter(hidden = true) @UserAuthentication UserDetailsImpl userDetails,
            @Valid @RequestBody MemberChangePasswordReqDto reqDto) {
        return memberService.changePassword(userDetails.getMember().getId(), reqDto);
    }

    @ApiResponses(
            value = {
                @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true),
            })
    @Operation(summary = "회원 삭제", description = "활성화된 회원만 삭제 가능, 삭제 시 상태 변경(하드 딜리트 X)")
    @DeleteMapping("{id}")
    public MemberRspDto deleteMember(@PathVariable Long id) {
        return memberService.deleteMember(id);
    }

    @ApiResponses(
            value = {
                @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true),
            })
    @Operation(summary = "로그아웃")
    @GetMapping("logout")
    public boolean logout(HttpServletRequest request) {
        var session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return true;
    }
}
