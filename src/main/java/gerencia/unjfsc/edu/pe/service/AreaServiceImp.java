package gerencia.unjfsc.edu.pe.service;

import gerencia.unjfsc.edu.pe.domain.Area;
import gerencia.unjfsc.edu.pe.repository.AreaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AreaServiceImp implements AreaService {
    @Autowired
    private AreaRepository areaRepository;

    @Override
    @Transactional
    public Area crearArea(Area area) {
        return areaRepository.save(area);
    }

    @Override
    @Transactional(readOnly = true)
    public Area obtenerPorId(Integer idArea) {
        return areaRepository.findById(idArea).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Area> obtenerTodasLasAreas() {
        return areaRepository.findAll();
    }

    @Override
    @Transactional
    public Area actualizarArea(Area area) {
        return areaRepository.save(area);
    }

    @Override
    @Transactional
    public void eliminarArea(Integer idArea) {
        areaRepository.deleteById(idArea);
    }
}
