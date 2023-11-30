package gerencia.unjfsc.edu.pe.controller;

import gerencia.unjfsc.edu.pe.domain.Usuario;
import gerencia.unjfsc.edu.pe.domain.UsuarioImagen;
import gerencia.unjfsc.edu.pe.response.UsuarioImagenResponse;
import gerencia.unjfsc.edu.pe.service.FileService;
import gerencia.unjfsc.edu.pe.service.UsuarioImagenService;
import gerencia.unjfsc.edu.pe.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/login")
public class LoginController {
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private FileService fileService;
    @Autowired
    private UsuarioImagenService imagenService;

    @PostMapping(produces = "application/json")
    public ResponseEntity<?> login(@Valid @RequestBody Usuario usuario, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // Manejar errores de validación, como campos incorrectos
            List<String> errores = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errores);
        }
        Usuario usuarioBuscado = usuarioService.obtenerCredenciales(usuario.getNombUsua(), usuario.getPassUsua());

        if (usuarioBuscado != null) {
            UsuarioImagen img = imagenService.obtenerImgPorId(usuarioBuscado.getIdUsua());
            if (img != null) {
                byte[] imgByte = fileService.dowloadFile(img.getNombImg());
                UsuarioImagenResponse request = new UsuarioImagenResponse(img.getUsuario(), imgByte);
                return ResponseEntity.ok().body(request);
            }
            UsuarioImagenResponse request = new UsuarioImagenResponse(usuarioBuscado, null);
            return ResponseEntity.ok().body(request);

        } else {
            return ResponseEntity.ok().body("Denegado");
        }
    }
}
