package es.uca.iw.aplication.tables.enumerados;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public enum Servicio { MOVIL, FIJO, FIBRA }