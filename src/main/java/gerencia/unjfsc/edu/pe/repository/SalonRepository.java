package gerencia.unjfsc.edu.pe.repository;

import gerencia.unjfsc.edu.pe.domain.Salon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SalonRepository extends JpaRepository<Salon, Integer> {
    Salon findByNombSalon(String nombSalon);

    @Query(value = "SELECT * FROM tb_salones s WHERE s.id_area=:idarea", nativeQuery = true)
    List<Salon> findByArea(Integer idarea);
}
