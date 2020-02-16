package com.ozceyhan.mailer.service.impl;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import com.ozceyhan.mailer.service.interfc.FileService;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;

@Service
public class FileServiceImpl implements FileService {

    public String getFileNameFromUrl(String url) {
        try {
            return FilenameUtils.getName(new URL(url).getPath());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return "";
        }
    }

    public FileSystemResource getInputStreamSourceOfUrl(String url) throws IOException {
        File file = new File(getFileNameFromUrl(url));
        if (!file.exists()) {
            FileUtils.copyURLToFile(new URL(url), file);
        }
        return new FileSystemResource(file);
    }

    public void deleteAttachmentFile(String fileName) {
        File file = new File(fileName);
        if (file.exists()) {
            file.delete();
        }
    }

}