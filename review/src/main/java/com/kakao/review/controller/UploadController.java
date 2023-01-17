package com.kakao.review.controller;

import com.kakao.review.dto.UploadResultDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Log4j2
@RestController
public class UploadController {

    // applicatino.yml 파일에 만든 파일 저장할 디렉토리 변수 주입
    @Value("${com.adamsoft.upload.path}")
    private String uploadPath;

    // 날짜 별로 디렉토리를 생성해주는 메서드 작성
    private String makeFoleder(){
        String str = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));

        String realUploadPath = str.replace("//", File.separator);
        File uploadPathDir = new File(uploadPath, realUploadPath);

        // 디렉토리가 없으면 생성
        if(uploadPathDir.exists() == false){
            uploadPathDir.mkdirs();
        }
        return realUploadPath;
    }

    @PostMapping("/uploadajax")
    public ResponseEntity<List<UploadResultDTO>> uploadFile(MultipartFile [] uploadFiles){

        // 결과를 전달할 객체를 생성
        List<UploadResultDTO> resultDTOList = new ArrayList<>();

        for(MultipartFile uploadFile : uploadFiles){

            // 이미지 파일이 아니면 이미지 업로드 수행하지 않음
            if(uploadFile.getContentType().startsWith("image")==false){
                log.warn("이미지 파일이 아닙니다.");
                // 404 애러
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }

            // 업로드 된 파일의 실제 이름

            String originalName = uploadFile.getOriginalFilename();

            // IE 는 파일이름이 아니고 전체 경로를 전송하기 때문에 마지막 \ 부분만 추출한다.
            String fileName = originalName.substring(originalName.lastIndexOf("\\")+1);

            log.warn("fileName: " + fileName);

            // 디렉토릿 생성하기
            String realUploadPath = makeFoleder();

            //UUID
            String uuid = UUID.randomUUID().toString();

            //저장할 파일 이름 중간에 _를 이용해서 구분
            String saveName = uploadPath + File.separator + realUploadPath + File.separator + uuid + fileName;
            Path savePath = Paths.get(saveName);
            try {
                uploadFile.transferTo(savePath);

                // 결과를 List에 추가
                resultDTOList.add(new UploadResultDTO(fileName, uuid, realUploadPath));
            } catch (IOException e) {
                System.out.println(e.getLocalizedMessage());
                e.printStackTrace();
            }
        }
        return new ResponseEntity<>(resultDTOList, HttpStatus.OK);
    }
}
