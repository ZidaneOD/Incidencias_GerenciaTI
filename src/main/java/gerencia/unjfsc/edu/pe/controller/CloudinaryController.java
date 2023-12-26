package gerencia.unjfsc.edu.pe.controller;

import gerencia.unjfsc.edu.pe.domain.Usuario;
import gerencia.unjfsc.edu.pe.domain.UsuarioImagen;
import gerencia.unjfsc.edu.pe.service.CloudinaryService;
import gerencia.unjfsc.edu.pe.service.UsuarioImagenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/archivo/")
public class CloudinaryController {
    @Autowired
    CloudinaryService cloudinaryService;
    @Autowired
    UsuarioImagenService usuarioImagenService;

    @GetMapping
    public ResponseEntity<List<UsuarioImagen>> listar() {
        List<UsuarioImagen> usuarioImagens = usuarioImagenService.obtenerLista();
        return new ResponseEntity(usuarioImagens, HttpStatus.OK);
    }

    @PostMapping("upload/{id}")
    public ResponseEntity<Map> upload(@PathVariable Integer id, @RequestParam MultipartFile multipartFile) throws IOException {
        BufferedImage bi = ImageIO.read(multipartFile.getInputStream());
        if (bi == null) {
            return new ResponseEntity("imagen no válida", HttpStatus.BAD_REQUEST);
        }
        Usuario user = new Usuario();
        user.setIdUsua(id);
        Map result = cloudinaryService.upload(multipartFile);
        UsuarioImagen upload = usuarioImagenService.crearUsuaImg(new UsuarioImagen(null, user, (String) result.get("original_filename"), (String) result.get("url"), (String) result.get("public_id")));
        return new ResponseEntity(upload, HttpStatus.OK);
    }

    @PostMapping("delete/{id}")
    public ResponseEntity<Map> delete(@PathVariable("id") String id) throws IOException {
        Map result = cloudinaryService.delete(id);
        return new ResponseEntity(result, HttpStatus.OK);
    }
}
