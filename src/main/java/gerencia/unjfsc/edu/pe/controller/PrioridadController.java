package gerencia.unjfsc.edu.pe.controller;

import gerencia.unjfsc.edu.pe.domain.Prioridad;
import gerencia.unjfsc.edu.pe.service.PrioridadService;
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
@RequestMapping("/api/prioridad")
public class PrioridadController {
    @Autowired
    private PrioridadService prioridadService;

    @PostMapping(produces = "application/json")
    public ResponseEntity<?> crearPrioridad(@Valid @RequestBody Prioridad prioridad, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // Manejar errores de validación, como campos incorrectos
            List<String> errores = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errores);
        }
        Prioridad prioridadCreada = prioridadService.crearPrioridad(prioridad);
        return ResponseEntity.status(HttpStatus.CREATED).body(prioridadCreada);
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<?> obtenerPrioridad(@PathVariable Integer id) {
        Prioridad prioridad = prioridadService.obtenerPrioridadPorId(id);
        if (prioridad != null) {
            return ResponseEntity.ok(prioridad);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Prioridad>> obtenerTodosLasPrioridades() {
        List<Prioridad> prioridades = prioridadService.obtnerTodasLasPrioridades();
        return ResponseEntity.ok(prioridades);
    }

    @PutMapping(produces = "application/json")
    public ResponseEntity<?> actualizarPrioridad(@Valid @RequestBody Prioridad prioridad, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errores = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errores);
        }
        Prioridad prioridadActualizado = prioridadService.actualizarPrioridad(prioridad);
        if (prioridadActualizado != null) {
            return ResponseEntity.ok(prioridadActualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Void> eliminarPrioridad(@PathVariable Integer id) {
        prioridadService.eliminarPrioridad(id);
        return ResponseEntity.noContent().build();
    }
}
