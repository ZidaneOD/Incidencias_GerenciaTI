package gerencia.unjfsc.edu.pe.repository;

import gerencia.unjfsc.edu.pe.domain.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolRepository extends JpaRepository<Rol, Integer> {
    Rol findByNombRol(String nombRol);
}
