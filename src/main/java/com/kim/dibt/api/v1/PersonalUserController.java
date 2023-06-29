package com.kim.dibt.api.v1;

import com.kim.dibt.core.utils.result.DataResult;
import com.kim.dibt.core.utils.result.Result;
import com.kim.dibt.services.personaluser.PersonalUserService;
import com.kim.dibt.services.personaluser.dtos.AddPersonalUser;
import com.kim.dibt.services.personaluser.dtos.AddedPersonalUser;
import com.kim.dibt.services.personaluser.dtos.DetailOfUserDto;
import com.kim.dibt.services.personaluser.dtos.UpdatePersonalUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/personal-user")
@RequiredArgsConstructor
public class PersonalUserController {
    private final PersonalUserService personalUserService;

    @PostMapping()
    public ResponseEntity<DataResult<AddedPersonalUser>> addPersonalUser(@RequestBody @Valid AddPersonalUser addPersonalUser, HttpServletRequest request) {
        var result = personalUserService.addPersonalUser(addPersonalUser, request);
        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.badRequest().body(result);
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<Result> updatePersonalUser(
            @RequestBody @Valid UpdatePersonalUser updatePersonalUser,
            @PathVariable long userId,
            HttpServletRequest request) {
        var result = personalUserService.updatePersonalUser(updatePersonalUser, userId, request);
        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.badRequest().body(result);
    }

    @GetMapping("/detail")
    public ResponseEntity<DataResult<DetailOfUserDto>> getDetailOfUser() {
        var result = personalUserService.getDetailOfUser();
        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.badRequest().body(result);
    }

    @GetMapping("/detail/{username}")
    public ResponseEntity<DataResult<DetailOfUserDto>> getDetailOfUser(@PathVariable String username) {
        var result = personalUserService.getDetailOfUser(username);
        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.badRequest().body(result);
    }

    @GetMapping("/is-verified-user")
    public ResponseEntity<Result> isVerifiedUser() {
        var result = personalUserService.isVerifiedUser();
        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.badRequest().body(result);
    }

}
