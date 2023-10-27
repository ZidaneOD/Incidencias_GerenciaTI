package gerencia.unjfsc.edu.pe.service;

import gerencia.unjfsc.edu.pe.domain.TipoIncidencia;

import java.util.List;

public interface TipoIncidenciaService {
    TipoIncidencia crearTipoIncidencia(TipoIncidencia tipoIncidencia);

    TipoIncidencia obtenerTipoIncidenciaPorId(Integer idTipoInci);

    TipoIncidencia obtenerTipoInciPorNomTipoInci(String nombTipoInci);

    List<TipoIncidencia> obtenerTodosLosTiposIncidencias();

    TipoIncidencia actualizarTipoIncidencia(TipoIncidencia tipoIncidencia);

    void eliminarTipoIncidencia(Integer idTipoInci);
}
