package gerencia.unjfsc.edu.pe.repository;

import gerencia.unjfsc.edu.pe.domain.Incidencia;
import gerencia.unjfsc.edu.pe.domain.Solucion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SolucionRepository extends JpaRepository<Solucion, Integer> {
    Solucion findByIncidencia(Incidencia incidencia);
}
