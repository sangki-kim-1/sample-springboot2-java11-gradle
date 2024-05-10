package com.example.samplespringboot2javagradle.service.member;

import com.example.samplespringboot2javagradle.dto.member.MemberRspDto;
import com.example.samplespringboot2javagradle.dto.member.MemberSaveReqDto;
import com.example.samplespringboot2javagradle.dto.member.MemberUpdateReqDto;
import com.example.samplespringboot2javagradle.entity.MemberRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    public MemberRspDto saveMember(MemberSaveReqDto memberReqDto) {
        var member = memberRepository.save(memberReqDto.toEntity());
        return new MemberRspDto(member);
    }

    public MemberRspDto findMember(Long id) {
        return memberRepository.findById(id).map(MemberRspDto::new).orElseGet(MemberRspDto::new);
    }

    public List<MemberRspDto> findAllMembers() {
        var members = new ArrayList<MemberRspDto>();
        memberRepository.findAll().forEach(member -> members.add(new MemberRspDto(member)));
        return members;
    }

    public MemberRspDto updateMember(Long id, MemberUpdateReqDto memberReqDto) {
        var member = memberRepository.save(memberReqDto.toEntity(id));
        return new MemberRspDto(member);
    }

    public boolean deleteMember(Long id) {
        memberRepository.deleteById(id);
        return true;
    }
}
