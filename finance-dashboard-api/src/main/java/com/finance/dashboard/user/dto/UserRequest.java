package com.finance.dashboard.user.dto;

import com.finance.dashboard.user.constant.Role;
import com.finance.dashboard.user.constant.UserStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;

@Data
public class UserRequest{



    private String name;
    private String email;
    private Role role;
    private UserStatus status;

}
