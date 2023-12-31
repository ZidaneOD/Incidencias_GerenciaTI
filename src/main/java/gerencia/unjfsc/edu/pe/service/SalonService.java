package gerencia.unjfsc.edu.pe.service;

import gerencia.unjfsc.edu.pe.domain.Salon;

import java.util.List;

public interface SalonService {
    Salon crearSalon(Salon salon);

    Salon obtenerSalonPorId(Integer idSalon);

    Salon obtenerSalonPorNombre(String nombSalon);

    List<Salon> obtenerTodosLosSalones();

    Salon actualizarSalon(Salon salon);

    void eliminarSalon(Integer idSalon);

    List<Salon> obtenerSalonPorArea(Integer idArea);
}
