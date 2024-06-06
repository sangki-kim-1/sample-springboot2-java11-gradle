package com.example.samplespringboot2javagradle.service.member;

import com.example.samplespringboot2javagradle.consts.entity.MemberRole;
import com.example.samplespringboot2javagradle.consts.entity.MemberStatus;
import com.example.samplespringboot2javagradle.dto.member.MemberChangePasswordReqDto;
import com.example.samplespringboot2javagradle.dto.member.MemberRspDto;
import com.example.samplespringboot2javagradle.dto.member.MemberSaveReqDto;
import com.example.samplespringboot2javagradle.repository.member.MemberRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
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
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberRspDto saveMember(MemberSaveReqDto memberReqDto) {
        var encodedPassword = passwordEncoder.encode(memberReqDto.getPassword());
        var roleList = List.of(MemberRole.USER);
        var member =
                memberReqDto.toEntity().toBuilder().password(encodedPassword).roleList(roleList).build();
        var savedMember = memberRepository.save(member);
        return new MemberRspDto(savedMember);
    }

    public MemberRspDto findMember(Long id) {
        return memberRepository.findById(id).map(MemberRspDto::new).orElseGet(MemberRspDto::new);
    }

    public List<MemberRspDto> findAllMembers() {
        var members = new ArrayList<MemberRspDto>();
        memberRepository.findAll().forEach(member -> members.add(new MemberRspDto(member)));
        return members;
    }

    @Transactional
    public MemberRspDto changePassword(Long id, MemberChangePasswordReqDto memberReqDto) {
        var member =
                memberRepository
                        .findById(id)
                        .filter(m -> MemberStatus.ACTIVE.equals(m.getStatus()))
                        .orElseThrow(
                                () -> new IllegalArgumentException("Member is not exist or deleted. id: " + id));
        if (!passwordEncoder.matches(memberReqDto.getCurrentPassword(), member.getPassword())) {
            throw new IllegalArgumentException("Password is not matched. id: " + id);
        }
        var encodedPassword = passwordEncoder.encode(memberReqDto.getNewPassword());
        member = member.toBuilder().password(encodedPassword).build();
        var updatedMember = memberRepository.save(member);
        return new MemberRspDto(updatedMember);
    }

    @Transactional
    public MemberRspDto deleteMember(Long id) {
        return memberRepository
                .findById(id)
                .filter(member -> MemberStatus.ACTIVE.equals(member.getStatus()))
                .map(
                        member -> {
                            member.toBuilder().status(MemberStatus.DELETED).build();
                            return memberRepository.save(member);
                        })
                .map(MemberRspDto::new)
                .orElseThrow(
                        () ->
                                new IllegalArgumentException("Member is not exist or already deleted. id: " + id));
    }
}
