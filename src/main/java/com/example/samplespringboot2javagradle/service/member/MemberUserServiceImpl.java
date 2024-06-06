package com.example.samplespringboot2javagradle.service.member;

import com.example.samplespringboot2javagradle.consts.entity.MemberStatus;
import com.example.samplespringboot2javagradle.dto.member.MemberChangePasswordReqDto;
import com.example.samplespringboot2javagradle.dto.member.MemberUserRspDto;
import com.example.samplespringboot2javagradle.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 *
 * <h3>Member User Service</h3>
 *
 * @author dongyoung.kim
 * @since 1.0
 */
@RequiredArgsConstructor
@Service
public class MemberUserServiceImpl implements MemberUserService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public MemberUserRspDto get(Long id) {
        var member =
                memberRepository
                        .findById(id)
                        .filter(m -> MemberStatus.ACTIVE.equals(m.getStatus()))
                        .orElseThrow(
                                () -> new IllegalArgumentException("Member is not exist or withdraw. id: " + id));
        return new MemberUserRspDto(member);
    }

    @Transactional
    @Override
    public MemberUserRspDto changePassword(Long id, MemberChangePasswordReqDto memberReqDto) {
        var member =
                memberRepository
                        .findById(id)
                        .filter(m -> MemberStatus.ACTIVE.equals(m.getStatus()))
                        .orElseThrow(
                                () -> new IllegalArgumentException("Member is not exist or withdraw. id: " + id));
        if (!passwordEncoder.matches(memberReqDto.getCurrentPassword(), member.getPassword())) {
            throw new IllegalArgumentException("Password is not matched. id: " + id);
        }
        var encodedPassword = passwordEncoder.encode(memberReqDto.getNewPassword());
        member = member.toBuilder().password(encodedPassword).build();
        var updatedMember = memberRepository.save(member);
        return new MemberUserRspDto(updatedMember);
    }

    @Override
    public MemberUserRspDto withdraw(Long id) {
        var member =
                memberRepository
                        .findById(id)
                        .filter(m -> MemberStatus.ACTIVE.equals(m.getStatus()))
                        .orElseThrow(
                                () -> new IllegalArgumentException("Member is not exist or withdraw. id: " + id));
        member = member.toBuilder().status(MemberStatus.WITHDRAW).build();
        return new MemberUserRspDto(memberRepository.save(member));
    }
}
