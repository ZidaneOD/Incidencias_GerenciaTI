package gerencia.unjfsc.edu.pe.service;

import gerencia.unjfsc.edu.pe.domain.UsuarioImagen;
import gerencia.unjfsc.edu.pe.repository.UsuarioImagenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UsuarioImagenServiceImp implements UsuarioImagenService {
    @Autowired
    private UsuarioImagenRepository usuarioImagenRepository;

    @Override
    @Transactional
    public UsuarioImagen crearUsuaImg(UsuarioImagen usuarioImagen) {
        UsuarioImagen creado = usuarioImagenRepository.save(usuarioImagen);
        if (creado != null) {
            return creado;
        }
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioImagen obtenerImgPorId(Integer idUsua) {
        UsuarioImagen buscado = usuarioImagenRepository.findByPorUsuario(idUsua);
        if (buscado != null) {
            return buscado;
        }
        return null;
    }

    @Override
    @Transactional
    public UsuarioImagen actualizarImg(UsuarioImagen usuarioImagen) {
        return usuarioImagenRepository.save(usuarioImagen);
    }

    @Override
    @Transactional
    public void eliminarSalon(Integer idUsua) {

        usuarioImagenRepository.deleteByUsuarioId(idUsua);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioImagen> obtenerLista() {
        return usuarioImagenRepository.findAll();
    }
}
