package hu.wob.assignment.client;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.stereotype.Component;

import hu.wob.assignment.configuration.FtpConfigProperties;
import hu.wob.assignment.exception.FtpClientException;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FtpClient {

    private final FtpConfigProperties ftpConfig;

    public String uploadToFTP(String reportJson, String baseFileName) throws Exception {
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(ftpConfig.getHost(), ftpConfig.getPort());
            ftpClient.login(ftpConfig.getUsername(), ftpConfig.getPassword());
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            String currentDateTimeString = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
            String uniqueJsonFileName = baseFileName + "_at_" + currentDateTimeString + ".json";

            try (InputStream inputStream = new ByteArrayInputStream(reportJson.getBytes())) {
                boolean success = ftpClient.storeFile(ftpConfig.getDirectory() + "/" + uniqueJsonFileName, inputStream);
                if (!success) {
                    throw new IOException("File upload failed!");
                }
            } catch (IOException e) {
                throw new FtpClientException(MessageFormat.format("Upload of file {0} to FTP server has failed", uniqueJsonFileName));
            }
            return uniqueJsonFileName;
        } finally {
            ftpClient.logout();
            ftpClient.disconnect();
        }
    }
}
