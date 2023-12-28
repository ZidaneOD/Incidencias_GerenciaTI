package gerencia.unjfsc.edu.pe.controller;

import gerencia.unjfsc.edu.pe.domain.*;
import gerencia.unjfsc.edu.pe.response.CantidadSeguimientoResponse;
import gerencia.unjfsc.edu.pe.response.CantidadTipoResponse;
import gerencia.unjfsc.edu.pe.response.IncidenciaResponse;
import gerencia.unjfsc.edu.pe.service.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Connection;
import java.sql.DriverManager;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;
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
    @Value("${spring.datasource.driver-class-name}")
    String driver;
    @Value("${spring.datasource.url}")
    String url;
    @Value("${spring.datasource.username}")
    String user;
    @Value("${spring.datasource.password}")
    String password;

    private Duration tiempoRestante(Date ini, Date fin) {
        Duration duration = Duration.between(ini.toInstant(), fin.toInstant());
        return duration;
    }

    @PostMapping(produces = "application/json")
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

    @GetMapping(produces = "application/json")
    public ResponseEntity<?> obtenerIncidencia(@RequestParam Integer id) {
        Incidencia incidencia = incidenciaService.obtenerIncidenciaPorId(id);
        if (incidencia.getTipoSeguimiento().getNombTipoSegui().equals("Resuelto")) {
            incidencia.setTipoSeguimiento(new TipoSeguimiento(3, "Resuelto"));
        } else {
            incidencia.setTipoSeguimiento(new TipoSeguimiento(2, "Proceso"));
        }
        incidenciaService.actualizarIncidencia(incidencia);
        if (incidencia != null) {
            Solucion solucion = solucionService.obtenerSolucionPorIncidencia(incidencia);
            IncidenciaResponse response;
            if (solucion != null) {
                response = new IncidenciaResponse(incidencia, "" + tiempoRestante(incidencia.getFechaInci(), solucion.getFechaSolu()));
            } else {
                // La fecha actual
                Date fechaactual = new Date(System.currentTimeMillis());
                response = new IncidenciaResponse(incidencia, "" + tiempoRestante(incidencia.getFechaInci(), fechaactual));
            }
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "/{content}", produces = "application/json")
    public ResponseEntity<List<IncidenciaResponse>> bucarObtenerTodasLasIncidencias(@PathVariable String content, @RequestParam Boolean search) {
        List<IncidenciaResponse> incidenciaResponses = new ArrayList<>();
        if (search) {
            System.out.println("'NO ES NULL'");
            List<Incidencia> incidencias = incidenciaService.busIncidencias(content);
            for (Incidencia inci : incidencias) {
                Solucion solucion = solucionService.obtenerSolucionPorIncidencia(inci);
                if (solucion != null) {
                    IncidenciaResponse response = new IncidenciaResponse(inci, "" + tiempoRestante(inci.getFechaInci(), solucion.getFechaSolu()));
                    incidenciaResponses.add(response);
                } else {
                    // La fecha actual
                    Date fechaactual = new Date(System.currentTimeMillis());
                    IncidenciaResponse response = new IncidenciaResponse(inci, "" + tiempoRestante(inci.getFechaInci(), fechaactual));
                    incidenciaResponses.add(response);
                }
            }
            return ResponseEntity.ok(incidenciaResponses);
        } else {
            System.out.println("'SI ES NULL'");
            List<Incidencia> incidencias = incidenciaService.obtenerTodasLasIncidencias();
            for (Incidencia inci : incidencias) {
                Solucion solucion = solucionService.obtenerSolucionPorIncidencia(inci);
                if (solucion != null) {
                    IncidenciaResponse response = new IncidenciaResponse(inci, "" + tiempoRestante(inci.getFechaInci(), solucion.getFechaSolu()));
                    incidenciaResponses.add(response);
                } else {
                    // La fecha actual
                    Date fechaactual = new Date(System.currentTimeMillis());
                    IncidenciaResponse response = new IncidenciaResponse(inci, "" + tiempoRestante(inci.getFechaInci(), fechaactual));
                    incidenciaResponses.add(response);
                }
            }
            return ResponseEntity.ok(incidenciaResponses);
        }
    }

    @PutMapping(produces = "application/json")
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

    @DeleteMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Void> eliminarIncidencia(@PathVariable Integer id) {
        incidenciaService.eliminarIncidencia(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/reporte/seguimiento", produces = "application/json")
    public ResponseEntity<?> cantidadSeguimiento() {
        List<Object[]> total = incidenciaService.cantidadxTipoSegui();
        List<CantidadSeguimientoResponse> cantidadResponseList = new ArrayList<>();
        Map<String, CantidadSeguimientoResponse> prueba = new HashMap<>();
        int i = 1;
        for (Object[] entry : total) {
            CantidadSeguimientoResponse response = new CantidadSeguimientoResponse(i, entry[0] + "", Long.parseLong(entry[1] + ""));
            cantidadResponseList.add(response);
            i++;
            prueba.put(entry[0] + "", response);
        }
        return ResponseEntity.ok(prueba);

    }

    @GetMapping(value = "/reporte/tipo", produces = "application/json")
    public ResponseEntity<?> cantidadTipo() {
        List<Object[]> total = incidenciaService.cantidadxTipoInci();
        List<CantidadTipoResponse> cantidadTipoResponses = new ArrayList<>();
        Map<String, CantidadTipoResponse> prueba = new HashMap<>();
        int i = 1;
        for (Object[] entry : total) {
            CantidadTipoResponse response = new CantidadTipoResponse(i, entry[0] + "", Long.parseLong(entry[1] + ""));
            cantidadTipoResponses.add(response);
            i++;
            prueba.put(entry[0] + "", response);
        }
        return ResponseEntity.ok(prueba);

    }

    @GetMapping("/reporte")
    public ResponseEntity<?> exportInvoice() {
        List<Object[]> totalSegui = incidenciaService.cantidadxTipoSegui();
        List<CantidadSeguimientoResponse> cantidadResponseList = new ArrayList<>();
        int i = 1, i_1 = 1;
        for (Object[] entry : totalSegui) {
            CantidadSeguimientoResponse response = new CantidadSeguimientoResponse(i++, entry[0] + "", Long.parseLong(entry[1] + ""));
            cantidadResponseList.add(response);
        }
        List<Object[]> totalInci = incidenciaService.cantidadxTipoInci();
        List<CantidadTipoResponse> cantidadTipoResponses = new ArrayList<>();
        for (Object[] entry : totalInci) {
            CantidadTipoResponse response = new CantidadTipoResponse(i_1++, entry[0] + "", Long.parseLong(entry[1] + ""));
            cantidadTipoResponses.add(response);
        }


        try {
            Connection conn;
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, password);

            java.net.URL file = this.getClass().getClassLoader().getResource("RP_Incidencias.jasper");
            java.net.URL filelogo = this.getClass().getClassLoader().getResource("images/logoIndacochea.jpg");
            java.net.URL fileSpring = this.getClass().getClassLoader().getResource("images/logoSpring.png");

            final JasperReport report = (JasperReport) JRLoader.loadObject(file);

            java.io.InputStream IndacocheastreamForImage = filelogo.openStream();
            java.io.InputStream SpringstreamForImage = fileSpring.openStream();

            final HashMap<String, Object> parameters = new HashMap<>();
            parameters.put("logoEmpresa", IndacocheastreamForImage);
            parameters.put("logoSpring", SpringstreamForImage);
            parameters.put("dsSegui", new JRBeanCollectionDataSource(cantidadResponseList));
            parameters.put("dsTipo", new JRBeanCollectionDataSource(cantidadTipoResponses));

            final JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, conn);
            byte[] reporte = JasperExportManager.exportReportToPdf(jasperPrint);
            String sdf = (new SimpleDateFormat("dd/MM/yyyy")).format(new Date());
            StringBuilder stringBuilder = new StringBuilder().append("ReportePDF:");
            ContentDisposition contentDisposition = ContentDisposition.builder("attachment")
                    .filename(stringBuilder.append(cantidadResponseList.size())
                            .append("generateDate:").append(sdf).append(".pdf").toString())
                    .build();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentDisposition(contentDisposition);
            return ResponseEntity.ok().contentLength((long) reporte.length)
                    .contentType(MediaType.APPLICATION_PDF)
                    .headers(headers).body(new ByteArrayResource(reporte));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.noContent().build();
    }

}
