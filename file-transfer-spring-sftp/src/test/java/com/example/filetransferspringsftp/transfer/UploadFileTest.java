package com.example.filetransferspringsftp.transfer;

import org.junit.jupiter.api.Test;

public class UploadFileTest {

  @Test
  public void uploadFileTest() {
    UploadFile uploadFile = new UploadFile();

    uploadFile.upload();
  }

}
