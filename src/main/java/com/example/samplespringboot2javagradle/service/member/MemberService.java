package com.example.samplespringboot2javagradle.service.member;

import com.example.samplespringboot2javagradle.consts.entity.MemberStatus;
import com.example.samplespringboot2javagradle.dto.member.MemberRspDto;
import com.example.samplespringboot2javagradle.dto.member.MemberSaveReqDto;
import com.example.samplespringboot2javagradle.dto.member.MemberUpdateReqDto;
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
        var member = memberReqDto.toEntity();
        member.setPassword(encodedPassword);
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
    public MemberRspDto updateMember(Long id, MemberUpdateReqDto memberReqDto) {
        var isExist = memberRepository.existsByIdAndStatus(id, MemberStatus.ACTIVE);
        if (!isExist) {
            throw new IllegalArgumentException("Member is not exist or deleted. id: " + id);
        }
        var encodedPassword = passwordEncoder.encode(memberReqDto.getPassword());
        var member = memberReqDto.toEntity(id);
        member.setPassword(encodedPassword);
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
                            member.setStatus(MemberStatus.DELETED);
                            return memberRepository.save(member);
                        })
                .map(MemberRspDto::new)
                .orElseThrow(
                        () ->
                                new IllegalArgumentException("Member is not exist or already deleted. id: " + id));
    }
}
