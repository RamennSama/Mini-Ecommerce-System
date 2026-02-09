package com.ramennsama.springboot.system.service.impl;

import com.ramennsama.springboot.system.dto.request.UserRequest;
import com.ramennsama.springboot.system.dto.response.UserResponse;
import com.ramennsama.springboot.system.entity.User;
import com.ramennsama.springboot.system.mapper.UserMapper;
import com.ramennsama.springboot.system.repository.UserRepository;
import com.ramennsama.springboot.system.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public List<UserResponse> findAll() {
        List<User> users = userRepository.findAll();
        return users.stream().map(userMapper::toUserResponse).collect(Collectors.toList());
    }

    @Override
    public User findById(Long id) {
        return this.userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public User save(UserRequest user) {
        return this.userRepository.save(userMapper.toUser(user));
    }

    @Override
    public User update(Long id, UserRequest userRequest) {
        User existingUser = findById(id);
        userMapper.updateUser(existingUser, userRequest);
        return this.userRepository.save(existingUser);
    }

    @Override
    public void deleteById(Long id) {
        this.userRepository.deleteById(id);
    }
}
