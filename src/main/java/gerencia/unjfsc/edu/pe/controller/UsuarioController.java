package gerencia.unjfsc.edu.pe.controller;

import gerencia.unjfsc.edu.pe.domain.*;
import gerencia.unjfsc.edu.pe.report.RE_Usuario;
import gerencia.unjfsc.edu.pe.service.PersonaService;
import gerencia.unjfsc.edu.pe.service.RolService;
import gerencia.unjfsc.edu.pe.service.UsuarioService;
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

    @PostMapping
    public ResponseEntity<?> crearRol(@Valid @RequestBody User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // Manejar errores de validación, como campos incorrectos
            List<String> errores = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errores);
        }
        Persona personaCreada = personaService.crearPersona(convertUserToPers(user));
        Usuario usuarioCreado = usuarioService.crearUsuario(convertUserToUsuario(user, personaCreada));

        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCreado);
    }

    private Persona convertUserToPers(User user) {
        Rol rolBuscado = rolService.obtenerRolPorNombre(user.getNombRol());
        List<Rol> list = new ArrayList<>();
        list.add(rolBuscado);
        Persona pers;
        if (user.getIdUsua() != null) {
            Usuario usuario = usuarioService.obtenerUsuarioPorId(user.getIdUsua());
            pers = new Persona(
                    usuario.getPersona().getIdPers(),
                    user.getNombPers(),
                    user.getAppaPers(),
                    user.getApmaPers(),
                    user.getDniPers(),
                    user.getTelfPers(),
                    user.getEmailPers(),
                    list
            );

        } else {
            pers = new Persona(
                    null,
                    user.getNombPers(),
                    user.getAppaPers(),
                    user.getApmaPers(),
                    user.getDniPers(),
                    user.getTelfPers(),
                    user.getEmailPers(),
                    list
            );
        }

        return pers;
    }

    private Usuario convertUserToUsuario(User user, Persona persona) {
        Usuario usuario;
        if (user.getIdUsua() != null) {
            usuario = new Usuario(
                    user.getIdUsua(),
                    user.getNombUsua(),
                    user.getPassUsua(),
                    persona
            );
        } else {
            usuario = new Usuario(
                    null,
                    user.getNombUsua(),
                    user.getPassUsua(),
                    persona
            );
        }
        return usuario;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> obtenerUsuario(@PathVariable Integer id) {
        Usuario usuario = usuarioService.obtenerUsuarioPorId(id);
        if (usuario != null) {
            return ResponseEntity.ok(usuario);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> obtenerTodosLosUsuarios() {
        List<Usuario> usuarios = usuarioService.obtenerTodosLosUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    @PutMapping
    public ResponseEntity<?> actualizarUsuario(@Valid @RequestBody User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errores = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errores);
        }
        Persona personaActualizada = personaService.actualizarPersona(convertUserToPers(user));
        Usuario usuarioActualizada = usuarioService.actualizarUsuario(convertUserToUsuario(user, personaActualizada));

        if (usuarioActualizada != null) {
            return ResponseEntity.ok(usuarioActualizada);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Integer id) {
        ;
        personaService.eliminarPersona(usuarioService.obtenerUsuarioPorId(id).getPersona().getIdPers());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/reporte")
    public ResponseEntity<?> exportInvoice() {
        List<Usuario> usuarios = usuarioService.obtenerTodosLosUsuarios();
        List<RE_Usuario> reUsuarios = new ArrayList<>();
        for (Usuario usuario : usuarios) {
            String apellidos = usuario.getPersona().getAppaPers() + " " + usuario.getPersona().getApmaPers();
            List<Rol> rols = usuario.getPersona().getRoles();
            String roles = null;
            for (Rol rol : rols) {
                roles = " " + rol.getNombRol();

            }
            reUsuarios.add(new RE_Usuario(usuario.getIdUsua(),
                    usuario.getNombUsua(),
                    usuario.getPersona().getNombPers(),
                    apellidos,
                    usuario.getPersona().getDniPers(),
                    usuario.getPersona().getEmailPers(),
                    roles));
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
