package gerencia.unjfsc.edu.pe.controller;

import gerencia.unjfsc.edu.pe.service.FileServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/archivo/")
public class S3Controller {
    @Autowired
    private FileServiceImp fileServiceImp;

    @PostMapping("upload")
    public String upload(@RequestParam("file") MultipartFile file) {
        return fileServiceImp.saveFile(file);
    }

    @GetMapping("download/{filename}")
    public ResponseEntity<byte[]> download(@PathVariable("filename") String filename) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", MediaType.ALL_VALUE);
        headers.add("Content-Disposition", "attachment; filename=" + filename);
        byte[] bytes = fileServiceImp.dowloadFile(filename);
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(bytes);
    }


    @DeleteMapping("{filename}")
    public String deleteFile(@PathVariable("filename") String filename) {
        return fileServiceImp.deleteFile(filename);
    }

    @GetMapping("list")
    public List<String> getAllFiles() {

        return fileServiceImp.listAllFiles();

    }
}
