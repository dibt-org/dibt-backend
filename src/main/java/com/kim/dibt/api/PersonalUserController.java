package com.kim.dibt.api;

import com.kim.dibt.core.utils.result.DataResult;
import com.kim.dibt.core.utils.result.Result;
import com.kim.dibt.services.personaluser.PersonalUserService;
import com.kim.dibt.services.personaluser.dtos.AddPersonalUser;
import com.kim.dibt.services.personaluser.dtos.AddedPersonalUser;
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

    //TODO this method not tested it will be updated
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
}
