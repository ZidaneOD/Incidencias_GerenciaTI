package gerencia.unjfsc.edu.pe.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_personas")
public class Persona {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pers")
    private Integer idPers;
    @Column(name = "nomb_pers", length = 100)
    private String nombPers;
    @Column(name = "appa_pers", length = 100)
    private String appaPers;
    @Column(name = "apma_pers", length = 100)
    private String apmaPers;
    @Column(name = "dni_pers", length = 8)
    private String dniPers;
    @Column(name = "telf_pers", length = 9)
    private String telfPers;
    @Column(name = "email_pers", length = 200, unique = true)
    private String emailPers;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_rol")
    private Rol rol;
    private static final long serialVersionUID = 1L;
}
