package com.ramennsama.springboot.system.dto.response;

import com.ramennsama.springboot.system.entity.enumtype.Role;

import lombok.Data;

@Data
public class UserResponse {
    
    private Long id;
    
    private String fullName;
    
    private String email;
    
    private Role role;
}
