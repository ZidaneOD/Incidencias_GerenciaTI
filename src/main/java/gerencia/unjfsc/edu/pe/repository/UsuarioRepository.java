package gerencia.unjfsc.edu.pe.repository;

import gerencia.unjfsc.edu.pe.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
}
