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
@Table(name = "tb_soluciones")
public class Solucion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_solu")
    private Integer idSolu;
    @ManyToOne
    @JoinColumn(name = "id_inci")
    private Incidencia incidencia;
    @Column(name = "desc_solu", length = 150)
    private String descSolu;
    @Column(name = "costo_solu")
    private Double costoSolu;
    @ManyToOne
    @JoinColumn(name = "id_usua")
    private Usuario usuario;
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @Column(name = "fecha_solu")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(timezone = "GMT-5:00")
    private Date fechaSolu;
    @PrePersist
    public void prePersist() {
        fechaSolu = new Date();
    }

    private static final long serialVersionUID = 1L;
}
