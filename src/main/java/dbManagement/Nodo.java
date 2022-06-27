package dbManagement;

public class Nodo {

    private String nombre;
    private Integer nodo_id;

    public Nodo(){

    }
    public Nodo(int nodo_id, String nombre){
        this.nodo_id = nodo_id;
        this.nombre = nombre;
    }
    public Nodo(String nombre){
        this.nombre = nombre;
    }

    public String getNombre(){
        return this.nombre;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public Integer getNodo_id() {
        return nodo_id;
    }

    public void setNodo_id(Integer nodo_id) {
        this.nodo_id = nodo_id;
    }
}
