package gerencia.unjfsc.edu.pe.controller;

import gerencia.unjfsc.edu.pe.domain.*;
import gerencia.unjfsc.edu.pe.report.REUsuario;
import gerencia.unjfsc.edu.pe.request.UserRequest;
import gerencia.unjfsc.edu.pe.response.UsuarioImagenResponse;
import gerencia.unjfsc.edu.pe.service.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {
    @Autowired
    private RolService rolService;
    @Autowired
    private PersonaService personaService;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private UsuarioImagenService usuarioImagenService;
    @Autowired
    private CloudinaryService cloudinaryService;

    @PostMapping(produces = "application/json")
    public ResponseEntity<?> crearUsuario(@Valid @RequestBody UserRequest userRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // Manejar errores de validación, como campos incorrectos
            List<String> errores = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errores);
        }
        Persona personaCreada = personaService.crearPersona(convertUserToPers(userRequest));
        Usuario usuarioCreado = usuarioService.crearUsuario(convertUserToUsuario(userRequest, personaCreada));
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCreado);
    }

    private Persona convertUserToPers(UserRequest userRequest) {
        Rol rolBuscado = rolService.obtenerRolPorNombre(userRequest.getNombRol());
        Persona pers;
        if (userRequest.getIdUsua() != null) {
            Usuario usuario = usuarioService.obtenerUsuarioPorId(userRequest.getIdUsua());
            pers = new Persona(
                    usuario.getPersona().getIdPers(),
                    userRequest.getNombPers(),
                    userRequest.getAppaPers(),
                    userRequest.getApmaPers(),
                    userRequest.getDniPers(),
                    userRequest.getTelfPers(),
                    userRequest.getEmailPers(),
                    rolBuscado
            );

        } else {
            pers = new Persona(
                    null,
                    userRequest.getNombPers(),
                    userRequest.getAppaPers(),
                    userRequest.getApmaPers(),
                    userRequest.getDniPers(),
                    userRequest.getTelfPers(),
                    userRequest.getEmailPers(),
                    rolBuscado
            );
        }

        return pers;
    }

    private Usuario convertUserToUsuario(UserRequest userRequest, Persona persona) {
        Usuario usuario;
        if (userRequest.getIdUsua() != null) {
            usuario = new Usuario(
                    userRequest.getIdUsua(),
                    userRequest.getNombUsua(),
                    userRequest.getPassUsua(),
                    persona
            );
        } else {
            usuario = new Usuario(
                    null,
                    userRequest.getNombUsua(),
                    userRequest.getPassUsua(),
                    persona
            );
        }
        return usuario;
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<?> obtenerUsuario(@PathVariable Integer id) {
        Usuario usuario = usuarioService.obtenerUsuarioPorId(id);
        if (usuario != null) {
            UsuarioImagen img = usuarioImagenService.obtenerImgPorId(usuario.getIdUsua());
            if (img != null) {
                UsuarioImagenResponse request = new UsuarioImagenResponse(img.getUsuario(), img);
                return ResponseEntity.ok().body(request);
            }
            UsuarioImagenResponse request = new UsuarioImagenResponse(usuario, null);
            return ResponseEntity.ok().body(request);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<UsuarioImagenResponse>> obtenerTodosLosUsuarios() {
        List<Usuario> usuarios = usuarioService.obtenerTodosLosUsuarios();
        List<UsuarioImagenResponse> usuarioImagenResponses = new ArrayList<>();

        for (Usuario usuario : usuarios) {
            UsuarioImagen img = usuarioImagenService.obtenerImgPorId(usuario.getIdUsua());
            if (img != null) {

                UsuarioImagenResponse request = new UsuarioImagenResponse(img.getUsuario(), img);
                usuarioImagenResponses.add(request);
            } else {
                UsuarioImagenResponse request = new UsuarioImagenResponse(usuario, null);
                usuarioImagenResponses.add(request);
            }
        }
        return ResponseEntity.ok(usuarioImagenResponses);
    }

    @PutMapping(produces = "application/json")
    public ResponseEntity<?> actualizarUsuario(@Valid @RequestBody UserRequest userRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errores = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errores);
        }
        Persona personaActualizada = personaService.actualizarPersona(convertUserToPers(userRequest));
        Usuario usuarioActualizada = usuarioService.actualizarUsuario(convertUserToUsuario(userRequest, personaActualizada));

        if (usuarioActualizada != null) {
            return ResponseEntity.ok(usuarioActualizada);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Integer id) {
        personaService.eliminarPersona(usuarioService.obtenerUsuarioPorId(id).getPersona().getIdPers());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/reporte")
    public ResponseEntity<?> exportInvoice() {
        List<Usuario> usuarios = usuarioService.obtenerTodosLosUsuarios();
        List<REUsuario> reUsuarios = new ArrayList<>();
        for (Usuario usuario : usuarios) {
            String apellidos = usuario.getPersona().getAppaPers() + " " + usuario.getPersona().getApmaPers();
            Rol rols = usuario.getPersona().getRol();

            reUsuarios.add(new REUsuario(usuario.getIdUsua(),
                    usuario.getNombUsua(),
                    usuario.getPersona().getNombPers(),
                    apellidos,
                    usuario.getPersona().getDniPers(),
                    usuario.getPersona().getEmailPers(),
                    rols.getNombRol()));
        }
        try {
            java.net.URL file = this.getClass().getClassLoader().getResource("RP_Usuarios.jasper");
            java.net.URL filelogo = this.getClass().getClassLoader().getResource("images/logoIndacochea.jpg");
            java.net.URL fileSpring = this.getClass().getClassLoader().getResource("images/logoSpring.png");

            final JasperReport report = (JasperReport) JRLoader.loadObject(file);

            java.io.InputStream IndacocheastreamForImage = filelogo.openStream();
            java.io.InputStream SpringstreamForImage = fileSpring.openStream();

            final HashMap<String, Object> parameters = new HashMap<>();
            parameters.put("logoEmpresa", IndacocheastreamForImage);
            parameters.put("logoSpring", SpringstreamForImage);
            parameters.put("ds", new JRBeanCollectionDataSource(reUsuarios));

            final JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, new JREmptyDataSource());
            byte[] reporte = JasperExportManager.exportReportToPdf(jasperPrint);
            String sdf = (new SimpleDateFormat("dd/MM/yyyy")).format(new Date());
            StringBuilder stringBuilder = new StringBuilder().append("ReportePDF:");
            ContentDisposition contentDisposition = ContentDisposition.builder("attachment")
                    .filename(stringBuilder.append(usuarios.size())
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
