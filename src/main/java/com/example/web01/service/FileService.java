package com.example.web01.service;

import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

@Service
@Log
public class FileService {

    public String uploadFile(
            String uploadPath,
            String originalFileName,
            byte[] fileDate) throws Exception{

        // 중복된 이름을 배제하기위한 난수
        UUID uuid = UUID.randomUUID();
        // 확장자 추출
        String extension =
                originalFileName.substring(originalFileName.lastIndexOf("."));

        String savedFileName = uuid.toString()+extension;
        String fileUploadFullUrl = uploadPath+"/"+savedFileName;

        FileOutputStream fos = new FileOutputStream((fileUploadFullUrl));
        fos.write(fileDate);
        fos.close();

        return savedFileName;
    }

    // 업로드 파일 수정 ( 기존파일 삭제 )
    public void deleteFile(String filePath) throws Exception{
        File deleteFile = new File(filePath);
        if(deleteFile.exists()){
            deleteFile.delete();
            log.info("파일 삭제하였습니다.");
        } else {
            log.info("파일 존재하지 않습니다.");
        }
    }
}
