package dbManagement;




public class User {
    private Integer user_id;
    private String user_name;
    private int edad;
    public enum SEXO{HOMBRE, MUJER, NA}
    private SEXO sexo;

    public User() {
        super();
    }

    public User(String user_name, int edad, String sexo) {
        this.user_name = user_name;
        this.edad = edad;
        this.sexo=SEXO.valueOf(sexo);

    }
    public User(int user_id, String user_name, int edad, String sexo) {
        this.user_id=user_id;
        this.user_name = user_name;
        this.edad = edad;
        this.sexo=SEXO.valueOf(sexo);

    }
    public String getUserName() {
        return user_name;
    }

    public void setUserName(String user_name) {
        this.user_name = user_name;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public Integer getUserId() {
        return user_id;
    }

    public void setUserId(Integer user_id) {
        this.user_id = user_id;
    }

    public SEXO getSexo(){ return sexo;}

    public void setSexo(String sexo){ this.sexo = SEXO.valueOf(sexo); }
}
