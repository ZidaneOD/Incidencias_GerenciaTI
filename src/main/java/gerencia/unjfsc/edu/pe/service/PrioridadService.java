package gerencia.unjfsc.edu.pe.service;

import gerencia.unjfsc.edu.pe.domain.Prioridad;

import java.util.List;

public interface PrioridadService {
    Prioridad crearPrioridad(Prioridad prioridad);

    Prioridad obtenerPrioridadPorId(Integer idPrio);

    List<Prioridad> obtnerTodasLasPrioridades();

    Prioridad actualizarPrioridad(Prioridad prioridad);

    void eliminarPrioridad(Integer idPrio);
}
