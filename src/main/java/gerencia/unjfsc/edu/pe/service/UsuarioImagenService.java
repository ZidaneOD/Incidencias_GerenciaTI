package gerencia.unjfsc.edu.pe.service;

import gerencia.unjfsc.edu.pe.domain.UsuarioImagen;

import java.util.List;

public interface UsuarioImagenService {
    UsuarioImagen crearUsuaImg(UsuarioImagen usuarioImagen);

    UsuarioImagen obtenerImgPorId(Integer idUsua);

    UsuarioImagen actualizarImg(UsuarioImagen usuarioImagen);

    void eliminarSalon(Integer idUsuaS3);

    List<UsuarioImagen> obtenerLista();
}
