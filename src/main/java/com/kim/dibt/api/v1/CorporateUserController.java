package com.kim.dibt.api.v1;

import com.kim.dibt.core.utils.result.DataResult;
import com.kim.dibt.core.utils.result.Result;
import com.kim.dibt.services.corporateuser.CorporateUserService;
import com.kim.dibt.services.corporateuser.dtos.AddCorporateUserDto;
import com.kim.dibt.services.corporateuser.dtos.AddedCorporateUserDto;
import com.kim.dibt.services.corporateuser.dtos.GetDetailOfCorporateUser;
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

import java.util.List;

@RestController
@RequestMapping("/api/v1/corporate-user")
@RequiredArgsConstructor
public class CorporateUserController {
    private final CorporateUserService service;

    @PostMapping()
    public ResponseEntity<DataResult<AddedCorporateUserDto>> addPersonalUser(@RequestBody @Valid AddCorporateUserDto addCorporateUserDto) {
        var result = service.add(addCorporateUserDto);
        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.badRequest().body(result);
    }
    @GetMapping("/city/{cityId}")
    public ResponseEntity<DataResult<List<GetDetailOfCorporateUser>>> getByCityId(@PathVariable Long cityId) {
        var result = service.getByCityId(cityId);
        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.badRequest().body(result);
    }

}
