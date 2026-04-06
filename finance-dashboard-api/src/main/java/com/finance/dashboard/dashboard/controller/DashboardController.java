package com.finance.dashboard.dashboard.controller;
import com.finance.dashboard.dashboard.serviceinterfaces.DashboardService;
import com.finance.dashboard.record.entity.FinancialRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;



    @GetMapping("/summary")
    public Map<String, Double> getSummary(@RequestHeader("X-User-Id") Long userId) {
        return dashboardService.getSummary(userId);
    }

    @GetMapping("/category")
    public Map<String, Double> categoryWise(@RequestHeader("X-User-Id") Long userId) {

        return dashboardService.getCategory(userId);
    }

    @GetMapping("/recent")
    public List<FinancialRecord> recent(@RequestHeader("X-User-Id") Long userId) {

        return dashboardService.getRecentRecords(userId);
    }

    @GetMapping("/monthly")
    public Map<String, Double> monthly(@RequestHeader("X-User-Id") Long userId) {
        return dashboardService.getMonthly(userId);
    }
}