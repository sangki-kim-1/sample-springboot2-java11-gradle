package com.example.samplespringboot2javagradle.service.member;

import com.example.samplespringboot2javagradle.consts.entity.MemberRole;
import com.example.samplespringboot2javagradle.consts.entity.MemberStatus;
import com.example.samplespringboot2javagradle.dto.member.MemberAdminRspDto;
import com.example.samplespringboot2javagradle.dto.member.MemberSaveReqDto;
import com.example.samplespringboot2javagradle.dto.member.MemberUpdateReqDto;
import com.example.samplespringboot2javagradle.repository.member.MemberRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Member Service
 *
 * @author dongyoung.kim
 * @since 1.0
 */
@RequiredArgsConstructor
@Service
public class MemberAdminAdminServiceImpl implements MemberAdminService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public MemberAdminRspDto saveMember(MemberSaveReqDto memberReqDto) {
        var encodedPassword = passwordEncoder.encode(memberReqDto.getPassword());
        var roleList = List.of(MemberRole.USER);
        var member =
                memberReqDto.toEntity().toBuilder().password(encodedPassword).roleList(roleList).build();
        var savedMember = memberRepository.save(member);
        return new MemberAdminRspDto(savedMember);
    }

    @Override
    public MemberAdminRspDto findMember(Long id) {
        return memberRepository
                .findById(id)
                .map(MemberAdminRspDto::new)
                .orElseGet(MemberAdminRspDto::new);
    }

    @Override
    public List<MemberAdminRspDto> findAllMembers() {
        var members = new ArrayList<MemberAdminRspDto>();
        memberRepository.findAll().forEach(member -> members.add(new MemberAdminRspDto(member)));
        return members;
    }

    @Override
    public MemberAdminRspDto updateMember(Long id, MemberUpdateReqDto reqDto) {
        var member =
                memberRepository
                        .findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("Member is not exist. id: " + id));

        if (StringUtils.isNoneBlank(reqDto.getEmail())) {
            member = member.toBuilder().email(reqDto.getEmail()).build();
        }
        if (StringUtils.isNoneBlank(reqDto.getPassword())) {
            var encodedPassword = passwordEncoder.encode(reqDto.getPassword());
            member = member.toBuilder().password(encodedPassword).build();
        }
        return new MemberAdminRspDto(memberRepository.save(member));
    }

    @Transactional
    @Override
    public void deleteMember(Long id) {
        var member =
                memberRepository
                        .findById(id)
                        .filter(m -> MemberStatus.ACTIVE.equals(m.getStatus()))
                        .orElseThrow(() -> new IllegalArgumentException("Member is not exist. id: " + id));
        memberRepository.delete(member);
    }
}
