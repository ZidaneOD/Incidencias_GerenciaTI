package gerencia.unjfsc.edu.pe.controller;

import gerencia.unjfsc.edu.pe.domain.Salon;
import gerencia.unjfsc.edu.pe.service.SalonService;
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
@RequestMapping("/api/salon")
public class SalonController {
    @Autowired
    private SalonService salonService;

    @PostMapping
    public ResponseEntity<?> crearSalon(@Valid @RequestBody Salon salon, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // Manejar errores de validación, como campos incorrectos
            List<String> errores = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errores);
        }
        Salon salonCreada = salonService.crearSalon(salon);
        return ResponseEntity.status(HttpStatus.CREATED).body(salonCreada);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> obtenerSalon(@PathVariable Integer id) {
        Salon salon = salonService.obtenerSalonPorId(id);
        if (salon != null) {
            return ResponseEntity.ok(salon);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Salon>> obtenerTodosLosSalones() {
        List<Salon> salones = salonService.obtenerTodosLosSalones();
        return ResponseEntity.ok(salones);
    }

    @PutMapping
    public ResponseEntity<?> actualizarSalon(@Valid @RequestBody Salon salon, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errores = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errores);
        }
        Salon salonActualizado = salonService.actualizarSalon(salon);
        if (salonActualizado != null) {
            return ResponseEntity.ok(salonActualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> eliminarSalon(@PathVariable Integer id) {
        salonService.eliminarSalon(id);
        return ResponseEntity.noContent().build();
    }
}
