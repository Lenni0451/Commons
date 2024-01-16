package net.lenni0451.commons.httpclient.content.impl;

import net.lenni0451.commons.httpclient.content.HttpContent;

import javax.annotation.ParametersAreNonnullByDefault;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@ParametersAreNonnullByDefault
public class FileContent extends HttpContent {

    private final File file;

    public FileContent(final File file) {
        this.file = file;
    }

    @Override
    public String getDefaultContentType() {
        return "application/octet-stream";
    }

    @Override
    public int getContentLength() {
        return (int) this.file.length();
    }

    @Override
    protected byte[] compute() throws IOException {
        try (FileInputStream fis = new FileInputStream(this.file)) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = fis.read(buffer)) != -1) baos.write(buffer, 0, len);
            return baos.toByteArray();
        }
    }

}