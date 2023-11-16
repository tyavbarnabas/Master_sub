package com.codemarathon.clients.allClient;



import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "User")
public interface UserClient {

    @GetMapping("/api/v1/auth-users/user/{id}")
    UserResponse getUserById(@PathVariable("id") Long id);

}
