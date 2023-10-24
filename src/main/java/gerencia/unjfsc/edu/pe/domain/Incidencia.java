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
@Table(name = "tb_incidencias")
public class Incidencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_inci")
    private Integer idInci;
    @ManyToOne
    @JoinColumn(name = "id_salon")
    private Salon salon;
    @ManyToOne
    @JoinColumn(name = "id_tipo_inci")
    private TipoIncidencia tipoIncidencia;
    @Column(name = "nomb_inci",length = 100)
    private String nombInci;
    @Column(name = "desc_inci",length = 200)
    private String descInci;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Column(name = "fecha_inci")
    private Date fechaInci;
    @ManyToOne
    @JoinColumn(name = "id_usua")
    private Usuario usuario;
    private static final long serialVersionUID = 1L;
}
