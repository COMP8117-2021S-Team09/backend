package com.tiffin_umbrella.first_release_1.service;

import com.tiffin_umbrella.first_release_1.adapter.ImageAdapter;
import com.tiffin_umbrella.first_release_1.common.BadRequestException;
import com.tiffin_umbrella.first_release_1.common.ErrorCode;
import com.tiffin_umbrella.first_release_1.entity.ImageEntity;
import com.tiffin_umbrella.first_release_1.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.util.Collection;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__({@Autowired, @Lazy}))
public class ImageService {

    private static final String RANDOM_IMAGE_URL = "https://picsum.photos";
    private static final String PRODUCTION_IMAGES_API_URL = "https://tiffin-umbrella.herokuapp.com/images/";
    private static final int MAX_FILE_UPLOAD_SIZE_ALLOWED_IN_MB = 10;
    private static final int BYTES_IN_1_MB = 1_000_000;
    private static final int BYTES_IN_1_KB = 1_000;
    private static final int MIN_FILE_ALLOWED_DIMENSION = 1;
    private static final int MAX_FILE_ALLOWED_DIMENSION = 1000;
    private final ImageRepository imageRepository;

    @SneakyThrows
    public ImageEntity create(final MultipartFile file, final String comment) {
        validateFileSize(file);
        final Binary imageBinary = new Binary(BsonBinarySubType.BINARY, file.getBytes());
        final ImageEntity imageCreated = ImageAdapter.create(imageBinary, comment);
        imageRepository.save(imageCreated);//generate ID
        final String urlForImage = PRODUCTION_IMAGES_API_URL + imageCreated.getId();
        imageCreated.setUrl(urlForImage);
        imageCreated.setSizeInKb(file.getSize() / BYTES_IN_1_KB);
        return imageRepository.save(imageCreated);//save image with URL
    }

    public Collection<ImageEntity> getAll() {
        return imageRepository.findAll();
    }

    public byte[] getImageByteArray(final String imageId) {
        byte[] imageData;
        final Optional<ImageEntity> imageById = imageRepository.findById(imageId);
        if (imageById.isPresent()) {
            imageData = imageById.get().getImageBinary().getData();
        } else {/* get random image if image not found by imageId */
            final int averageAllowedFileDimension = MAX_FILE_ALLOWED_DIMENSION / 2;
            imageData = getRandomImageForSize(averageAllowedFileDimension);
        }
        return imageData;
    }

    public byte[] getRandomImageForSize(final Integer size) {
        validateImageSize(size);
        final String sizePathSegment = "/" + size.toString();
        byte[] bytes = new byte[0];
        try (final ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            final URL url = new URL(RANDOM_IMAGE_URL + sizePathSegment + sizePathSegment);
            final BufferedImage image = ImageIO.read(url);
            ImageIO.write(image, "jpeg", outputStream);
            outputStream.flush();
            bytes = outputStream.toByteArray();
        } catch (Exception exception) {
            System.out.println("getRandomImageForSize Exception: " + exception.getLocalizedMessage());
        }
        return bytes;
    }

    private void validateImageSize(final Integer size) {
        final String allowedRange = MIN_FILE_ALLOWED_DIMENSION + "-" + MAX_FILE_ALLOWED_DIMENSION;
        if (size < MIN_FILE_ALLOWED_DIMENSION || size > MAX_FILE_ALLOWED_DIMENSION) {
            BadRequestException.throwException(ErrorCode.IMAGE_SIZE_EXCEEDS_ALLOWED_DIMENSIONS, allowedRange);
        }
    }

    private void validateFileSize(final MultipartFile file) {
        final long fileSizeInMB = file.getSize() / BYTES_IN_1_MB;
        log.info("file size to be uploaded: {}", fileSizeInMB);
        if (fileSizeInMB > MAX_FILE_UPLOAD_SIZE_ALLOWED_IN_MB) {
            BadRequestException.throwException(ErrorCode.IMAGE_SIZE_EXCEEDS_ALLOWED_LIMIT_IN_MB,
                    String.valueOf(fileSizeInMB));
        }
    }
}
