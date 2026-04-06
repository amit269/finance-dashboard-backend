package com.finance.dashboard.dashboard.serviceinterfaces;

import com.finance.dashboard.record.entity.FinancialRecord;

import java.util.List;
import java.util.Map;

public interface DashboardService {
    public Map<String, Double> getSummary(Long userId);
    public Map<String, Double> getCategory(Long userId);
    public List<FinancialRecord> getRecentRecords(Long userId);
    public Map<String, Double> getMonthly(Long userId);

}
