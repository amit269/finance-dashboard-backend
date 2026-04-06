package com.finance.dashboard.record.repo;

import com.finance.dashboard.record.constant.RecordType;
import com.finance.dashboard.record.entity.FinancialRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface RecordRepository extends JpaRepository<FinancialRecord , Long> {

    List<FinancialRecord> findByUserId(Long userId);
    List<FinancialRecord> findByUserIdAndType(Long userId, RecordType type);
    List<FinancialRecord> findByUserIdAndCategory(Long userId, String category);
    List<FinancialRecord> findByUserIdAndDateBetween(Long userId, LocalDate from, LocalDate to);
}
