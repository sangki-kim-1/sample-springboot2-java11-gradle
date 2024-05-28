package com.example.samplespringboot2javagradle.dto.member;

import com.example.samplespringboot2javagradle.consts.entity.MemberRole;
import com.example.samplespringboot2javagradle.consts.entity.MemberStatus;
import com.example.samplespringboot2javagradle.entity.member.Member;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Member Response Dto
 *
 * @author dongyoung.kim
 * @since 1.0
 */
@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberRspDto {

    private Long id;
    private String email;
    private List<MemberRole> roleList;
    private MemberStatus status;

    public MemberRspDto(Member member) {
        this.id = member.getId();
        this.email = member.getEmail();
        this.roleList = member.getRoleList();
        this.status = member.getStatus();
    }
}
