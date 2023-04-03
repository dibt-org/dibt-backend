package com.kim.dibt.core.handlers;

import com.kim.dibt.core.models.ApiError;
import com.kim.dibt.core.utils.constants.CoreConstants;
import com.kim.dibt.core.utils.result.ErrorDataResult;
import jakarta.annotation.Nonnull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ValidationExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(@Nonnull MethodArgumentNotValidException ex, @Nonnull HttpHeaders headers, @Nonnull HttpStatusCode status, @Nonnull WebRequest request) {
        ApiError error = new ApiError();
        error.setMessage(CoreConstants.VALIDATION_ERROR);
        error.setCode(status.value());
        ex.getBindingResult().getFieldErrors().forEach(fieldError -> error.getErrors().put(fieldError.getField(), fieldError.getDefaultMessage()));


        return new ResponseEntity<>(ErrorDataResult.of(error, CoreConstants.VALIDATION_ERROR), HttpStatus.BAD_REQUEST);
    }

}
