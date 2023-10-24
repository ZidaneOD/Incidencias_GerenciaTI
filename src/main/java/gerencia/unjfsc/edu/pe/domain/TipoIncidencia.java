package gerencia.unjfsc.edu.pe.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_tipo_incidencias")
public class TipoIncidencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_inci")
    private Integer idTipoInci;
    @Column(name = "nomb_tipo_inci", length = 100)
    private String nombTipoInci;
    @Column(name = "dias_tipo_inci")
    private Integer diasTipoInci;
    @Column(name = "presu_tipo_inci")
    private Double presuTipoInci;
    @ManyToOne
    @JoinColumn(name = "id_prio")
    private Prioridad prioridad;
    private static final long serialVersionUID = 1L;
}
