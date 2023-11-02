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
@Table(name = "tb_tipo_seguimientos")
public class TipoSeguimiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_segui")
    private Integer idTipoSegui;
    @Column(name = "nomb_tipo_segui", length = 100)
    private String nombTipoSegui;
    private static final long serialVersionUID = 1L;
}
