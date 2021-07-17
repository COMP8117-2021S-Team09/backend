package com.tiffin_umbrella.first_release_1.service;

import com.tiffin_umbrella.first_release_1.dto.LoginDetailsDto;
import com.tiffin_umbrella.first_release_1.dto.Role;
import com.tiffin_umbrella.first_release_1.entity.*;
import com.tiffin_umbrella.first_release_1.repository.BuyerRepository;
import com.tiffin_umbrella.first_release_1.repository.SellerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Service
@RequiredArgsConstructor(onConstructor = @__({@Autowired, @Lazy}))
public class DefaultService {

    private final SellerRepository sellerRepository;
    private final BuyerRepository buyerRepository;

    public HttpStatus login(final LoginDetailsDto loginDetails) {
        final String actualPassword = getPassword(loginDetails);
        HttpStatus responseStatus = HttpStatus.OK;
        if (!loginDetails.getPassword().equals(actualPassword)) {
            loginDetails.setMessage("Wrong credentials (email or password mismatch)");
            loginDetails.setToken(null);
            responseStatus = HttpStatus.BAD_REQUEST;
        } else {
            loginDetails.setMessage("Login successful (email and password valid)");
            loginDetails.setToken(UUID.randomUUID().toString());
        }
        return responseStatus;
    }

    public void redirectToApiDocs(final HttpServletResponse response) {
        response.addHeader(HttpHeaders.LOCATION, "/swagger-ui.html");
        response.setStatus(HttpServletResponse.SC_TEMPORARY_REDIRECT);
    }

    private String getPassword(final LoginDetailsDto loginDetails) {
        final String password;
        if (Role.SELLER.equals(loginDetails.getRole())) {
            password = sellerRepository.findByContact_Email(loginDetails.getEmail())
                    .orElse(SellerEntity.builder().build()).getPassword();
        } else {
            password = buyerRepository.findByContact_Email(loginDetails.getEmail())
                    .orElse(BuyerEntity.builder().build()).getFirstName();
        }
        return password;
    }
}
