package com.tiffin_umbrella.first_release_1.controller;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@CrossOrigin
@RestController
@Hidden
public class DefaultController {

    @GetMapping(value = {"/", "/docs", "/api-docs"})
    public void defaultLandingController(final HttpServletResponse response) {
        response.addHeader(HttpHeaders.LOCATION, "/swagger-ui.html");
        response.setStatus(HttpServletResponse.SC_TEMPORARY_REDIRECT);
    }
}