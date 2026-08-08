package com.simulator.backend.cloudinary;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CloudinaryService {

    private static final Logger log =
            LoggerFactory.getLogger(CloudinaryService.class);

    private final Cloudinary cloudinary;

    private static final long MAX_PROFILE_IMAGE_SIZE =
            5L * 1024 * 1024;

    private static final long MAX_RESUME_SIZE =
            10L * 1024 * 1024;

    private static final Set<String> ALLOWED_IMAGE_TYPES =
            Set.of(
                    "image/jpeg",
                    "image/png",
                    "image/webp"
            );

    private static final Set<String> ALLOWED_RESUME_TYPES =
            Set.of(
                    "application/pdf"
            );

    /**
     * Upload candidate profile image.
     */
    public CloudinaryUploadResponse uploadProfileImage(
            MultipartFile file,
            String userUuid
    ) {

        validateFile(
                file,
                MAX_PROFILE_IMAGE_SIZE,
                ALLOWED_IMAGE_TYPES,
                "profile image"
        );

        String folder =
                "ai-interview-simulator/profiles/" + userUuid;

        return upload(
                file,
                folder,
                "image"
        );
    }

    /**
     * Upload candidate resume.
     */
    public CloudinaryUploadResponse uploadResume(
            MultipartFile file,
            String userUuid
    ) {

        validateFile(
                file,
                MAX_RESUME_SIZE,
                ALLOWED_RESUME_TYPES,
                "resume"
        );

        String folder =
                "ai-interview-simulator/resumes/" + userUuid;

        return upload(
                file,
                folder,
                "raw"
        );
    }

    /**
     * Generic Cloudinary upload.
     */
    private CloudinaryUploadResponse upload(
            MultipartFile file,
            String folder,
            String resourceType
    ) {

        try {

            Map<String, Object> options = ObjectUtils.asMap(
                    "folder", folder,
                    "resource_type", resourceType,
                    "use_filename", true,
                    "unique_filename", true,
                    "overwrite", false
            );

            Map<?, ?> result =
                    cloudinary.uploader().upload(
                            file.getBytes(),
                            options
                    );

            String publicId =
                    getStringValue(result, "public_id");

            String secureUrl =
                    getStringValue(result, "secure_url");

            String returnedResourceType =
                    getStringValue(result, "resource_type");

            String format =
                    getStringValue(result, "format");

            Long bytes = null;

            Object bytesValue =
                    result.get("bytes");

            if (bytesValue instanceof Number number) {
                bytes = number.longValue();
            }

            return CloudinaryUploadResponse.builder()
                    .publicId(publicId)
                    .secureUrl(secureUrl)
                    .resourceType(returnedResourceType)
                    .format(format)
                    .bytes(bytes)
                    .build();

        } catch (IOException exception) {

            log.error(
                    "Cloudinary upload failed for folder: {}",
                    folder,
                    exception
            );

            throw new CloudinaryException(
                    "Failed to upload file to Cloudinary.",
                    exception
            );

        } catch (RuntimeException exception) {

            log.error(
                    "Cloudinary upload failed for folder: {}",
                    folder,
                    exception
            );

            throw new CloudinaryException(
                    "Failed to upload file to Cloudinary.",
                    exception
            );
        }
    }

    /**
     * Delete an asset from Cloudinary.
     *
     * IMPORTANT:
     * publicId must be the Cloudinary public_id,
     * NOT the complete secure URL.
     */
    public void delete(
            String publicId,
            String resourceType
    ) {

        if (publicId == null || publicId.isBlank()) {
            return;
        }

        if (resourceType == null || resourceType.isBlank()) {
            resourceType = "image";
        }

        try {

            Map<String, Object> options =
                    ObjectUtils.asMap(
                            "resource_type", resourceType,
                            "type", "upload",
                            "invalidate", true
                    );

            /*
             * Cloudinary Java SDK:
             *
             * destroy(String publicId, Map options)
             */
            Map<?, ?> result =
                    cloudinary.uploader().destroy(
                            publicId,
                            options
                    );

            String deleteResult =
                    getStringValue(result, "result");

            if (
                    "ok".equalsIgnoreCase(deleteResult)
                            || "not found".equalsIgnoreCase(deleteResult)
            ) {

                log.info(
                        "Cloudinary asset deleted. publicId={}, resourceType={}, result={}",
                        publicId,
                        resourceType,
                        deleteResult
                );

                return;
            }

            log.warn(
                    "Cloudinary delete returned unexpected result. publicId={}, result={}",
                    publicId,
                    deleteResult
            );

        } catch (IOException exception) {

            log.error(
                    "Cloudinary delete failed. publicId={}, resourceType={}",
                    publicId,
                    resourceType,
                    exception
            );

            throw new CloudinaryException(
                    "Failed to delete file from Cloudinary.",
                    exception
            );

        } catch (RuntimeException exception) {

            log.error(
                    "Cloudinary delete failed. publicId={}, resourceType={}",
                    publicId,
                    resourceType,
                    exception
            );

            throw new CloudinaryException(
                    "Failed to delete file from Cloudinary.",
                    exception
            );
        }
    }

    /**
     * Delete profile image.
     */
    public void deleteProfileImage(
            String publicId
    ) {

        delete(
                publicId,
                "image"
        );
    }

    /**
     * Delete resume.
     */
    public void deleteResume(
            String publicId
    ) {

        delete(
                publicId,
                "raw"
        );
    }

    /**
     * Replace profile image.
     *
     * New image is uploaded first.
     * Old image is deleted only after successful upload.
     */
    public CloudinaryUploadResponse replaceProfileImage(
            MultipartFile newFile,
            String userUuid,
            String oldPublicId
    ) {

        CloudinaryUploadResponse newImage =
                uploadProfileImage(
                        newFile,
                        userUuid
                );

        /*
         * Delete old image only after new image
         * has been successfully uploaded.
         */
        if (
                oldPublicId != null
                        && !oldPublicId.isBlank()
        ) {

            try {

                deleteProfileImage(
                        oldPublicId
                );

            } catch (CloudinaryException exception) {

                /*
                 * New image has already been uploaded.
                 *
                 * Do not fail the profile update because
                 * the old image could not be deleted.
                 *
                 * Log the problem so it can be monitored.
                 */
                log.error(
                        "New profile image uploaded but old image could not be deleted. oldPublicId={}",
                        oldPublicId,
                        exception
                );
            }
        }

        return newImage;
    }

    /**
     * Validate uploaded file.
     */
    private void validateFile(
            MultipartFile file,
            long maxSize,
            Set<String> allowedTypes,
            String fileType
    ) {

        if (file == null || file.isEmpty()) {

            throw new CloudinaryException(
                    "Please select a " + fileType + "."
            );
        }

        if (file.getSize() > maxSize) {

            long maxSizeMb =
                    maxSize / (1024 * 1024);

            throw new CloudinaryException(
                    "The "
                            + fileType
                            + " must not exceed "
                            + maxSizeMb
                            + " MB."
            );
        }

        String contentType =
                file.getContentType();

        if (
                contentType == null
                        || !allowedTypes.contains(
                        contentType.toLowerCase()
                )
        ) {

            throw new CloudinaryException(
                    "Invalid "
                            + fileType
                            + " type."
            );
        }
    }

    /**
     * Safely get String value from Cloudinary response.
     */
    private String getStringValue(
            Map<?, ?> result,
            String key
    ) {

        Object value = result.get(key);

        return value != null
                ? value.toString()
                : null;
    }
}