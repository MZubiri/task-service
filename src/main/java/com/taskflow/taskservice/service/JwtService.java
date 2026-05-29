package com.taskflow.taskservice.service;

public interface JwtService {

    String obtenerUsername(String token);

    boolean validarToken(String token);
}