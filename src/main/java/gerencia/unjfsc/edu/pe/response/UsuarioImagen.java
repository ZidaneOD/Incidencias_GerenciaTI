package gerencia.unjfsc.edu.pe.response;

import gerencia.unjfsc.edu.pe.domain.Usuario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioImagen {
    private Usuario usuario;
    private byte[] img;
}
