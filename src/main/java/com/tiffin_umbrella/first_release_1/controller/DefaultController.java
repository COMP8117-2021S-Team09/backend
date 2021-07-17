package com.tiffin_umbrella.first_release_1.controller;

import com.tiffin_umbrella.first_release_1.Presentation_Layer.LoginDetailsDto;
import com.tiffin_umbrella.first_release_1.Presentation_Layer.Role;
import com.tiffin_umbrella.first_release_1.entity.BuyerEntity;
import com.tiffin_umbrella.first_release_1.entity.SellerEntity;
import com.tiffin_umbrella.first_release_1.repository.BuyerRepository;
import com.tiffin_umbrella.first_release_1.repository.SellerRepository;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@CrossOrigin
@RestController
public class DefaultController {

    @Autowired
    private SellerRepository sellerRepository;
    @Autowired
    private BuyerRepository buyerRepository;

    @Hidden
    @GetMapping(value = {"/", "/docs", "/api-docs"})
    public void defaultLandingController(final HttpServletResponse response) {
        response.addHeader(HttpHeaders.LOCATION, "/swagger-ui.html");
        response.setStatus(HttpServletResponse.SC_TEMPORARY_REDIRECT);
    }

    @PostMapping(value = "/login",
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<LoginDetailsDto> login(@RequestBody @Valid final LoginDetailsDto loginDetails) {
        String password;
        HttpStatus responseStatus = HttpStatus.OK;
        if (Role.SELLER.equals(loginDetails.getRole())) {
            password = sellerRepository.findByContact_Email(loginDetails.getEmail())
                    .orElse(SellerEntity.builder().build()).getPassword();
        } else {
            password = buyerRepository.findByContact_Email(loginDetails.getEmail())
                    .orElse(BuyerEntity.builder().build()).getFirstName();
        }
        if (!loginDetails.getPassword().equals(password)) {
            loginDetails.setMessage("Wrong credentials (email or password mismatch)");
            loginDetails.setToken(null);
            responseStatus = HttpStatus.BAD_REQUEST;
        } else {
            loginDetails.setMessage("Login successful (email and password valid)");
            loginDetails.setToken(UUID.randomUUID().toString());
        }
        return new ResponseEntity<>(loginDetails, responseStatus);
    }
}