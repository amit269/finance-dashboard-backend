package com.finance.dashboard.record.entity;
import com.finance.dashboard.record.constant.RecordType;
import com.finance.dashboard.user.entity.User;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "financial_records")
public class FinancialRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double amount;

    @Enumerated(EnumType.STRING)
    private RecordType type;

    private String category;

    private LocalDate date;

    private String description;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}