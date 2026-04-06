package com.finance.dashboard.user.serviceinterfaces;

import com.finance.dashboard.user.dto.UserRequest;
import com.finance.dashboard.user.dto.UserResponse;
import com.finance.dashboard.user.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    public UserResponse createUser(UserRequest user , Long  userId);
    public List<UserResponse> getAllUsers();
    public UserResponse getUserById(Long userID);
    public  User findEntityById(Long id);
     public UserResponse updateUser(Long id , UserRequest userRequest , Long userId);
    public  UserResponse deactivateUser(Long id ,Long userId);
}
