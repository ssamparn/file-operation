package com.java.fileupload.model;

import lombok.Data;

@Data
public class UploadFileResponse {
  private String desc;
  private String requestType;
  private String inputStreamType;
  private String multipartResolverType;
  private String fileSizeThreshold;
}
