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

    /**
     * Get file name from given url using Apache commons.io library.
     *
     * @param url String
     */
    public String getFileNameFromUrl(String url) {
        try {
            return FilenameUtils.getName(new URL(url).getPath());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * Download attachment file and create FileSystemResource Object from given url.
     *
     * @param url String
     */
    public FileSystemResource getInputStreamSourceOfUrl(String url) throws IOException {
        File file = new File(getFileNameFromUrl(url));
        if (!file.exists()) {
            FileUtils.copyURLToFile(new URL(url), file);
        }
        return new FileSystemResource(file);
    }

    /**
     * Remove downloaded file if exists.
     *
     * @param fileName String
     */
    public void deleteAttachmentFile(String fileName) {
        File file = new File(fileName);
        if (file.exists()) {
            file.delete();
        }
    }

}