package gerencia.unjfsc.edu.pe.service;

import gerencia.unjfsc.edu.pe.domain.TipoIncidencia;
import gerencia.unjfsc.edu.pe.repository.TipoIncideciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TipoIncidenciaServiceImp implements TipoIncidenciaService {
    @Autowired
    private TipoIncideciaRepository tipoIncideciaRepository;

    @Override
    @Transactional
    public TipoIncidencia crearTipoIncidencia(TipoIncidencia tipoIncidencia) {
        return tipoIncideciaRepository.save(tipoIncidencia);
    }

    @Override
    @Transactional(readOnly = true)
    public TipoIncidencia obtenerTipoIncidenciaPorId(Integer idTipoInci) {
        return tipoIncideciaRepository.findById(idTipoInci).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TipoIncidencia> obtenerTodosLosTiposIncidencias() {
        return tipoIncideciaRepository.findAll();
    }

    @Override
    @Transactional
    public TipoIncidencia actualizarTipoIncidencia(TipoIncidencia tipoIncidencia) {
        return tipoIncideciaRepository.save(tipoIncidencia);
    }

    @Override
    @Transactional
    public void eliminarTipoIncidencia(Integer idTipoInci) {
        tipoIncideciaRepository.deleteById(idTipoInci);
    }
}
