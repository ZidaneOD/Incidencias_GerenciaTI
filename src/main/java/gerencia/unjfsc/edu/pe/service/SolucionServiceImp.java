package gerencia.unjfsc.edu.pe.service;

import gerencia.unjfsc.edu.pe.domain.Solucion;
import gerencia.unjfsc.edu.pe.repository.SolucionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SolucionServiceImp implements SolucionService {
    @Autowired
    private SolucionRepository solucionRepository;

    @Override
    @Transactional
    public Solucion crearSolucion(Solucion solucion) {
        return solucionRepository.save(solucion);
    }

    @Override
    @Transactional(readOnly = true)
    public Solucion obtenerSolucionPorId(Integer idSolu) {
        return solucionRepository.findById(idSolu).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Solucion> obtenerTodasLasSoluciones() {
        return solucionRepository.findAll();
    }

    @Override
    @Transactional
    public Solucion actualizarSolucion(Solucion solucion) {
        return solucionRepository.save(solucion);
    }

    @Override
    @Transactional
    public void eliminarSolucion(Integer idSolu) {
        solucionRepository.deleteById(idSolu);
    }
}
