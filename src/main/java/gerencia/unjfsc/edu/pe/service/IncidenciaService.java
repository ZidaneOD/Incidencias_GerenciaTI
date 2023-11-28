package gerencia.unjfsc.edu.pe.service;

import gerencia.unjfsc.edu.pe.domain.Incidencia;

import java.util.List;

public interface IncidenciaService {
    Incidencia crearIncidencia(Incidencia incidencia);

    Incidencia obtenerIncidenciaPorId(Integer idInci);

    List<Incidencia> obtenerTodasLasIncidencias();

    Incidencia actualizarIncidencia(Incidencia incidencia);

    void eliminarIncidencia(Integer idInci);
    List<Incidencia> busIncidencias(String buscar);
}
