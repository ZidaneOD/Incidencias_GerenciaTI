package gerencia.unjfsc.edu.pe.controller;

import gerencia.unjfsc.edu.pe.domain.TipoSeguimiento;
import gerencia.unjfsc.edu.pe.service.TipoSeguimientoService;
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
@RequestMapping("/api/tiposeguimiento")
public class TipoSeguimientoController {
    @Autowired
    private TipoSeguimientoService tipoSeguimientoService;

    @PostMapping
    public ResponseEntity<?> crearTipoSeguimiento(@Valid @RequestBody TipoSeguimiento tipoSeguimiento, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // Manejar errores de validación, como campos incorrectos
            List<String> errores = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errores);
        }
        TipoSeguimiento tipoSeguimientoCreada = tipoSeguimientoService.crearTipoSeguimiento(tipoSeguimiento);
        return ResponseEntity.status(HttpStatus.CREATED).body(tipoSeguimientoCreada);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> obtenerTipoSeguimiento(@PathVariable Integer id) {
        TipoSeguimiento tipoSeguimiento = tipoSeguimientoService.obtenerTipoSeguimientoPorId(id);
        if (tipoSeguimiento != null) {
            return ResponseEntity.ok(tipoSeguimiento);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<TipoSeguimiento>> obtenerTodosLosTipoSeguimientos() {
        List<TipoSeguimiento> tipoSeguimientos = tipoSeguimientoService.obtenerTodosLosTiposSeguimientos();
        return ResponseEntity.ok(tipoSeguimientos);
    }

    @PutMapping
    public ResponseEntity<?> actualizarTipoSeguimiento(@Valid @RequestBody TipoSeguimiento tipoSeguimiento, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errores = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errores);
        }
        TipoSeguimiento tipoSeguimientoActualizado = tipoSeguimientoService.actualizarTipoSeguimiento(tipoSeguimiento);
        if (tipoSeguimientoActualizado != null) {
            return ResponseEntity.ok(tipoSeguimientoActualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> eliminarTipoSeguimiento(@PathVariable Integer id) {
        tipoSeguimientoService.eliminarTipoSeguimiento(id);
        return ResponseEntity.noContent().build();
    }
}
