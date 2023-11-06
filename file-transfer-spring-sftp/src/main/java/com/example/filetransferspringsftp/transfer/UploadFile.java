package com.example.filetransferspringsftp.transfer;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;

import org.springframework.integration.sftp.session.DefaultSftpSessionFactory;
import org.springframework.integration.sftp.session.SftpSession;

public class UploadFile {
  private DefaultSftpSessionFactory sessionFactory() {

    DefaultSftpSessionFactory factory = new DefaultSftpSessionFactory();
    factory.setHost("0.0.0.0");
    factory.setPort(22);
    factory.setAllowUnknownKeys(true);
    factory.setUser("user");
    factory.setPassword("password");

    return factory;
  }

  public void upload() {
    SftpSession session = sessionFactory().getSession();
    InputStream resourceAsStream = UploadFile.class.getClassLoader().getResourceAsStream("source-dir/source-file.txt");
    try {
      session.write(resourceAsStream, "/upload/file" + LocalDateTime.now() + ".txt");
    } catch (IOException e) {
      throw new RuntimeException(e);
    } finally {
      session.close();
    }
  }
}
