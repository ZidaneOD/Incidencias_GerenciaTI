package gerencia.unjfsc.edu.pe.report;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RE_Usuario {
    private Integer idUsua;
    private String nombUsua;
    private String nombPers;
    private String apellidosPers;
    private String dniPers;
    private String emailPers;
    private String roles;
}
