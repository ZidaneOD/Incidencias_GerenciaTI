package gerencia.unjfsc.edu.pe.service;

import gerencia.unjfsc.edu.pe.domain.Prioridad;
import gerencia.unjfsc.edu.pe.repository.PrioridadRepository;
import gerencia.unjfsc.edu.pe.repository.SalonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PrioridadServiceImp implements PrioridadService {
    @Autowired
    private PrioridadRepository prioridadRepository;

    @Override
    @Transactional
    public Prioridad crearPrioridad(Prioridad prioridad) {
        return prioridadRepository.save(prioridad);
    }

    @Override
    @Transactional(readOnly = true)
    public Prioridad obtenerPrioridadPorId(Integer idPrio) {
        return prioridadRepository.findById(idPrio).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Prioridad> obtnerTodasLasPrioridades() {
        return prioridadRepository.findAll();
    }

    @Override
    @Transactional
    public Prioridad actualizarPrioridad(Prioridad prioridad) {
        return prioridadRepository.save(prioridad);
    }

    @Override
    @Transactional
    public void eliminarPrioridad(Integer idPrio) {
        prioridadRepository.deleteById(idPrio);
    }
}
