package com.tt1.test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Contiene la lógica de negocio principal del sistema de tareas.
 * Se encarga de coordinar la creación de tareas, el registro de emails y 
 * la validación del estado de las tareas para enviar alertas si están caducadas.
 */

public class Servicio {
    private IRepositorio repositorio;
    private IMailer mailer;

    public Servicio(IRepositorio repositorio, IMailer mailer) {
        this.repositorio = repositorio;
        this.mailer = mailer;
    }
    
    /**
     * Crea una nueva tarea pendiente y la guarda a través del repositorio.
     * Antes de crearla, comprueba si existen tareas caducadas para enviar alertas.
     * @param nombre El nombre de la tarea.
     * @param descripcion Los detalles sobre lo que hay que hacer.
     * @param fechaLimite La fecha máxima para finalizar la tarea.
     */
    
    public void crearToDo(String nombre, String descripcion, LocalDate fechaLimite) {
        comprobarYEnviarAlertas();
        
        ToDo nuevo = new ToDo();
        nuevo.setNombre(nombre);
        nuevo.setDescripcion(descripcion);
        nuevo.setFechaLimite(fechaLimite);
        nuevo.setCompletado(false);
        
        repositorio.guardarToDo(nuevo);
    }

    public void agregarEmailAgenda(String email) {
        comprobarYEnviarAlertas();
        repositorio.guardarEmail(email);
    }

    public void marcarTareaFinalizada(Integer id) {
        comprobarYEnviarAlertas();
        repositorio.marcarComoCompletado(id);
    }
    
    /**
     * Consulta y devuelve una lista con todas las tareas que aún no están completadas.
     * Antes de la consulta, verifica si hay tareas caducadas para enviar alertas.
     * * @return Una lista de objetos ToDo cuyo estado es no completado.
     */
    
    public List<ToDo> consultarPendientes() {
        comprobarYEnviarAlertas();
        
        List<ToDo> todos = repositorio.obtenerTodosLosToDos();
        List<ToDo> pendientes = new ArrayList<>();
        
        for (ToDo t : todos) {
            if (!t.isCompletado()) {
                pendientes.add(t);
            }
        }
        return pendientes;
    }

    private void comprobarYEnviarAlertas() {
        List<ToDo> todos = repositorio.obtenerTodosLosToDos();
        boolean hayCaducadas = false;
        
        for (ToDo t : todos) {
            // Si no está completada y su fecha límite es ANTERIOR a hoy
            if (!t.isCompletado() && t.getFechaLimite() != null && t.getFechaLimite().isBefore(LocalDate.now())) {
                hayCaducadas = true;
                break;
            }
        }

        if (hayCaducadas) {
            List<String> emails = repositorio.obtenerTodosLosEmails();
            for (String email : emails) {
                mailer.enviarCorreo(email, "¡Alerta! Tienes tareas pendientes cuya fecha límite ha expirado.");
            }
        }
    }
}
