package gerencia.unjfsc.edu.pe.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

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
    @Column(name = "nomb_inci", length = 100)
    private String nombInci;
    @Column(name = "desc_inci", length = 200)
    private String descInci;
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @Column(name = "fecha_inci")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(timezone = "GMT+1:00")
    private Date fechaInci;

    @PrePersist
    public void prePersist() {
        fechaInci = new Date();
    }

    @ManyToOne
    @JoinColumn(name = "id_tipo_segui")
    private TipoSeguimiento tipoSeguimiento;
    @ManyToOne
    @JoinColumn(name = "id_usua")
    private Usuario usuario;
    private static final long serialVersionUID = 1L;
}
