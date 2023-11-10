package gerencia.unjfsc.edu.pe.service;

import gerencia.unjfsc.edu.pe.domain.Incidencia;
import gerencia.unjfsc.edu.pe.domain.Solucion;

import java.util.List;

public interface SolucionService {
    Solucion crearSolucion(Solucion solucion);

    Solucion obtenerSolucionPorId(Integer idSolu);

    Solucion obtenerSolucionPorIncidencia(Incidencia incidencia);

    List<Solucion> obtenerTodasLasSoluciones();

    Solucion actualizarSolucion(Solucion solucion);


    void eliminarSolucion(Integer idSolu);
}
