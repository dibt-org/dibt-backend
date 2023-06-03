package com.kim.dibt.api.v1;

import com.kim.dibt.core.utils.result.DataResult;
import com.kim.dibt.core.utils.result.Result;
import com.kim.dibt.security.models.RoleType;
import com.kim.dibt.services.post.dtos.UpdatePostDto;
import com.kim.dibt.services.post.dtos.UpdatedPostDto;
import com.kim.dibt.services.user.UserService;
import com.kim.dibt.services.user.dtos.UpdateEmailUserDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @PatchMapping("/just-update-email")
    public ResponseEntity<DataResult<UpdateEmailUserDto>> update(@RequestBody @Valid UpdateEmailUserDto updateEmailUserDto) {
        var result = userService.update(updateEmailUserDto);
        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.badRequest().body(result);
    }
}
