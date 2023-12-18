package gerencia.unjfsc.edu.pe.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CantidadTipoResponse {
    private int id;
    private String tincidencia;
    private long cantidad;
}
