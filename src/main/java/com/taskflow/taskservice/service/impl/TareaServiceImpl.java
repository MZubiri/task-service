package com.taskflow.taskservice.service.impl;

import com.taskflow.taskservice.dto.TareaRequest;
import com.taskflow.taskservice.dto.TareaResponse;
import com.taskflow.taskservice.entity.Tarea;
import com.taskflow.taskservice.repository.TareaRepository;
import com.taskflow.taskservice.service.TareaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TareaServiceImpl implements TareaService {

    private final TareaRepository tareaRepository;

    @Override
    public TareaResponse crear(TareaRequest request) {
        Tarea tarea = Tarea.builder()
                .titulo(request.getTitulo())
                .descripcion(request.getDescripcion())
                .estado(request.getEstado())
                .fechaCreacion(LocalDateTime.now())
                .fechaActualizacion(LocalDateTime.now())
                .build();

        return convertirAResponse(tareaRepository.save(tarea));
    }

    @Override
    public List<TareaResponse> listar() {
        return tareaRepository.findAll()
                .stream()
                .map(this::convertirAResponse)
                .toList();
    }

    @Override
    public TareaResponse buscarPorId(Long id) {
        Tarea tarea = tareaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada"));

        return convertirAResponse(tarea);
    }

    @Override
    public TareaResponse actualizar(Long id, TareaRequest request) {
        Tarea tarea = tareaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada"));

        tarea.setTitulo(request.getTitulo());
        tarea.setDescripcion(request.getDescripcion());
        tarea.setEstado(request.getEstado());
        tarea.setFechaActualizacion(LocalDateTime.now());

        return convertirAResponse(tareaRepository.save(tarea));
    }

    @Override
    public void eliminar(Long id) {
        Tarea tarea = tareaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada"));

        tareaRepository.delete(tarea);
    }

    private TareaResponse convertirAResponse(Tarea tarea) {
        return TareaResponse.builder()
                .id(tarea.getId())
                .titulo(tarea.getTitulo())
                .descripcion(tarea.getDescripcion())
                .estado(tarea.getEstado())
                .fechaCreacion(tarea.getFechaCreacion())
                .fechaActualizacion(tarea.getFechaActualizacion())
                .build();
    }
}