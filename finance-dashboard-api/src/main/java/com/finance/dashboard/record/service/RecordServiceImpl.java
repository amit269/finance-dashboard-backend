package com.finance.dashboard.record.service;


import com.finance.dashboard.record.constant.RecordType;
import com.finance.dashboard.user.acesscontrol.AccessControlService;
import com.finance.dashboard.user.constant.ErrorCodeEnum;
import com.finance.dashboard.record.entity.FinancialRecord;
import com.finance.dashboard.record.serviceinterfaces.RecordService;
import com.finance.dashboard.user.entity.User;
import com.finance.dashboard.record.repo.RecordRepository;
import com.finance.dashboard.user.exception.UserException;
import com.finance.dashboard.user.serviceinterfaces.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecordServiceImpl implements RecordService {

    private final RecordRepository recordRepository;
    private final AccessControlService accessControlService;
    private final UserService userService;


    public void  create(FinancialRecord record , User user) {
        accessControlService.requireAdmin(user);

        if (record.getAmount() == null || record.getAmount() <= 0) {
            throw new UserException(
                    ErrorCodeEnum.GENERIC_ERROR.getCode(),
                    "Amount must be positive",
                    HttpStatus.BAD_REQUEST
            );
        }
        if (record.getType() == null) {
            throw new UserException(
                    ErrorCodeEnum.GENERIC_ERROR.getCode(),
                    "Type is required",
                    HttpStatus.BAD_REQUEST
            );
        }
        record.setDate(LocalDate.now());
        record.setUser(user);
        log.info(" record is {}", record);
        FinancialRecord save = recordRepository.save(record);
        log.info("| saved record is  {}", save);
    }

    public List<FinancialRecord> getUser(Long userId)
    {
        List<FinancialRecord> currentUser  = recordRepository.findByUserId(userId);
        return currentUser;
    }


    public void delete(Long id, User user) {
        accessControlService.requireAdmin(user);
        FinancialRecord record = recordRepository.findById(id)
                .orElseThrow(() -> new UserException(
                        ErrorCodeEnum.USER_NOT_FOUND.getCode(),
                        ErrorCodeEnum.USER_NOT_FOUND.getMessage(),
                        HttpStatus.NOT_FOUND
                ));
        recordRepository.delete(record);
    }


    public FinancialRecord update(Long id, FinancialRecord updated, User user) {
         accessControlService.requireAdmin(user);

        FinancialRecord existing = recordRepository.findById(id)
                .orElseThrow(() ->  new UserException(
                ErrorCodeEnum.USER_NOT_FOUND.getCode(),
                ErrorCodeEnum.USER_NOT_FOUND.getMessage(),
                HttpStatus.NOT_FOUND
        ));

        if (updated.getAmount() != null && updated.getAmount() > 0) {
            existing.setAmount(updated.getAmount());
        }
        if (updated.getType() != null) {
            existing.setType(updated.getType());
        }
        if (updated.getCategory() != null && !updated.getCategory().isEmpty()) {
            existing.setCategory(updated.getCategory());
        }
        if (updated.getDescription() != null && !updated.getDescription().isEmpty()) {
            existing.setDescription(updated.getDescription());
        }

        return recordRepository.save(existing);
    }

    public List<FinancialRecord> getFiltered(Long userId, RecordType type, String category, String from, String to) {

        User user  = userService.findEntityById(userId);
        accessControlService.requireAnalystOrAbove(user);

        List<FinancialRecord> records = recordRepository.findByUserId(user.getId());
        if(records.isEmpty()){
            throw new UserException(
                    ErrorCodeEnum.USER_NOT_FOUND.getCode(),
                    ErrorCodeEnum.USER_NOT_FOUND.getMessage(),
                    HttpStatus.NOT_FOUND
            );
        }

        if (type != null) {
            records = records.stream()
                    .filter(r -> r.getType() == type)
                    .toList();
        }

        if (category != null && !category.isEmpty()) {
            records = records.stream()
                    .filter(r -> category.equalsIgnoreCase(r.getCategory()))
                    .toList();
        }

        if (from != null && to != null) {
            LocalDate fromDate = LocalDate.parse(from);
            LocalDate toDate = LocalDate.parse(to);
            records = records.stream()
                    .filter(r -> !r.getDate().isBefore(fromDate) && !r.getDate().isAfter(toDate))
                    .toList();
        }
        return records;
    }
}