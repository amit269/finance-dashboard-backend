package com.finance.dashboard.record.serviceinterfaces;

import com.finance.dashboard.record.constant.RecordType;
import com.finance.dashboard.record.entity.FinancialRecord;
import com.finance.dashboard.user.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RecordService {
    public void create(FinancialRecord record , User user);
    public List<FinancialRecord> getUser(Long userId);
    public void delete(Long id, User user);
    public FinancialRecord update(Long id, FinancialRecord updated, User user);
    public  List<FinancialRecord> getFiltered(Long userId, RecordType type, String category, String from, String to);
}
