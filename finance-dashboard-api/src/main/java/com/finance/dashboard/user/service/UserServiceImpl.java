package com.finance.dashboard.user.service;

import com.finance.dashboard.user.acesscontrol.AccessControlService;
import com.finance.dashboard.user.constant.ErrorCodeEnum;
import com.finance.dashboard.user.constant.Role;
import com.finance.dashboard.user.constant.UserStatus;
import com.finance.dashboard.user.dto.UserRequest;
import com.finance.dashboard.user.dto.UserResponse;
import com.finance.dashboard.user.entity.User;
import com.finance.dashboard.user.exception.UserException;
import com.finance.dashboard.user.repo.UserRepository;
import com.finance.dashboard.user.serviceinterfaces.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AccessControlService accessControlService;


    private UserResponse toResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole());
        response.setStatus(user.getStatus());
        return response;
    }

    @Override
    public UserResponse createUser(UserRequest userRequest  , Long userId) {
        User currentUser = findEntityById(userId);
        if(currentUser.getRole() != Role.ADMIN){
            throw new UserException(
                    ErrorCodeEnum.ACCESS_DENIED.getCode(),
                    ErrorCodeEnum.ACCESS_DENIED.getMessage(),
                    HttpStatus.FORBIDDEN
            );
        }
        if (userRequest.getEmail() == null || userRequest.getEmail().isEmpty()) {
            throw new UserException(
                    ErrorCodeEnum.EMAIL_NOT_FOUND.getCode(),
                    ErrorCodeEnum.EMAIL_NOT_FOUND.getMessage(),
                    HttpStatus.BAD_REQUEST
            );
        }
        if (userRepository.findByEmail(userRequest.getEmail()).isPresent()) {
            throw new UserException(
                    ErrorCodeEnum.USER_ALREADY_EXISTS.getCode(),
                    ErrorCodeEnum.USER_ALREADY_EXISTS.getMessage(),
                    HttpStatus.CONFLICT
            );
        }

        User user = new User();
        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());
        user.setRole(userRequest.getRole());
        user.setStatus(userRequest.getStatus());

        User saved = userRepository.save(user);
        log.info("User created with id {}", saved.getId());
        return toResponse(saved);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public UserResponse getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(
                        ErrorCodeEnum.USER_NOT_FOUND.getCode(),
                        ErrorCodeEnum.USER_NOT_FOUND.getMessage(),
                        HttpStatus.NOT_FOUND
                ));
        return toResponse(user);
    }

    @Override
    public User findEntityById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserException(
                        ErrorCodeEnum.USER_NOT_FOUND.getCode(),
                        ErrorCodeEnum.USER_NOT_FOUND.getMessage(),
                        HttpStatus.NOT_FOUND
                ));
    }
    @Override
    public UserResponse updateUser(Long id, UserRequest userRequest, Long userId) {
        User currentUser = findEntityById(userId);

        if(currentUser.getRole() != Role.ADMIN){
            throw new UserException(
                    ErrorCodeEnum.ACCESS_DENIED.getCode(),
                    ErrorCodeEnum.ACCESS_DENIED.getMessage(),
                    HttpStatus.FORBIDDEN
            );
        }
        User existingUser = findEntityById(id);

        if (userRequest.getName() != null && !userRequest.getName().isEmpty()) {
            existingUser.setName(userRequest.getName());
        }
        if (userRequest.getEmail() != null && !userRequest.getEmail().isEmpty()) {
            existingUser.setEmail(userRequest.getEmail());
        }
        if (userRequest.getRole() != null) {
            existingUser.setRole(userRequest.getRole());
        }
        if (userRequest.getStatus() != null) {
            existingUser.setStatus(userRequest.getStatus());
        }

        User updated = userRepository.save(existingUser);
        log.info("User updated with id {}", updated.getId());
        return toResponse(updated);
    }



    @Override
    public UserResponse deactivateUser(Long id ,Long userId) {
        User currentUser = findEntityById(userId);

        if(currentUser.getRole() != Role.ADMIN){
            throw new UserException(
                    ErrorCodeEnum.ACCESS_DENIED.getCode(),
                    ErrorCodeEnum.ACCESS_DENIED.getMessage(),
                    HttpStatus.FORBIDDEN
            );
        }
        User user = findEntityById(id);
        accessControlService.checkActive(user);
        user.setStatus(UserStatus.INACTIVE);
        User saved = userRepository.save(user);
        log.info("User deactivated with id {}", saved.getId());
        return toResponse(saved);
    }
}