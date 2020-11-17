package com.jiyou.nm.common.entity;

import java.io.Serializable;

/**
 * Created by will on 2015/11/22.
 *
 * @author chq
 */
public class FileItem implements Serializable {
    private String fileName;
    private byte[] content;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
