package com.tiffin_umbrella.first_release_1.controller;

import com.tiffin_umbrella.first_release_1.adapter.ImageAdapter;
import com.tiffin_umbrella.first_release_1.dto.ImageDto;
import com.tiffin_umbrella.first_release_1.entity.ImageEntity;
import com.tiffin_umbrella.first_release_1.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;

import static org.springframework.http.MediaType.*;

@CrossOrigin
@RestController
@RequiredArgsConstructor(onConstructor = @__({@Autowired, @Lazy}))
public class ImageController {

    private final ImageService imageService;

    @PostMapping(value = "/images",
            consumes = MULTIPART_FORM_DATA_VALUE,
            produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ImageDto> create(
            @RequestPart(name = "image") final MultipartFile image,
            @RequestParam(name = "comment") final String comment) {
        final ImageEntity imageSaved = imageService.create(image, comment);
        return new ResponseEntity<>(ImageAdapter.adapt(imageSaved), HttpStatus.OK);
    }

    @GetMapping(value = "/images",
            produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<ImageDto>> getAll() {
        return new ResponseEntity<>(ImageAdapter.adapt(imageService.getAll()), HttpStatus.OK);
    }

    @GetMapping(value = "/images/{imageId}", produces = IMAGE_JPEG_VALUE)//referred by create
    public byte[] getImageBytes(@PathVariable("imageId") final String imageId) {
        return imageService.getImageByteArray(imageId);
    }

    @SneakyThrows
    @GetMapping(value = "/image", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getRandomImage(@RequestParam(name = "size", defaultValue = "500") final int size) {
        return imageService.getRandomImageForSize(size);
    }
}