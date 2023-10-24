package gerencia.unjfsc.edu.pe.service;

import gerencia.unjfsc.edu.pe.domain.Persona;

import java.util.List;

public interface PersonaService {
    Persona crearPersona(Persona persona);

    Persona obtenerPersonaPorId(Integer idPers);

    List<Persona> obtenerTodasLasPersonas();

    Persona actualizarPersona(Persona persona);

    void eliminarPersona(Integer idPers);
}
