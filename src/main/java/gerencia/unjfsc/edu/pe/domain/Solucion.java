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
@Table(name = "tb_soluciones")
public class Solucion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_solu")
    private Integer idSolu;
    @ManyToOne
    @JoinColumn(name = "id_inci")
    private Incidencia incidencia;
    @ManyToOne
    @JoinColumn(name = "id_tipo_segui")
    private TipoSeguimiento tipoSeguimiento;
    @Column(name = "desc_solu",length = 150)
    private String descSolu;
    @Column(name = "costo_solu")
    private Double costoSolu;
    @ManyToOne
    @JoinColumn(name = "id_usua")
    private Usuario usuario;
    private static final long serialVersionUID = 1L;
}
