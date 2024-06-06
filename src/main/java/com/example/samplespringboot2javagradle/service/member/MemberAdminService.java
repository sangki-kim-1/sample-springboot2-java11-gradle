package com.example.samplespringboot2javagradle.service.member;

import com.example.samplespringboot2javagradle.dto.member.MemberAdminRspDto;
import com.example.samplespringboot2javagradle.dto.member.MemberSaveReqDto;
import com.example.samplespringboot2javagradle.dto.member.MemberUpdateReqDto;
import java.util.List;

/**
 *
 *
 * <h3>Member Service</h3>
 *
 * @author dongyoung.kim
 * @since 1.0
 */
public interface MemberAdminService {
    MemberAdminRspDto saveMember(MemberSaveReqDto reqDto);

    MemberAdminRspDto findMember(Long id);

    List<MemberAdminRspDto> findAllMembers();

    MemberAdminRspDto updateMember(Long id, MemberUpdateReqDto reqDto);

    void deleteMember(Long id);
}
