package com.tt1.test;

import java.util.List;

/**
 * Capa de acceso a datos que interactúa con la base de datos simulada (DBStub).
 * Proporciona métodos para gestionar las tareas y los emails.
 */

public class Repositorio implements IRepositorio{
	
	private IDBStub db;

    public Repositorio(DBStub db) {
        this.db = db;
    }

    @Override
    public ToDo encontrarToDo(Integer id) {
        return db.findToDo(id);
    }
    
    /**
     * Marca una tarea específica como completada en la base de datos.
     * * @param id El identificador de la tarea que se desea completar.
     */
    
    @Override
    public void marcarComoCompletado(Integer id) {
        ToDo todo = db.findToDo(id);
        if (todo != null) {
            todo.setCompletado(true);
            db.updateToDo(todo); // Actualizamos en la BD
        }
    }

    @Override
    public void guardarToDo(ToDo todo) {
        db.insertToDo(todo);
    }

    @Override
    public void guardarEmail(String email) {
        db.insertEmail(email);
    }

    @Override
    public List<ToDo> obtenerTodosLosToDos() {
        return db.findAllToDos();
    }

    @Override
    public List<String> obtenerTodosLosEmails() {
        return db.findAllEmails();
    }
}
