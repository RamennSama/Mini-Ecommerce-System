package com.ramennsama.springboot.system.service;

import com.ramennsama.springboot.system.dto.request.UserRequest;
import com.ramennsama.springboot.system.dto.response.UserResponse;
import com.ramennsama.springboot.system.entity.User;

import java.util.List;

public interface UserService {
    List<UserResponse> findAll();

    User findById(Long id);

    User save(UserRequest user);

    User update(Long id, UserRequest userRequest);

    void deleteById(Long id);
}
