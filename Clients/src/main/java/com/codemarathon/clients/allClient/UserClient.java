package com.codemarathon.clients.allClient;




import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "USER")
public interface UserClient {

    @GetMapping("/api/v1/auth-users/user/{id}")
    UserResponse getUserById(@PathVariable("id") Long id);


    @PostMapping("/api/v1/users/authenticate")
    ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthRequest request);

}
