package com.example.sharediary.member.repository;

import com.example.sharediary.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByMemberNameAndPassword(String memberName, String password);

    // Member findByMemberName(String memberName);

    Optional<Member> findByMemberName(String memberName);
}
