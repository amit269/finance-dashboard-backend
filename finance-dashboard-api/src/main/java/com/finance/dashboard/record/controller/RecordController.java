package com.finance.dashboard.record.controller;
import com.finance.dashboard.record.constant.RecordType;
import com.finance.dashboard.record.entity.FinancialRecord;
import com.finance.dashboard.record.serviceinterfaces.RecordService;

import com.finance.dashboard.user.entity.User;
import com.finance.dashboard.user.serviceinterfaces.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/records")
public class RecordController {


    private final UserService userService;
    private final RecordService recordService;


    @PostMapping
    public ResponseEntity<String> create(@RequestBody FinancialRecord record,
                                         @RequestHeader("X-User-Id") Long userId) {
        log.info("Record id {} and User id {}",record,userId);
        User getUserById = userService.findEntityById(userId);
        recordService.create(record,getUserById);
        return ResponseEntity.status(HttpStatus.CREATED).body("Record created ");
    }
    @GetMapping
    public List<FinancialRecord> get(  @RequestHeader("X-User-Id") Long userId,
                                     @RequestParam(required = false) RecordType type,
                                     @RequestParam(required = false) String category,
                                     @RequestParam(required = false) String from,
                                     @RequestParam(required = false) String to) {

        return recordService.getFiltered(userId, type, category, from, to);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @RequestHeader("X-User-Id")  Long userId) {

        User user  = userService.findEntityById(userId);
        recordService.delete(id,user);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public FinancialRecord update(@PathVariable Long id,
                                  @RequestBody FinancialRecord record,
                                  @RequestHeader("X-User-Id")  Long userId){
        User entityById = userService.findEntityById(userId);
        FinancialRecord financialRecordUpdated = recordService.update(id, record, entityById);
        return financialRecordUpdated;
    }
}