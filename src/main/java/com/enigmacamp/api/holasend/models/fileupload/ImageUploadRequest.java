package com.enigmacamp.api.holasend.models.fileupload;

import org.springframework.web.multipart.MultipartFile;

public class ImageUploadRequest {

    private MultipartFile file;

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
