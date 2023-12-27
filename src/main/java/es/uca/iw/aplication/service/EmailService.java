package es.uca.iw.aplication.service;

import es.uca.iw.aplication.tables.usuarios.Usuario;

public interface EmailService { boolean sendRegistartionEmail(Usuario usuario, String code); }