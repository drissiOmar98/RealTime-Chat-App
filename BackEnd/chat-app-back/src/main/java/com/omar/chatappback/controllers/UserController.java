package com.omar.chatappback.controllers;


import com.omar.chatappback.dto.RestUser;
import com.omar.chatappback.entities.User;
import com.omar.chatappback.services.UserService;
import com.omar.chatappback.services.UserUtilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final UserUtilityService userUtilityService;


    @GetMapping("/get-authenticated-user")
    public ResponseEntity<RestUser> getAuthenticatedUser(@AuthenticationPrincipal Jwt user,
                                                         @RequestParam boolean forceResync) {
        User authenticatedUser = userUtilityService.getAuthenticatedUserWithSync(user, forceResync);
        RestUser restUser = RestUser.from(authenticatedUser);
        return ResponseEntity.ok(restUser);
    }


}
