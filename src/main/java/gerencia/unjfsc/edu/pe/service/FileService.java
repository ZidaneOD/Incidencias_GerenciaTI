package gerencia.unjfsc.edu.pe.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {
    String saveFile(MultipartFile file);

    byte[] dowloadFile(String filename);

    String deleteFile(String filename);

    List<String> listAllFiles();
}
