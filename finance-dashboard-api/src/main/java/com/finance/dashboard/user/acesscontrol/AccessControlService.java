package com.finance.dashboard.user.acesscontrol;
import com.finance.dashboard.user.constant.ErrorCodeEnum;
import com.finance.dashboard.user.constant.Role;
import com.finance.dashboard.user.constant.UserStatus;
import com.finance.dashboard.user.entity.User;
import com.finance.dashboard.user.exception.UserException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class AccessControlService {

    // Block any INACTIVE user from doing anything
    public void checkActive(User user) {
        if (user.getStatus() == UserStatus.INACTIVE) {
            throw new UserException(
                    ErrorCodeEnum.ACCESS_DENIED.getCode(),
                    "Inactive users cannot perform this action",
                    HttpStatus.FORBIDDEN
            );
        }
    }

    // Only ADMIN allowed
    public void requireAdmin(User user) {
        checkActive(user);
        if (user.getRole() != Role.ADMIN) {
            throw new UserException(
                    ErrorCodeEnum.ACCESS_DENIED.getCode(),
                    ErrorCodeEnum.ACCESS_DENIED.getMessage(),
                    HttpStatus.FORBIDDEN
            );
        }
    }

    // ADMIN or ANALYST allowed — VIEWER blocked
    public void requireAnalystOrAbove(User user) {
        checkActive(user);
        if (user.getRole() == Role.VIEWER) {
            throw new UserException(
                    ErrorCodeEnum.ACCESS_DENIED.getCode(),
                    ErrorCodeEnum.ACCESS_DENIED.getMessage(),
                    HttpStatus.FORBIDDEN
            );
        }
    }

    // Any active user allowed (ADMIN, ANALYST, VIEWER)
    public void requireAnyActiveRole(User user) {
        checkActive(user);
    }
}