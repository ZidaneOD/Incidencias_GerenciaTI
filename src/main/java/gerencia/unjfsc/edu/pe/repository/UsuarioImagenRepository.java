package gerencia.unjfsc.edu.pe.repository;

import gerencia.unjfsc.edu.pe.domain.UsuarioImagen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UsuarioImagenRepository extends JpaRepository<UsuarioImagen, Integer> {
    @Query(value = "INSERT INTO tb_usua_s3() VALUES(id_usua_s3=:idUsuaS3,id_usua=:idUsua,nomb_img=:nombImg,url_img=:urlImg,id_img=:idImg)", nativeQuery = true)
    UsuarioImagen guardar(Integer idUsuaS3, Integer idUsua, String nombImg, String urlImg, String idImg);

    @Query(value = "SELECT * FROM tb_usua_s3 s WHERE s.id_usua=:idUsua", nativeQuery = true)
    UsuarioImagen findByPorUsuario(Integer idUsua);

    @Query(value = "DELETE FROM tb_usua_s3 s WHERE s.id_usua=:idUsua", nativeQuery = true)
    void deleteByUsuarioId(Integer idUsua);
}
