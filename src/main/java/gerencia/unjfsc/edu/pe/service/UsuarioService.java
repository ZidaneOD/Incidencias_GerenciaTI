package gerencia.unjfsc.edu.pe.service;

import gerencia.unjfsc.edu.pe.domain.Usuario;

import java.util.List;

public interface UsuarioService {
    Usuario crearUsuario(Usuario usuario);

    Usuario obtenerUsuarioPorId(Integer idUsua);

    List<Usuario> obtenerTodosLosUsuarios();

    Usuario actualizarUsuario(Usuario usuario);

    void eliminarUsuario(Integer idUsua);

}
