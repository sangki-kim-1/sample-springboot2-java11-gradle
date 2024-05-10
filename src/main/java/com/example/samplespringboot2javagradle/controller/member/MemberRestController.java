package com.example.samplespringboot2javagradle.controller.member;

import com.example.samplespringboot2javagradle.dto.member.MemberRspDto;
import com.example.samplespringboot2javagradle.dto.member.MemberSaveReqDto;
import com.example.samplespringboot2javagradle.dto.member.MemberUpdateReqDto;
import com.example.samplespringboot2javagradle.service.member.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
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
@Tag(name = "Member Controller", description = "DB CRUD용 회원 컨트롤러")
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
    public MemberRspDto saveMember(@RequestBody MemberSaveReqDto memberReqDto) {
        return memberService.saveMember(memberReqDto);
    }

    @ApiResponses(
            value = {
                @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true),
            })
    @Operation(summary = "회원 조회", description = "1~5까지 샘플 데이터로 조회됩니다.")
    @GetMapping("{id}")
    public MemberRspDto findMember(@PathVariable Long id) {
        return memberService.findMember(id);
    }

    @ApiResponses(
            value = {
                @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true),
            })
    @Operation(summary = "회원 전체 조회")
    @GetMapping
    public List<MemberRspDto> findAllMembers() {
        return memberService.findAllMembers();
    }

    @ApiResponses(
            value = {
                @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true),
            })
    @Operation(summary = "회원 수정", description = "회원 데이터 수정 API")
    @PutMapping("{id}")
    public MemberRspDto updateMember(
            @PathVariable Long id, @RequestBody MemberUpdateReqDto memberReqDto) {
        return memberService.updateMember(id, memberReqDto);
    }

    @ApiResponses(
            value = {
                @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true),
            })
    @Operation(summary = "회원 삭제", description = "회원 데이터 삭제 API")
    @DeleteMapping("{id}")
    public boolean deleteMember(@PathVariable Long id) {
        return memberService.deleteMember(id);
    }
}
