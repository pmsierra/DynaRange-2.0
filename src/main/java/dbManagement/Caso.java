package dbManagement;

import java.util.LinkedList;

public class Caso {
    private String nombre;
    private Nodo nodo;
    private User user;
    private Integer caso_id;
    private String notas;

    private LinkedList<Sesion> sesionList;

    public Caso(){

    }
    public Caso (String nombre, Nodo nodo, User user, String notas){
        this.nombre=nombre;
        this.nodo=nodo;
        this.user=user;
        this.notas=notas;
    }
    public Caso (int caso_id, String nombre, Nodo nodo, User user, String notas){
        this.caso_id=caso_id;
        this.nombre=nombre;
        this.nodo=nodo;
        this.user=user;
        this.notas=notas;
    }
    public Nodo getNodo() {
        return nodo;
    }

    public User getUser() {
        return user;
    }


    public String getNombre() {
        return nombre;
    }


    public void setNodo(Nodo nodo) {
        this.nodo = nodo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getCaso_id() {
        return caso_id;
    }

    public void setCaso_id(Integer caso_id) {
        this.caso_id = caso_id;
    }

    public LinkedList<Sesion> getSesionList() {
        return sesionList;
    }

    public void setSesionList(LinkedList<Sesion> sesionList) {
        this.sesionList = sesionList;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }
}

