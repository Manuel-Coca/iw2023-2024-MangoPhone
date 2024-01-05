package es.uca.iw.aplication.service;

import es.uca.iw.aplication.repository.MensajeRepository;

import org.springframework.stereotype.Service;


@Service
public class MensajeService {
    private final MensajeRepository mensajeRepository;

    public MensajeService(MensajeRepository mensajeRepository) { this.mensajeRepository = mensajeRepository; }
}
