package gerencia.unjfsc.edu.pe.controller;

import gerencia.unjfsc.edu.pe.domain.TipoIncidencia;
import gerencia.unjfsc.edu.pe.service.TipoIncidenciaService;
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
@RequestMapping("/api/tipoincidencia")
public class TipoIncidenciaController {
    @Autowired
    private TipoIncidenciaService tipoIncidenciaService;

    @PostMapping
    public ResponseEntity<?> crearTipoIncidencia(@Valid @RequestBody TipoIncidencia tipoIncidencia, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // Manejar errores de validación, como campos incorrectos
            List<String> errores = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errores);
        }
        TipoIncidencia tipoIncidenciaCreada = tipoIncidenciaService.crearTipoIncidencia(tipoIncidencia);
        return ResponseEntity.status(HttpStatus.CREATED).body(tipoIncidenciaCreada);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> obtenerTipoIncidencia(@PathVariable Integer id) {
        TipoIncidencia tipoIncidencia = tipoIncidenciaService.obtenerTipoIncidenciaPorId(id);
        if (tipoIncidencia != null) {
            return ResponseEntity.ok(tipoIncidencia);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<TipoIncidencia>> obtenerTodosLosTipoIncidencias() {
        List<TipoIncidencia> tipoIncidencias = tipoIncidenciaService.obtenerTodosLosTiposIncidencias();
        return ResponseEntity.ok(tipoIncidencias);
    }

    @PutMapping
    public ResponseEntity<?> actualizarTipoIncidencia(@Valid @RequestBody TipoIncidencia tipoIncidencia, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errores = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errores);
        }
        TipoIncidencia tipoIncidenciaActualizado = tipoIncidenciaService.actualizarTipoIncidencia(tipoIncidencia);
        if (tipoIncidenciaActualizado != null) {
            return ResponseEntity.ok(tipoIncidenciaActualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> eliminarTipoIncidencia(@PathVariable Integer id) {
        tipoIncidenciaService.eliminarTipoIncidencia(id);
        return ResponseEntity.noContent().build();
    }
}
