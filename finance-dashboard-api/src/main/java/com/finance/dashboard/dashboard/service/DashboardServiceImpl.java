package com.finance.dashboard.dashboard.service;

import com.finance.dashboard.record.constant.RecordType;
import com.finance.dashboard.dashboard.serviceinterfaces.DashboardService;
import com.finance.dashboard.record.entity.FinancialRecord;
import com.finance.dashboard.user.acesscontrol.AccessControlService;
import com.finance.dashboard.user.entity.User;
import com.finance.dashboard.user.serviceinterfaces.UserService;
import com.finance.dashboard.record.repo.RecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final UserService userService;
    private final RecordRepository recordRepository;
    private  final AccessControlService accessControlService;

    private List<FinancialRecord> validateAndGetRecords(Long userId) {

        User user = userService.findEntityById(userId);

        accessControlService.requireAnyActiveRole(user);

        return recordRepository.findByUserId(userId);
    }


    public Map<String, Double> getSummary(Long userId) {
        User user  = userService.findEntityById(userId);
        accessControlService.requireAnalystOrAbove(user);

        List<FinancialRecord> records = validateAndGetRecords(userId);

        double income = getTotalIncome(records);
        double expense = getTotalExpense(records);

        Map<String, Double> result = new HashMap<>();
        result.put("totalIncome", income);
        result.put("totalExpense", expense);
        result.put("netBalance", income - expense);

        return result;
    }

    public Map<String, Double> getCategory(Long userId) {
        List<FinancialRecord> records = validateAndGetRecords(userId);
        return getCategoryWise(records);
    }
    public List<FinancialRecord> getRecentRecords(Long userId) {
        List<FinancialRecord> records = validateAndGetRecords(userId);
        return getRecent(records);
    }

    public Map<String, Double> getMonthly(Long userId) {
        List<FinancialRecord> records = validateAndGetRecords(userId);
        return getMonthlySummary(records);
    }

    public Map<String, Double> getCategoryWise(List<FinancialRecord> records) {
        Map<String, Double> map = new HashMap<>();
        for (FinancialRecord r : records) {
            map.put(r.getCategory(),
                    map.getOrDefault(r.getCategory(), 0.0) + r.getAmount());
        }
        return map;
    }

    public List<FinancialRecord> getRecent(List<FinancialRecord> records) {
        records.sort((a, b) -> b.getDate().compareTo(a.getDate()));
        return records.size() > 5 ? records.subList(0, 5) : records;
    }

    public double getTotalIncome(List<FinancialRecord> records) {
        double total = 0;
        for (FinancialRecord r : records) {
            if (r.getType() == RecordType.INCOME  && r.getAmount() != null) {
                total += r.getAmount();
            }

        }
        return total;
    }

    public double getTotalExpense(List<FinancialRecord> records) {
        double total = 0;
        for (FinancialRecord r : records) {
            if (r.getType() == RecordType.EXPENSE && r.getAmount() != null) {
                total += r.getAmount();
            }
        }
        return total;
    }

    public Map<String, Double> getMonthlySummary(List<FinancialRecord> records) {
        Map<String, Double> map = new HashMap<>();
        for (FinancialRecord r : records) {
            String month = r.getDate().getMonth().toString();
            map.put(month,
                    map.getOrDefault(month, 0.0) + r.getAmount());
        }
        return map;
    }
}