package com.example.saheel.Repository;

import com.example.saheel.Model.Membership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MembershipRepository extends JpaRepository<Membership,Integer> {
    Membership findMembershipById(Integer id);
}
