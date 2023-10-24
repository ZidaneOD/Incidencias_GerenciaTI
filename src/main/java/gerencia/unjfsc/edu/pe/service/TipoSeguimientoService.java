package gerencia.unjfsc.edu.pe.service;

import gerencia.unjfsc.edu.pe.domain.TipoIncidencia;
import gerencia.unjfsc.edu.pe.domain.TipoSeguimiento;

import java.util.List;

public interface TipoSeguimientoService {
    TipoSeguimiento crearTipoSeguimiento(TipoSeguimiento tipoSeguimiento);

    TipoSeguimiento obtenerTipoSeguimientoPorId(Integer idTipoSegui);

    List<TipoSeguimiento> obtenerTodosLosTiposSeguimientos();

    TipoSeguimiento actualizarTipoSeguimiento(TipoSeguimiento tipoSeguimiento);

    void eliminarTipoSeguimiento(Integer idTipoSegui);
}
