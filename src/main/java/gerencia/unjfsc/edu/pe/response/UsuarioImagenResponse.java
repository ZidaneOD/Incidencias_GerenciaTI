package gerencia.unjfsc.edu.pe.response;

import gerencia.unjfsc.edu.pe.domain.Usuario;
import gerencia.unjfsc.edu.pe.domain.UsuarioImagen;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioImagenResponse {
    private Usuario usuario;
    private UsuarioImagen img;
}
