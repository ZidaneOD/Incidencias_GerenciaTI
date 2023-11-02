package gerencia.unjfsc.edu.pe.report;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class REUsuario {
    private Integer idUsua;
    private String nombUsua;
    private String nombPers;
    private String apellidosPers;
    private String dniPers;
    private String emailPers;
    private String roles;
}
