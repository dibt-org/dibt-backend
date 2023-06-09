package com.kim.dibt.api.v1;

import com.kim.dibt.core.utils.result.DataResult;
import com.kim.dibt.core.utils.result.Result;
import com.kim.dibt.security.models.RoleType;
import com.kim.dibt.services.user.UserService;
import com.kim.dibt.services.user.dtos.SearchUserDto;
import com.kim.dibt.services.user.dtos.UpdateAboutUserDto;
import com.kim.dibt.services.user.dtos.UpdateEmailUserDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/add-role-to-user")
    public ResponseEntity<Result> addRoleToUser(@RequestParam Long userId, @RequestParam RoleType roleType) {
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

    @PatchMapping("/just-update-about")
    public ResponseEntity<DataResult<UpdateAboutUserDto>> update(@RequestBody @Valid UpdateAboutUserDto updateAboutUserDto) {
        var result = userService.update(updateAboutUserDto);
        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.badRequest().body(result);
    }

    @GetMapping("/query")
    public ResponseEntity<DataResult<List<String>>> query(@RequestParam String query) {
        var result = userService.query(query);
        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.badRequest().body(result);
    }

    @GetMapping("/search")
    public ResponseEntity<DataResult<List<SearchUserDto>>> search(@RequestParam String query) {
        var result = userService.search(query);
        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.badRequest().body(result);
    }
}
