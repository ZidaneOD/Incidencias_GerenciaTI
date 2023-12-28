package gerencia.unjfsc.edu.pe.response;

import gerencia.unjfsc.edu.pe.domain.Incidencia;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IncidenciaResponse {
    private Incidencia incidencia;
    private String diasSolucion;

}
