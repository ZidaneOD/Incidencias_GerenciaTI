package gerencia.unjfsc.edu.pe.service;

import gerencia.unjfsc.edu.pe.domain.Incidencia;
import gerencia.unjfsc.edu.pe.repository.IncidenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class IncidenciaServiceImp implements IncidenciaService {
    @Autowired
    private IncidenciaRepository incidenciaRepository;

    @Override
    @Transactional
    public Incidencia crearIncidencia(Incidencia incidencia) {
        return incidenciaRepository.save(incidencia);
    }

    @Override
    @Transactional(readOnly = true)
    public Incidencia obtenerIncidenciaPorId(Integer idInci) {
        return incidenciaRepository.findById(idInci).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Incidencia> obtenerTodasLasIncidencias() {
        return incidenciaRepository.findAll();
    }

    @Override
    @Transactional
    public Incidencia actualizarIncidencia(Incidencia incidencia) {
        return incidenciaRepository.save(incidencia);
    }

    @Override
    @Transactional
    public void eliminarIncidencia(Integer idInci) {
        incidenciaRepository.deleteById(idInci);
    }
}
