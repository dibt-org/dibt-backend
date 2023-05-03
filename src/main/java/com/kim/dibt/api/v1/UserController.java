package com.kim.dibt.api.v1;

import com.kim.dibt.core.utils.result.Result;
import com.kim.dibt.security.models.RoleType;
import com.kim.dibt.services.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/add-role-to-user")
    public ResponseEntity<Result> addRoleToUser(@RequestParam Long userId,@RequestParam RoleType roleType) {
        var result = userService.addRoleToUser(userId, roleType);
        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.badRequest().body(result);
    }
}
