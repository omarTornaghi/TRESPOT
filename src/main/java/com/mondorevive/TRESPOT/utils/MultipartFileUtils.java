package com.mondorevive.TRESPOT.utils;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.text.MessageFormat;

public class MultipartFileUtils {
    public static byte[] fromByteArrToBytePrim(Byte[] arr){
        if(arr == null) return null;
        int j=0;
        byte[] bytes = new byte[arr.length];
        for(Byte b: arr) bytes[j++] = b;
        return bytes;
    }

    public static String getFileName(String nome, MultipartFile file){
        return MessageFormat.format("{0}.{1}", nome, getEstensione(file));
    }
    public static String getEstensione(MultipartFile file) {
        if(file == null) return null;
        String nomeFile = file.getOriginalFilename();
        String estensione;
        try {
            estensione = nomeFile == null ? null : (nomeFile.contains(".") ? (nomeFile.substring(nomeFile.lastIndexOf(".") + 1)) : "txt");
        }
        catch(Exception ex){ estensione = ""; }
        return estensione;
    }

    public static ResponseEntity<byte[]>preparaFileDownload(byte[] content, String filename, String contentType){
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .body(content);
    }
    /*
    public static ResponseEntity<byte[]>preparaFileDownload(byte[] content, String filename){
        MimetypesFileTypeMap fileTypeMap = new MimetypesFileTypeMap();
        String mimeType = fileTypeMap.getContentType(filename);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(mimeType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .body(content);
    }*/
}
