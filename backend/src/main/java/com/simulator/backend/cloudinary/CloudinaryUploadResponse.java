package com.simulator.backend.cloudinary;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CloudinaryUploadResponse {

    private String publicId;

    private String secureUrl;

    private String resourceType;

    private String format;

    private Long bytes;
}
