package gerencia.unjfsc.edu.pe.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Integer idUsua;
    private String nombUsua;
    private String passUsua;
    private String nombPers;
    private String appaPers;
    private String apmaPers;
    private String dniPers;
    private String telfPers;
    private String emailPers;
    private String nombRol;
    private String nombImg;
    private static final long serialVersionUID = 1L;
}
