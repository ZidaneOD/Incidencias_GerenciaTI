package gerencia.unjfsc.edu.pe.service;

import gerencia.unjfsc.edu.pe.domain.Persona;
import gerencia.unjfsc.edu.pe.repository.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PersonaServiceImp implements PersonaService{
    @Autowired
    private PersonaRepository personaRepository;
    @Override
    @Transactional
    public Persona crearPersona(Persona persona) {
        return personaRepository.save(persona);
    }

    @Override
    @Transactional(readOnly = true)
    public Persona obtenerPersonaPorId(Integer idPers) {
        return personaRepository.findById(idPers).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Persona> obtenerTodasLasPersonas() {
        return personaRepository.findAll();
    }

    @Override
    @Transactional
    public Persona actualizarPersona(Persona persona) {
        return personaRepository.save(persona);
    }

    @Override
    @Transactional
    public void eliminarPersona(Integer idPers) {
        personaRepository.deleteById(idPers);
    }
}
