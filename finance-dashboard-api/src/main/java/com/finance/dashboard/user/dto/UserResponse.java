package com.finance.dashboard.user.dto;

import com.finance.dashboard.user.constant.Role;
import com.finance.dashboard.user.constant.UserStatus;
import lombok.Data;

@Data
public class UserResponse {
    private Long id;
    private String name;
    private String email;
    private Role role;
    private UserStatus status;
}
