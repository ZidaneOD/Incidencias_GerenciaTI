package gerencia.unjfsc.edu.pe.service;

import gerencia.unjfsc.edu.pe.domain.Rol;

import java.util.List;

public interface RolService {
    Rol crearRol(Rol rol);

    Rol obtenerRolPorId(Integer idRol);

    List<Rol> obtenerTodosLosRoles();

    Rol actualizarRol(Rol rol);

    void eliminarRol(Integer idRol);
    Rol obtenerRolPorNombre(String nombRol);
}
