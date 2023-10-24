package gerencia.unjfsc.edu.pe.service;

import gerencia.unjfsc.edu.pe.domain.Rol;

import gerencia.unjfsc.edu.pe.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RolServiceImp implements RolService {
    @Autowired
    private RolRepository rolRepository;

    @Override
    @Transactional
    public Rol crearRol(Rol rol) {
        return rolRepository.save(rol);
    }

    @Override
    @Transactional(readOnly = true)
    public Rol obtenerRolPorId(Integer idRol) {
        return rolRepository.findById(idRol).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Rol> obtenerTodosLosRoles() {
        return rolRepository.findAll();
    }

    @Override
    @Transactional
    public Rol actualizarRol(Rol rol) {
        return rolRepository.save(rol);
    }

    @Override
    @Transactional
    public void eliminarRol(Integer idRol) {
        rolRepository.deleteById(idRol);
    }

    @Override
    @Transactional(readOnly = true)
    public Rol obtenerRolPorNombre(String nombRol) {
        return rolRepository.findByNombRol(nombRol);
    }
}
