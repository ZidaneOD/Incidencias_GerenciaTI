package gerencia.unjfsc.edu.pe.controller;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import gerencia.unjfsc.edu.pe.domain.Rol;
import gerencia.unjfsc.edu.pe.service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/rol")
public class RolController {
    @Autowired
    private RolService rolService;

    @PostMapping(produces = "application/json")
    public ResponseEntity<?> crearRol(@Valid @RequestBody Rol rol, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // Manejar errores de validación, como campos incorrectos
            List<String> errores = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errores);
        }
        Rol rolCreada = rolService.crearRol(rol);
        return ResponseEntity.status(HttpStatus.CREATED).body(rolCreada);
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<?> obtenerRol(@PathVariable Integer id) {

        Rol rol = rolService.obtenerRolPorId(id);
        if (rol != null) {
            return ResponseEntity.ok(rol);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Rol>> obtenerTodosLosRoles() {
        List<Rol> roles = rolService.obtenerTodosLosRoles();
        return ResponseEntity.ok(roles);
    }

    @PutMapping(produces = "application/json")
    public ResponseEntity<?> actualizarRol(@Valid @RequestBody Rol rol, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errores = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errores);
        }
        Rol rolActualizado = rolService.actualizarRol(rol);
        if (rolActualizado != null) {
            return ResponseEntity.ok(rolActualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value = "/{id}",produces = "application/json")
    public ResponseEntity<Void> eliminarRol(@PathVariable Integer id) {
        rolService.eliminarRol(id);
        return ResponseEntity.noContent().build();
    }
}
