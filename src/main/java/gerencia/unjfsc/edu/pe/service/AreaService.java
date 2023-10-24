package gerencia.unjfsc.edu.pe.service;

import gerencia.unjfsc.edu.pe.domain.Area;

import java.util.List;

public interface AreaService {
    Area crearArea(Area area);

    Area obtenerPorId(Integer idArea);

    List<Area> obtenerTodasLasAreas();

    Area actualizarArea(Area area);

    void eliminarArea(Integer idArea);
}
