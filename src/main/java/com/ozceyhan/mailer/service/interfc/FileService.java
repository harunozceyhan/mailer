package com.ozceyhan.mailer.service.interfc;

import java.io.IOException;
import org.springframework.core.io.FileSystemResource;

public interface FileService {
    String getFileNameFromUrl(String url);

    FileSystemResource getInputStreamSourceOfUrl(String url) throws IOException;

    void deleteAttachmentFile(String fileName);
}