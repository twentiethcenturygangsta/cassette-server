package com.playlist.cassette.repository;

import com.playlist.cassette.entity.MemberWithdrawalLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberWithdrawalLogRepository extends JpaRepository<MemberWithdrawalLog, Long> {
}
