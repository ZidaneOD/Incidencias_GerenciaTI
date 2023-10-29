package gerencia.unjfsc.edu.pe.controller;

import gerencia.unjfsc.edu.pe.domain.Incidencia;
import gerencia.unjfsc.edu.pe.domain.Solucion;
import gerencia.unjfsc.edu.pe.domain.TipoSeguimiento;
import gerencia.unjfsc.edu.pe.service.IncidenciaService;
import gerencia.unjfsc.edu.pe.service.SolucionService;
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
@RequestMapping("/api/solucion")
public class SolucionController {
    @Autowired
    private SolucionService solucionService;

    @Autowired
    private IncidenciaService incidenciaService;

    @PostMapping
    public ResponseEntity<?> crearSolucion(@Valid @RequestBody Solucion solucion, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // Manejar errores de validación, como campos incorrectos
            List<String> errores = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errores);
        }
        Incidencia incidencia = incidenciaService.obtenerIncidenciaPorId(solucion.getIncidencia().getIdInci());
        incidencia.setTipoSeguimiento(new TipoSeguimiento(3, "Resuelto"));
        incidenciaService.actualizarIncidencia(incidencia);
        Solucion solucionCreada = solucionService.crearSolucion(solucion);
        return ResponseEntity.status(HttpStatus.CREATED).body(solucionCreada);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> obtenerSolucion(@PathVariable Integer id) {
        Solucion solucion = solucionService.obtenerSolucionPorId(id);
        if (solucion != null) {
            return ResponseEntity.ok(solucion);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Solucion>> obtenerTodasLasSoluciones() {
        List<Solucion> soluciones = solucionService.obtenerTodasLasSoluciones();
        return ResponseEntity.ok(soluciones);
    }

    @PutMapping
    public ResponseEntity<?> actualizarSolucion(@Valid @RequestBody Solucion solucion, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errores = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errores);
        }
        Solucion solucionActualizado = solucionService.actualizarSolucion(solucion);
        if (solucionActualizado != null) {
            return ResponseEntity.ok(solucionActualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> eliminarSolucion(@PathVariable Integer id) {
        Solucion solucion = solucionService.obtenerSolucionPorId(id);

        Incidencia incidencia = incidenciaService.obtenerIncidenciaPorId(solucion.getIncidencia().getIdInci());
        incidencia.setTipoSeguimiento(new TipoSeguimiento(1, "Registrada"));
        incidenciaService.actualizarIncidencia(incidencia);

        solucionService.eliminarSolucion(id);
        return ResponseEntity.noContent().build();
    }
}
