package gerencia.unjfsc.edu.pe.service;

import gerencia.unjfsc.edu.pe.domain.Salon;
import gerencia.unjfsc.edu.pe.repository.SalonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SalonServiceImp implements SalonService {
    @Autowired
    private SalonRepository salonRepository;

    @Override
    @Transactional
    public Salon crearSalon(Salon salon) {
        return salonRepository.save(salon);
    }

    @Override
    @Transactional(readOnly = true)
    public Salon obtenerSalonPorId(Integer idSalon) {
        return salonRepository.findById(idSalon).orElse(null);
    }

    @Override
    public Salon obtenerSalonPorNombre(String nombSalon) {
        return salonRepository.findByNombSalon(nombSalon);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Salon> obtenerTodosLosSalones() {
        return salonRepository.findAll();
    }

    @Override
    @Transactional
    public Salon actualizarSalon(Salon salon) {
        return salonRepository.save(salon);
    }

    @Override
    @Transactional
    public void eliminarSalon(Integer idSalon) {
        salonRepository.deleteById(idSalon);
    }
}
