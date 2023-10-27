package gerencia.unjfsc.edu.pe.repository;

import gerencia.unjfsc.edu.pe.domain.Salon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalonRepository extends JpaRepository<Salon, Integer> {
    Salon findByNombSalon(String nombSalon);
}
