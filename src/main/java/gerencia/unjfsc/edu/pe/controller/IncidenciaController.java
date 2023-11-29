package gerencia.unjfsc.edu.pe.controller;

import gerencia.unjfsc.edu.pe.domain.*;
import gerencia.unjfsc.edu.pe.response.IncidenciaResponse;
import gerencia.unjfsc.edu.pe.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.*;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
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
    @Autowired
    private SolucionService solucionService;

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

    @GetMapping
    public ResponseEntity<?> obtenerIncidencia(@RequestParam Integer id) {
        Incidencia incidencia = incidenciaService.obtenerIncidenciaPorId(id);
        if (incidencia.getTipoSeguimiento().getNombTipoSegui().equals("Resuelto")) {
            incidencia.setTipoSeguimiento(new TipoSeguimiento(3, "Resuelto"));
        } else {
            incidencia.setTipoSeguimiento(new TipoSeguimiento(2, "Proceso"));
        }
        incidenciaService.actualizarIncidencia(incidencia);
        if (incidencia != null) {
            return ResponseEntity.ok(incidencia);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "/{search}")
    public ResponseEntity<List<IncidenciaResponse>> bucarObtenerTodasLasIncidencias(@PathVariable String search) {
        List<IncidenciaResponse> incidenciaResponses = new ArrayList<>();
        int milisecondsByDay = 86400000;
        if (search != null) {
            List<Incidencia> incidencias = incidenciaService.busIncidencias(search);
            for (Incidencia inci : incidencias) {
                Solucion solucion = solucionService.obtenerSolucionPorIncidencia(inci);
                if (solucion != null) {
                    int dias = (int) ((solucion.getFechaSolu().getTime() - inci.getFechaInci().getTime()) / milisecondsByDay);
                    int diasFaltantes = inci.getTipoIncidencia().getDiasTipoInci() - dias;
                    IncidenciaResponse response = new IncidenciaResponse(inci, diasFaltantes, dias);
                    incidenciaResponses.add(response);
                } else {
                    // La fecha actual
                    Date fechaactual = new Date(System.currentTimeMillis());
                    int dias = (int) ((fechaactual.getTime() - inci.getFechaInci().getTime()) / milisecondsByDay);
                    int diasFaltantes = inci.getTipoIncidencia().getDiasTipoInci() - dias;
                    IncidenciaResponse response = new IncidenciaResponse(inci, diasFaltantes, dias);
                    incidenciaResponses.add(response);
                }
            }
            return ResponseEntity.ok(incidenciaResponses);
        } else {
            List<Incidencia> incidencias = incidenciaService.obtenerTodasLasIncidencias();
            for (Incidencia inci : incidencias) {
                Solucion solucion = solucionService.obtenerSolucionPorIncidencia(inci);
                if (solucion != null) {
                    int dias = (int) ((solucion.getFechaSolu().getTime() - inci.getFechaInci().getTime()) / milisecondsByDay);
                    int diasFaltantes = inci.getTipoIncidencia().getDiasTipoInci() - dias;
                    IncidenciaResponse response = new IncidenciaResponse(inci, diasFaltantes, dias);
                    incidenciaResponses.add(response);
                } else {
                    // La fecha actual
                    Date fechaactual = new Date(System.currentTimeMillis());
                    int dias = (int) ((fechaactual.getTime() - inci.getFechaInci().getTime()) / milisecondsByDay);
                    int diasFaltantes = inci.getTipoIncidencia().getDiasTipoInci() - dias;
                    IncidenciaResponse response = new IncidenciaResponse(inci, diasFaltantes, dias);
                    incidenciaResponses.add(response);
                }
            }
            return ResponseEntity.ok(incidenciaResponses);
        }
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
