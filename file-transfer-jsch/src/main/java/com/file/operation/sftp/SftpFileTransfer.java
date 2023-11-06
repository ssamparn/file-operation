package com.file.operation.sftp;

import java.io.File;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

public class SftpFileTransfer {

    public static void main(String[] args) {
        File file = new File("file-transfer-jsch/src/test/resources/source/file.txt");
        String sourceFile = file.getAbsolutePath();
        System.out.println(sourceFile);

        String sftpPath = "/upload";
        String sftpHost = "0.0.0.0";
        String sftpPort = "22";
        String sftpUserName = "user";
        String sftpPassword = "password";
        Session jschSession = null;
        ChannelSftp sftpChannel = null;
        try {
            JSch jsch = new JSch();
            jschSession = jsch.getSession(sftpUserName, sftpHost, Integer.valueOf(sftpPort));
            jschSession.setPassword(sftpPassword);
            jschSession.setConfig("StrictHostKeyChecking", "no");

            System.out.println("Connecting------");
            jschSession.connect();
            System.out.println("Establishing Session------");

            Channel channel = jschSession.openChannel("sftp");
            sftpChannel = (ChannelSftp) channel;
            sftpChannel.connect();
            sftpChannel.cd("/upload");

            System.out.println("Opened Sftp Channel------");

            sftpChannel.put(sourceFile, sftpPath);
            System.out.println("Copied file to Sftp Server------");

            sftpChannel.disconnect();

        } catch (JSchException | SftpException e) {
            throw new RuntimeException(e);
        } finally {
            if (jschSession != null) {
                assert sftpChannel != null;
                sftpChannel.disconnect();
                jschSession.disconnect();
            }
        }

        System.out.println("Done");
    }
}
