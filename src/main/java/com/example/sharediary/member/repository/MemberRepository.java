package com.example.sharediary.member.repository;

import com.example.sharediary.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByMemberNameAndPassword(String memberName, String password);
    Optional<Member> findByMemberName(String name);

}
