package gerencia.unjfsc.edu.pe.service;

import gerencia.unjfsc.edu.pe.domain.TipoSeguimiento;
import gerencia.unjfsc.edu.pe.repository.TipoSeguimientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TipoSeguimientoServiceImp implements TipoSeguimientoService {
    @Autowired
    private TipoSeguimientoRepository tipoSeguimientoRepository;

    @Override
    @Transactional
    public TipoSeguimiento crearTipoSeguimiento(TipoSeguimiento tipoSeguimiento) {
        return tipoSeguimientoRepository.save(tipoSeguimiento);
    }

    @Override
    @Transactional(readOnly = true)
    public TipoSeguimiento obtenerTipoSeguimientoPorId(Integer idTipoSegui) {
        return tipoSeguimientoRepository.findById(idTipoSegui).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TipoSeguimiento> obtenerTodosLosTiposSeguimientos() {
        return tipoSeguimientoRepository.findAll();
    }

    @Override
    @Transactional
    public TipoSeguimiento actualizarTipoSeguimiento(TipoSeguimiento tipoSeguimiento) {
        return tipoSeguimientoRepository.save(tipoSeguimiento);
    }

    @Override
    @Transactional
    public void eliminarTipoSeguimiento(Integer idTipoSegui) {
        tipoSeguimientoRepository.deleteById(idTipoSegui);
    }
}
