package com.tiffin_umbrella.first_release_1.service;

import com.tiffin_umbrella.first_release_1.dto.LoginDetailsDto;
import com.tiffin_umbrella.first_release_1.entity.Role;
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
import java.util.concurrent.atomic.AtomicReference;

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
            loginDetails.setId(null);
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
        final AtomicReference<String> password = new AtomicReference<>();
        if (Role.SELLER.equals(loginDetails.getRole())) {
            sellerRepository.findByContact_Email(loginDetails.getEmail()).ifPresent(seller -> {
                password.set(seller.getPassword());
                loginDetails.setId(seller.getId());
            });
        } else {
            buyerRepository.findByContact_Email(loginDetails.getEmail()).ifPresent(buyer -> {
                password.set(buyer.getFirstName());
                loginDetails.setId(buyer.getId());
            });
        }
        return password.get();
    }
}
