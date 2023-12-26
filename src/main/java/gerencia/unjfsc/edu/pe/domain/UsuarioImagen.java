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
@Table(name = "tb_usua_s3")
public class UsuarioImagen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usua_s3")
    private Integer idUsuaS3;
    @ManyToOne
    @JoinColumn(name = "id_usua")
    private Usuario usuario;
    @Column(name = "nomb_img", length = 200)
    private String nombImg;
    @Column(name = "url_img", length = 200)
    private String urlImg;
    @Column(name = "id_img", length = 200)
    private String idImg;
    private static final long serialVersionUID = 1L;
}
