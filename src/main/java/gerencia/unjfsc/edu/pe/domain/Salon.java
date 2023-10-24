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
@Table(name = "tb_salones")
public class Salon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_salon")
    private Integer idSalon;
    @Column(name = "nomb_salon", length = 100)
    private String nombSalon;
    @ManyToOne
    @JoinColumn(name = "id_area", nullable = true)
    private Area area;
    private static final long serialVersionUID = 1L;
}
