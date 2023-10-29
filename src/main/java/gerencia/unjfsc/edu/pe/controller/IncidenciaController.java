package gerencia.unjfsc.edu.pe.controller;

import gerencia.unjfsc.edu.pe.domain.*;
import gerencia.unjfsc.edu.pe.service.IncidenciaService;
import gerencia.unjfsc.edu.pe.service.SalonService;
import gerencia.unjfsc.edu.pe.service.TipoIncidenciaService;
import gerencia.unjfsc.edu.pe.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/incidencia")
public class IncidenciaController {
    @Autowired
    private IncidenciaService incidenciaService;
    @Autowired
    private SalonService salonService;
    @Autowired
    private TipoIncidenciaService tipoIncidenciaService;
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<?> crearIncidencia(@Valid @RequestBody Incidencia incidencia, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // Manejar errores de validación, como campos incorrectos
            List<String> errores = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errores);
        }
        Salon salonBuscado = salonService.obtenerSalonPorId(incidencia.getSalon().getIdSalon());
        TipoIncidencia tipoIncidenciaBuscado = tipoIncidenciaService.obtenerTipoIncidenciaPorId(incidencia.getTipoIncidencia().getIdTipoInci());
        Usuario usuarioBuscado = usuarioService.obtenerUsuarioPorId(incidencia.getUsuario().getIdUsua());

        incidencia.setSalon(salonBuscado);
        incidencia.setTipoIncidencia(tipoIncidenciaBuscado);
        incidencia.setUsuario(usuarioBuscado);
        incidencia.setTipoSeguimiento(new TipoSeguimiento(1, "Registrada"));

        Incidencia incidenciaCreada = incidenciaService.crearIncidencia(incidencia);
        return ResponseEntity.status(HttpStatus.CREATED).body(incidenciaCreada);
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<?> obtenerIncidencia(@PathVariable Integer id) {
        Incidencia incidencia = incidenciaService.obtenerIncidenciaPorId(id);
        if (incidencia != null) {
            return ResponseEntity.ok(incidencia);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Incidencia>> obtenerTodasLasIncidencias() {
        List<Incidencia> incidencias = incidenciaService.obtenerTodasLasIncidencias();
        return ResponseEntity.ok(incidencias);
    }

    @PutMapping
    public ResponseEntity<?> actualizarIncidencia(@Valid @RequestBody Incidencia incidencia, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errores = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errores);
        }
        Salon salonBuscado = salonService.obtenerSalonPorId(incidencia.getSalon().getIdSalon());
        TipoIncidencia tipoIncidenciaBuscado = tipoIncidenciaService.obtenerTipoIncidenciaPorId(incidencia.getTipoIncidencia().getIdTipoInci());
        Usuario usuarioBuscado = usuarioService.obtenerUsuarioPorId(incidencia.getUsuario().getIdUsua());

        incidencia.setSalon(salonBuscado);
        incidencia.setTipoIncidencia(tipoIncidenciaBuscado);
        incidencia.setUsuario(usuarioBuscado);

        incidencia.setFechaInci(new Date());
        Incidencia incidenciaActualizada = incidenciaService.actualizarIncidencia(incidencia);
        if (incidenciaActualizada != null) {
            return ResponseEntity.ok(incidenciaActualizada);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<Void> eliminarIncidencia(@PathVariable Integer id) {
        incidenciaService.eliminarIncidencia(id);
        return ResponseEntity.noContent().build();
    }
}
