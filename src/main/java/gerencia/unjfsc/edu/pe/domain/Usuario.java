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
@Table(name = "tb_usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usua")
    private Integer idUsua;
    @Column(name = "nomb_usua", length = 100)
    private String nombUsua;
    @Column(name = "pass_usua")
    private String passUsua;
    @OneToOne
    @JoinColumn(name = "id_pers", unique = true)
    private Persona persona;
    private static final long serialVersionUID = 1L;
}
