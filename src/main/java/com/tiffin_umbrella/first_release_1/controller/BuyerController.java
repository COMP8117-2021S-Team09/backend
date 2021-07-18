package com.tiffin_umbrella.first_release_1.controller;

import com.tiffin_umbrella.first_release_1.dto.BuyerDto;
import com.tiffin_umbrella.first_release_1.service.BuyerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@CrossOrigin
@RestController
@RequiredArgsConstructor(onConstructor = @__({@Autowired, @Lazy}))
public class BuyerController {

    private final BuyerService buyerService;

    @PostMapping(
            value = {"/buyers", "/post_buyer"},
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE)
    public void createBuyer(@RequestBody final BuyerDto buyer) {
        buyerService.createBuyer(buyer);
    }
}
