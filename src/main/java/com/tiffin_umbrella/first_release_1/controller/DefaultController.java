package com.tiffin_umbrella.first_release_1.controller;

import com.tiffin_umbrella.first_release_1.dto.LoginDetailsDto;
import com.tiffin_umbrella.first_release_1.service.DefaultService;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@CrossOrigin
@RestController
@Slf4j
@RequiredArgsConstructor(onConstructor = @__({@Autowired, @Lazy}))
public class DefaultController {

    private final DefaultService defaultService;

    @Hidden
    @GetMapping(value = {"/", "/docs", "/api-docs"})
    public void defaultLandingController(final HttpServletResponse response) {
        defaultService.redirectToApiDocs(response);
    }

    @PostMapping(value = "/login",
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<LoginDetailsDto> login(
            @RequestBody @Valid final LoginDetailsDto loginDetails) {
        final HttpStatus responseStatus = defaultService.login(loginDetails);
        log.info("/login attempt with details: {} status: {}", loginDetails, responseStatus);
        return new ResponseEntity<>(loginDetails, responseStatus);
    }
}