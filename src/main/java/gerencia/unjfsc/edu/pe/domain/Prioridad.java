package gerencia.unjfsc.edu.pe.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_prioridades")
public class Prioridad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_prio")
    private Integer idPrio;
    @Column(name = "nomb_prio", length = 100)
    private String nombPrio;
    private static final long serialVersionUID = 1L;
}
