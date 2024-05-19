package com.example.samplespringboot2javagradle.entity.member;

import com.example.samplespringboot2javagradle.consts.entity.MemberRole;
import com.example.samplespringboot2javagradle.consts.entity.MemberStatus;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;

/**
 * Member Entity
 *
 * @author dongyoung.kim
 * @since 1.0
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "members")
public class Member implements Serializable {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Email
    @Comment("이메일")
    @Column(unique = true, nullable = false, length = 100)
    private String email;

    @Setter
    @Comment("비밀번호")
    @Column(nullable = false, length = 100)
    private String password;

    @Convert(converter = MemberRoleListConverter.class)
    @Comment("권한 목록")
    @Column(nullable = false, length = 50)
    private List<MemberRole> roleList;

    @Setter
    @Convert(converter = MemberStatusConverter.class)
    @Comment("회원 상태")
    @Column(nullable = false, length = 1)
    private MemberStatus status;
}
