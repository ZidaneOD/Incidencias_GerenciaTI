package gerencia.unjfsc.edu.pe.repository;

import gerencia.unjfsc.edu.pe.domain.Incidencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;

import java.util.List;

public interface IncidenciaRepository extends JpaRepository<Incidencia, Integer> {
    @Query("""
            select i from Incidencia i
            where upper(i.nombInci) like upper(:nombInci) or upper(i.descInci) like upper(:descInci) or upper(i.salon.nombSalon) like upper(:nombSalon) or upper(i.salon.area.nombArea) like upper(:nombArea) or upper(i.tipoIncidencia.nombTipoInci) like upper(:nombTipoInci) or upper(i.tipoSeguimiento.nombTipoSegui) like upper(:nombTipoSegui) or upper(i.usuario.nombUsua) like upper(:nombUsua) or upper(i.usuario.persona.nombPers) like upper(:nombPers)""")
    @Nullable
    List<Incidencia> BuscarIncidencia(@Param("nombInci") @Nullable String nombInci, @Param("descInci") @Nullable String descInci, @Param("nombSalon") @Nullable String nombSalon, @Param("nombArea") @Nullable String nombArea, @Param("nombTipoInci") @Nullable String nombTipoInci, @Param("nombTipoSegui") @Nullable String nombTipoSegui, @Param("nombUsua") @Nullable String nombUsua, @Param("nombPers") @Nullable String nombPers);

    @Query(value = "SELECT s.nomb_tipo_segui,count(*) FROM tb_incidencias i INNER JOIN tb_tipo_seguimientos s ON i.id_tipo_segui = s.id_tipo_segui GROUP BY s.nomb_tipo_segui", nativeQuery = true)
    @Nullable
    List<Object[]> CantidadSeguimientos();

    @Query(value = "SELECT s.nomb_tipo_inci,count(*) FROM tb_incidencias i INNER JOIN tb_tipo_incidencias s ON i.id_tipo_inci = s.id_tipo_inci GROUP BY s.nomb_tipo_inci", nativeQuery = true)
    @Nullable
    List<Object[]> CantidadTipoIncidencia();
}
