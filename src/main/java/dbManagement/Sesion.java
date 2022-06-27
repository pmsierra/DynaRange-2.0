package dbManagement;

public class Sesion {
    private Integer sesion_id;

    private String path;
    private Caso caso;

    private String fecha;

    public Sesion(){

    }
    public Sesion(String fecha, String path, Caso caso){
        this.fecha=fecha;
        this.path=path;
        this.caso=caso;


    }
    public Sesion(int sesion_id, String path, Caso caso,  String fecha){
        this.sesion_id=sesion_id;
        this.fecha=fecha;
        this.path=path;
        this.caso=caso;


    }


    public Caso getCaso() {
        return caso;
    }

    public Integer getSesion_id() {
        return sesion_id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setSesion_id(Integer sesion_id) {
        this.sesion_id = sesion_id;
    }

    public void setCaso(Caso caso) {
        this.caso = caso;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getFecha() {
        return fecha;
    }
}
