package dev.kaplanbedwars.GitDW;

import dev.kaplanbedwars.erormessage.ErrorLog;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public class GitHubDownloader {
    public static String downloadFile(String fileURL, String saveDir) {
        try {
            String fileName = Paths.get(new URL(fileURL).getPath()).getFileName().toString();
            Files.createDirectories(Paths.get(saveDir));
            String savePath = saveDir + "/" + fileName;
            try (BufferedInputStream in = new BufferedInputStream(new URL(fileURL).openStream());
                 FileOutputStream fileOutputStream = new FileOutputStream(savePath)) {
                byte[] dataBuffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                    fileOutputStream.write(dataBuffer, 0, bytesRead);
                }
            }
            return savePath; // İndirilen dosyanın tam yolu
        } catch (Exception e) {
            ErrorLog.logError(e, "Dosya indirilirken bir hata oluştu: " + fileURL);
            return null;
        }
    }
}
