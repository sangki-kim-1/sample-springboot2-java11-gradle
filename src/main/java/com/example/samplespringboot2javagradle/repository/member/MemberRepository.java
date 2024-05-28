package com.example.samplespringboot2javagradle.repository.member;

import com.example.samplespringboot2javagradle.consts.entity.MemberStatus;
import com.example.samplespringboot2javagradle.entity.member.Member;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

/**
 * Member Repository
 *
 * @author dongyoung.kim
 * @since 1.0
 */
public interface MemberRepository extends CrudRepository<Member, Long> {
    Optional<Member> findByEmailAndStatus(String email, MemberStatus status);

    boolean existsByIdAndStatus(Long id, MemberStatus status);
}
