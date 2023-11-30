package gerencia.unjfsc.edu.pe.controller;

import gerencia.unjfsc.edu.pe.domain.Area;
import gerencia.unjfsc.edu.pe.service.AreaService;
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
@RequestMapping("/api/area")
public class AreaController {
    @Autowired
    private AreaService areaService;

    @PostMapping(produces = "application/json")
    public ResponseEntity<?> crearArea(@Valid @RequestBody Area area, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // Manejar errores de validación, como campos incorrectos
            List<String> errores = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errores);
        }
        Area areaCreada = areaService.crearArea(area);
        return ResponseEntity.status(HttpStatus.CREATED).body(areaCreada);
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<?> obtenerArea(@PathVariable Integer id) {
        Area area = areaService.obtenerPorId(id);
        if (area != null) {
            return ResponseEntity.ok(area);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Area>> obtenerTodosLasAreas() {
        List<Area> areas = areaService.obtenerTodasLasAreas();
        return ResponseEntity.ok(areas);
    }

    @PutMapping(produces = "application/json")
    public ResponseEntity<?> actualizarArea(@Valid @RequestBody Area area, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errores = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errores);
        }

        Area areaActualizada = areaService.actualizarArea(area);
        if (areaActualizada != null) {
            return ResponseEntity.ok(areaActualizada);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Void> eliminarArea(@PathVariable Integer id) {
        areaService.eliminarArea(id);
        return ResponseEntity.noContent().build();
    }
}
