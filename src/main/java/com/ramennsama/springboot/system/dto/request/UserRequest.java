package com.ramennsama.springboot.system.dto.request;

import com.ramennsama.springboot.system.entity.enumtype.Role;

import lombok.Data;

@Data
public class UserRequest {
    
    private String fullName;
    
    private String email;
    
    private String password;
    
    private Role role;
}
