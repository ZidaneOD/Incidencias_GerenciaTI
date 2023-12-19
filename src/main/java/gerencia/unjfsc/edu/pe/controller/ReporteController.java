package gerencia.unjfsc.edu.pe.controller;

import gerencia.unjfsc.edu.pe.response.CantidadSeguimientoResponse;
import gerencia.unjfsc.edu.pe.response.CantidadTipoResponse;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.DriverManager;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(value = "api/reporte")
public class ReporteController {

    @Value("${spring.datasource.driver-class-name}")
    String driver;
    @Value("${spring.datasource.url}")
    String url;
    @Value("${spring.datasource.username}")
    String user;
    @Value("${spring.datasource.password}")
    String password;
    @Value("${logoInsignia}")
    String insignia;
    @Value("${logoSpring}")
    String spring;

    public ResponseEntity<?> reporte(String nameJasper) {
        try {
            Connection conn;
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, password);

            java.net.URL file = this.getClass().getClassLoader().getResource(nameJasper);
            java.net.URL filelogo = this.getClass().getClassLoader().getResource(insignia);
            java.net.URL fileSpring = this.getClass().getClassLoader().getResource(spring);

            final JasperReport report = (JasperReport) JRLoader.loadObject(file);

            java.io.InputStream IndacocheastreamForImage = filelogo.openStream();
            java.io.InputStream SpringstreamForImage = fileSpring.openStream();

            final HashMap<String, Object> parameters = new HashMap<>();
            parameters.put("logoEmpresa", IndacocheastreamForImage);
            parameters.put("logoSpring", SpringstreamForImage);

            final JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, conn);
            byte[] reporte = JasperExportManager.exportReportToPdf(jasperPrint);
            String sdf = (new SimpleDateFormat("dd/MM/yyyy")).format(new Date());
            StringBuilder stringBuilder = new StringBuilder().append("ReportePDF:");
            ContentDisposition contentDisposition = ContentDisposition.builder("attachment")
                    .filename(stringBuilder.append((int) (Math.random() * 100) + 1)
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


    @GetMapping("/solucion")
    public ResponseEntity<?> report_soluciones() {
        return reporte("RP_Soluciones.jasper");
    }
    @GetMapping("/incidenciaall")
    public ResponseEntity<?> report_incidencias() {
        return reporte("RP_IncidenciasFull.jasper");
    }
    @GetMapping("/areaxsalon")
    public ResponseEntity<?> report_areasalon() {
        return reporte("RP_Areas_Salon.jasper");
    }


}
