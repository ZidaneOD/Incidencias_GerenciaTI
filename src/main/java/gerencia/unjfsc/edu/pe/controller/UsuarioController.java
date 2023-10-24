package gerencia.unjfsc.edu.pe.controller;

import gerencia.unjfsc.edu.pe.domain.Rol;
import gerencia.unjfsc.edu.pe.domain.User;
import gerencia.unjfsc.edu.pe.domain.Usuario;
import gerencia.unjfsc.edu.pe.domain.Persona;
import gerencia.unjfsc.edu.pe.service.PersonaService;
import gerencia.unjfsc.edu.pe.service.RolService;
import gerencia.unjfsc.edu.pe.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {
    @Autowired
    private RolService rolService;
    @Autowired
    private PersonaService personaService;
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<?> crearRol(@Valid @RequestBody User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // Manejar errores de validación, como campos incorrectos
            List<String> errores = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errores);
        }
        Persona personaCreada = personaService.crearPersona(convertUserToPers(user));
        Usuario usuarioCreado = usuarioService.crearUsuario(convertUserToUsuario(user, personaCreada));

        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCreado);
    }

    private Persona convertUserToPers(User user) {
        Rol rolBuscado = rolService.obtenerRolPorNombre(user.getNombRol());
        List<Rol> list = new ArrayList<>();
        list.add(rolBuscado);
        Persona pers;
        if (user.getIdUsua() != null) {
            Usuario usuario = usuarioService.obtenerUsuarioPorId(user.getIdUsua());
            pers = new Persona(
                    usuario.getPersona().getIdPers(),
                    user.getNombPers(),
                    user.getAppaPers(),
                    user.getApmaPers(),
                    user.getDniPers(),
                    user.getTelfPers(),
                    user.getEmailPers(),
                    list
            );

        } else {
            pers = new Persona(
                    null,
                    user.getNombPers(),
                    user.getAppaPers(),
                    user.getApmaPers(),
                    user.getDniPers(),
                    user.getTelfPers(),
                    user.getEmailPers(),
                    list
            );
        }

        return pers;
    }

    private Usuario convertUserToUsuario(User user, Persona persona) {
        Usuario usuario;
        if (user.getIdUsua() != null) {
            usuario = new Usuario(
                    user.getIdUsua(),
                    user.getNombUsua(),
                    user.getPassUsua(),
                    persona
            );
        } else {
            usuario = new Usuario(
                    null,
                    user.getNombUsua(),
                    user.getPassUsua(),
                    persona
            );
        }
        return usuario;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> obtenerUsuario(@PathVariable Integer id) {
        Usuario usuario = usuarioService.obtenerUsuarioPorId(id);
        if (usuario != null) {
            return ResponseEntity.ok(usuario);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> obtenerTodosLosUsuarios() {
        List<Usuario> usuarios = usuarioService.obtenerTodosLosUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    @PutMapping
    public ResponseEntity<?> actualizarUsuario(@Valid @RequestBody User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errores = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errores);
        }
        Persona personaActualizada = personaService.actualizarPersona(convertUserToPers(user));
        Usuario usuarioActualizada = usuarioService.actualizarUsuario(convertUserToUsuario(user, personaActualizada));

        if (usuarioActualizada != null) {
            return ResponseEntity.ok(usuarioActualizada);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Integer id) {
        personaService.eliminarPersona(id);
        return ResponseEntity.noContent().build();
    }
}
