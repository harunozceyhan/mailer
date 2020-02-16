package com.ozceyhan.mailer.service.impl;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import com.ozceyhan.mailer.service.interfc.FileService;

import org.apache.commons.io.FileUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;

@Service
public class FileServiceImpl implements FileService {

    public String getFileNameFromUrl(String url) {
        return url.substring(url.lastIndexOf('/') + 1, url.length());
    }

    public FileSystemResource getInputStreamSourceOfUrl(String url) throws IOException {
        File file = new File(getFileNameFromUrl(url));
        FileUtils.copyURLToFile(new URL(url), file);
        return new FileSystemResource(file);
    }

    public void deleteAttachmentFile(String fileName) {
        File file = new File(fileName);
        if (file.exists()) {
            file.delete();
        }
    }

}