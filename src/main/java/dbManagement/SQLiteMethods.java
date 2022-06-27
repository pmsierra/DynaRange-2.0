package dbManagement;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class SQLiteMethods {

    private Connection sqlite_connection;


    // -----> INSERT METHODS <-----

    public SQLiteMethods(Connection sqlite_connection) {
        this.sqlite_connection = sqlite_connection;
    }
    public void Insertar_nuevo_usuario(User user) {
        try {
            String table = "INSERT INTO User (user_name, sexo, edad) " + " VALUES(?,?,?);";
            PreparedStatement template = this.sqlite_connection.prepareStatement(table);
            template.setString(1, user.getUserName());
            template.setString(2, user.getSexo().toString());
            template.setInt(3, user.getEdad());
            template.executeUpdate();
            user.setUserId(Search_user_by_name(user.getUserName()).getUserId());


        } catch (SQLException insert_user_error) {
            insert_user_error.printStackTrace();
        }
    }

    public void Insertar_nuevo_nodo(Nodo nodo) {
        try {
            String table = "INSERT INTO Nodo (nombre) " + " VALUES(?);";
            PreparedStatement template = this.sqlite_connection.prepareStatement(table);
            template.setString(1, nodo.getNombre());
            template.executeUpdate();
            nodo.setNodo_id(Search_nodo_by_name(nodo.getNombre()).getNodo_id());

        } catch(SQLException insert_nodo_error) {
            insert_nodo_error.printStackTrace();
        }
    }


    public void Insertar_nuevo_caso(Caso caso) {
        try {
            String table = "INSERT INTO Caso (nombre, nodo_id, user_id, notas) " + " VALUES(?,?,?,?);";
            PreparedStatement template = this.sqlite_connection.prepareStatement(table);
            String name = caso.getNombre();
            template.setString(1, name);
            template.setInt(2, caso.getNodo().getNodo_id());
            template.setInt(3, caso.getUser().getUserId());
            template.setString(4, caso.getNotas());
            template.executeUpdate();

            caso.setCaso_id(Search_caso_by_name(caso.getNombre()).getCaso_id());
        } catch (SQLException insert_caso_error) {
            insert_caso_error.printStackTrace();
        }
    }


    public void Insertar_nueva_sesion(Sesion sesion) {
        try {
            String table = "INSERT INTO Sesion (path_a_folder, caso_id, fecha) " + "VALUES (?,?,?);";
            PreparedStatement template = this.sqlite_connection.prepareStatement(table);
            template.setString(1, sesion.getPath());
            template.setInt(2, sesion.getCaso().getCaso_id());
            template.setString(3, sesion.getFecha());
            template.executeUpdate();
            sesion.setSesion_id(Search_sesion_by_fecha(sesion.getFecha()).getSesion_id());

        } catch(SQLException insert_sesion_error) {
            insert_sesion_error.printStackTrace();
        }
    }


    // -----> UPDATES METHODS <-----

    public boolean Update_user(User user) {
        try {
            String SQL_code = "UPDATE User SET user_name = ?, sexo = ?, edad = ? WHERE user_id = ?";
            PreparedStatement template = this.sqlite_connection.prepareStatement(SQL_code);
            template.setString(1, user.getUserName());
            template.setString(2, user.getSexo().toString());
            template.setInt(3, user.getEdad());
            template.setInt(4, user.getUserId());
            template.executeUpdate();
            template.close();
            return true;
        } catch (SQLException update_user_error) {
            update_user_error.printStackTrace();
            return false;
        }
    }

    public boolean Update_caso(Caso caso) {
        try {
            String SQL_code = "UPDATE Caso SET nombre = ?, nodo_id = ?, user_id = ?, notas = ?, WHERE caso_id = ?";
            PreparedStatement template = this.sqlite_connection.prepareStatement(SQL_code);
            template.setString(1, caso.getNombre());
            template.setInt(2, caso.getNodo().getNodo_id());
            template.setInt(3, caso.getUser().getUserId());
            template.setInt(4, caso.getCaso_id());
            template.setString(5, caso.getNotas());

            template.executeUpdate();
            template.close();
            return true;
        } catch (SQLException update_caso_error) {
            update_caso_error.printStackTrace();
            return false;
        }
    }
    public boolean Update_nodo(Nodo nodo) {
        try {
            String SQL_code = "UPDATE Nodo SET nombre = ? WHERE nodo_id = ?";
            PreparedStatement template = this.sqlite_connection.prepareStatement(SQL_code);
            template.setString(1, nodo.getNombre());
            template.setInt(2, nodo.getNodo_id());

            template.executeUpdate();
            template.close();

            return true;
        } catch (SQLException update_nodo_error) {
            update_nodo_error.printStackTrace();
            return false;
        }
    }

    public boolean Update_sesion(Sesion sesion) {
        try {
            String SQL_code = "UPDATE Sesion SET path_a_folder = ?, caso_id=?, fecha=? WHERE sesion_id  = ?";
            PreparedStatement template = this.sqlite_connection.prepareStatement(SQL_code);
            template.setString(1, sesion.getPath());
            template.setInt(2, sesion.getCaso().getCaso_id());
            template.setInt(3, sesion.getSesion_id());
            template.setString(4, sesion.getFecha());
            template.executeUpdate();
            template.close();
            return true;
        } catch (SQLException update_sesion_error) {
            update_sesion_error.printStackTrace();
            return false;
        }
    }


    // -----> DELETE METHODS <-----
    public boolean Delete_user(User user) {
        try {
            String SQL_code = "DELETE FROM User WHERE user_name = ?;";
            PreparedStatement template = this.sqlite_connection.prepareStatement(SQL_code);
            template.setString(1, user.getUserName());
            template.executeUpdate();
            template.close();
            return true;
        } catch (SQLException delete_user_error) {
            delete_user_error.printStackTrace();
            return false;
        }
    }

    public boolean Delete_caso(Caso caso) {
        try {
            String SQL_code = "DELETE FROM Caso WHERE nombre = ?;";
            PreparedStatement template = this.sqlite_connection.prepareStatement(SQL_code);
            template.setString(1, caso.getNombre());
            template.executeUpdate();
            template.close();
            return true;
        } catch (SQLException delete_caso_error) {
            delete_caso_error.printStackTrace();
            return false;
        }
    }

    public boolean Delete_nodo(Nodo nodo) {
        try {
            String SQL_code = "DELETE FROM Nodo WHERE nombre= ?;";
            PreparedStatement template = this.sqlite_connection.prepareStatement(SQL_code);
            template.setString(1, nodo.getNombre());
            template.executeUpdate();
            template.close();
            return true;
        } catch (SQLException delete_nodo_error) {
            delete_nodo_error.printStackTrace();
            return false;
        }
    }
    public boolean Delete_sesion(Sesion sesion) {
        try {
            String SQL_code = "DELETE FROM Sesion WHERE sesion_id= ?;";
            PreparedStatement template = this.sqlite_connection.prepareStatement(SQL_code);
            template.setInt(1, sesion.getSesion_id());
            template.executeUpdate();
            template.close();
            return true;
        } catch (SQLException delete_sesion_error) {
            delete_sesion_error.printStackTrace();
            return false;
        }
    }


    // -----> SEARCH METHODS <-----

    public User Search_user_by_name (String user_name ) {
        try {
            String SQL_code = "SELECT * FROM User WHERE user_name LIKE ?";
            PreparedStatement template = this.sqlite_connection.prepareStatement(SQL_code);
            template.setString(1, user_name);
            User user= new User();
            ResultSet result_set = template.executeQuery();
            result_set.next();
            user.setUserId(result_set.getInt("user_id"));
            user.setUserName(user_name);
            user.setSexo(result_set.getString("sexo"));
            user.setEdad(result_set.getInt("edad"));
            template.close();
            return user;
        } catch (SQLException search_user_by_nombre_error) {
            search_user_by_nombre_error.printStackTrace();
            return null;
        }
    }

    public User Search_user_by_id (Integer user_id ) {
        try {
            String SQL_code = "SELECT * FROM User WHERE user_id LIKE ?";
            PreparedStatement template = this.sqlite_connection.prepareStatement(SQL_code);
            template.setInt(1, user_id);
            User user= new User();
            ResultSet result_set = template.executeQuery();
            result_set.next();
            user.setUserId(user_id);
            user.setUserName(result_set.getString("user_name"));
            user.setSexo(result_set.getString("sexo"));
            user.setEdad(result_set.getInt("edad"));
            template.close();
            return user;
        } catch (SQLException search_user_by_id_error) {
            search_user_by_id_error.printStackTrace();
            return null;
        }
    }
    public Nodo Search_nodo_by_id (Integer nodo_id ) {
        try {
            String SQL_code = "SELECT * FROM Nodo WHERE nodo_id LIKE ?";
            PreparedStatement template = this.sqlite_connection.prepareStatement(SQL_code);
            template.setInt(1, nodo_id);
            Nodo nodo = new Nodo();
            ResultSet result_set = template.executeQuery();
            result_set.next();
            nodo.setNodo_id(nodo_id);
            nodo.setNombre(result_set.getString("nombre"));
            template.close();
            return nodo;
        } catch (SQLException search_nodo_by_id_error) {
            search_nodo_by_id_error.printStackTrace();
            return null;
        }
    }
    public Nodo Search_nodo_by_name (String nombre_nodo ) {
        try {
            String SQL_code = "SELECT * FROM Nodo WHERE nombre LIKE ?";
            PreparedStatement template = this.sqlite_connection.prepareStatement(SQL_code);
            template.setString(1, nombre_nodo);
            Nodo nodo = new Nodo();
            ResultSet result_set = template.executeQuery();
            result_set.next();
            nodo.setNodo_id(result_set.getInt("nodo_id"));
            nodo.setNombre(nombre_nodo);
            template.close();
            return nodo;
        } catch (SQLException search_nodo_by_nombre_error) {
            search_nodo_by_nombre_error.printStackTrace();
            return null;
        }
    }

    public Caso Search_caso_by_name (String nombreCaso ) {
        try {
            String SQL_code = "SELECT * FROM Caso WHERE nombre LIKE ?";
            PreparedStatement template = this.sqlite_connection.prepareStatement(SQL_code);
            template.setString(1, nombreCaso);
            Caso caso = new Caso();
            ResultSet result_set = template.executeQuery();
            result_set.next();
            caso.setCaso_id(result_set.getInt("caso_id"));
            caso.setNombre(nombreCaso);
            caso.setNodo(Search_nodo_by_id(result_set.getInt("nodo_id")));
            caso.setUser(Search_user_by_id(result_set.getInt("user_id")));
            caso.setNotas(result_set.getString("notas"));

            template.close();
            return caso;
        } catch (SQLException search_caso_by_nombre_error) {
            search_caso_by_nombre_error.printStackTrace();
            return null;
        }
    }
    public Caso Search_caso_by_id(Integer caso_id ) {
        try {
            String SQL_code = "SELECT * FROM Caso WHERE caso_id LIKE ?";
            PreparedStatement template = this.sqlite_connection.prepareStatement(SQL_code);
            template.setInt(1, caso_id);
            Caso caso = new Caso();
            ResultSet result_set = template.executeQuery();
            result_set.next();
            caso.setCaso_id(caso_id);
            caso.setNombre(result_set.getString("nombre"));
            caso.setNodo(Search_nodo_by_id(result_set.getInt("nodo_id")));
            caso.setUser(Search_user_by_id(result_set.getInt("user_id")));
            caso.setNotas(result_set.getString("notas"));

            template.close();
            return caso;
        } catch (SQLException search_caso_by_id_error) {
            search_caso_by_id_error.printStackTrace();
            return null;
        }
    }
    public Sesion Search_sesion_by_fecha (String fechaSesion) {
        try {
            String SQL_code = "SELECT * FROM Sesion WHERE fecha LIKE ?";
            PreparedStatement template = this.sqlite_connection.prepareStatement(SQL_code);
            template.setString(1,fechaSesion);
            Sesion sesion = new Sesion();
            ResultSet result_set = template.executeQuery();
            result_set.next();
            sesion.setSesion_id(result_set.getInt("sesion_id"));
            sesion.setPath(result_set.getString("path_a_folder"));
            sesion.setCaso(Search_caso_by_id(result_set.getInt("caso_id")));
            sesion.setFecha(fechaSesion);

            template.close();
            return sesion;
        } catch (SQLException search_sesion_by_name_error) {
            search_sesion_by_name_error.printStackTrace();
            return null;
        }
    }
    public Sesion Search_sesion_by_id (Integer sesion_id) {
        try {
            String SQL_code = "SELECT * FROM Sesion WHERE sesion_id LIKE ?";
            PreparedStatement template = this.sqlite_connection.prepareStatement(SQL_code);
            template.setInt(1,sesion_id);
            Sesion sesion = new Sesion();
            ResultSet result_set = template.executeQuery();
            result_set.next();
            sesion.setSesion_id(sesion_id);
            sesion.setPath(result_set.getString("path_a_folder"));
            sesion.setCaso(Search_caso_by_id(result_set.getInt("caso_id")));
            sesion.setFecha(result_set.getString("fecha"));

            template.close();
            return sesion;
        } catch (SQLException search_sesion_by_id_error) {
            search_sesion_by_id_error.printStackTrace();
            return null;
        }
    }

    // -----> LIST METHODS <-----

    public List<User> List_all_users() {
        List<User> users = new LinkedList<User>();
        try {
            Statement statement = this.sqlite_connection.createStatement();
            String SQL_code = "SELECT * FROM User";
            ResultSet rs = statement.executeQuery(SQL_code);
            while(rs.next()) {
                Integer user_id = rs.getInt("user_id");
                String user_name = rs.getString("user_name");
                String sexo= rs.getString("sexo");
                Integer edad= rs.getInt("edad");

                users.add(new User(user_id, user_name, edad, sexo));
            }
            return users;
        } catch (SQLException list_users_error) {
            list_users_error.printStackTrace();
            return null;
        }

    }

    public List<Caso> List_all_casos() {
        List<Caso> casos = new LinkedList<Caso>();
        try {
            Statement statement = this.sqlite_connection.createStatement();
            String SQL_code = "SELECT * FROM Caso";
            ResultSet rs = statement.executeQuery(SQL_code);
            while(rs.next()) {
                Integer caso_id = rs.getInt("caso_id");
                String nombreCaso= rs.getString("nombre");
                Nodo nodo = Search_nodo_by_id(rs.getInt("nodo_id"));
                User user = Search_user_by_id(rs.getInt("user_id"));
                String notas = rs.getString("notas");
                casos.add(new Caso(caso_id,nombreCaso,nodo,user,notas));
            }
            return casos;
        } catch (SQLException list_casos_error) {
            list_casos_error.printStackTrace();
            return null;
        }

    }
    public List<Caso> List_all_casos_by_user(Integer user_id) {
        List<Caso> casosUser = new LinkedList<Caso>();
        try {
            String SQL_code = "SELECT * FROM Caso WHERE user_id LIKE ?";
            PreparedStatement template = this.sqlite_connection.prepareStatement(SQL_code);
            template.setInt(1,user_id);
            ResultSet rs = template.executeQuery();
            while(rs.next()) {
                Integer caso_id = rs.getInt("caso_id");
                String nombreCaso= rs.getString("nombre");
                Integer nodo_id = rs.getInt("nodo_id");
                String notas = rs.getString("notas");
                Nodo nodo = Search_nodo_by_id(nodo_id);
                User user = Search_user_by_id(user_id);
                casosUser.add(new Caso(caso_id,nombreCaso,nodo,user,notas));
            }
            return casosUser;
        } catch (SQLException list_casos_error) {
            list_casos_error.printStackTrace();
            return null;
        }

    }

    public List<Nodo> List_all_nodos() {
        List<Nodo> nodos = new LinkedList<Nodo>();
        try {
            Statement statement = this.sqlite_connection.createStatement();
            String SQL_code = "SELECT * FROM Nodo";
            ResultSet rs = statement.executeQuery(SQL_code);
            while(rs.next()) {
                Integer nodo_id = rs.getInt("nodo_id");
                String nombreNodo= rs.getString("nombre");
                nodos.add(new Nodo(nodo_id,nombreNodo));
            }
            return nodos;
        } catch (SQLException list_nodos_error) {
            list_nodos_error.printStackTrace();
            return null;
        }

    }

    public List<Sesion> List_all_sesion() {
        List<Sesion> sesiones = new LinkedList<Sesion>();
        try {
            Statement statement = this.sqlite_connection.createStatement();
            String SQL_code = "SELECT * FROM Sesion";
            ResultSet rs = statement.executeQuery(SQL_code);
            while(rs.next()) {
                Integer sesion_id = rs.getInt("sesion_id");
                String path= rs.getString("path_a_folder");
                Caso caso = Search_caso_by_id(rs.getInt("caso_id"));
                String fecha= rs.getString("fecha");
                sesiones.add(new Sesion(sesion_id,path,caso,fecha));
            }
            return sesiones;
        } catch (SQLException list_sesion_error) {
            list_sesion_error.printStackTrace();
            return null;
        }

    }

    public List<Sesion> List_sesion_by_caso (Integer caso_id) {
        List<Sesion> sesionesCaso = new LinkedList<Sesion>();
        try {
            String SQL_code = "SELECT * FROM Sesion WHERE caso_id LIKE ?";
            PreparedStatement template = this.sqlite_connection.prepareStatement(SQL_code);
            template.setInt(1,caso_id);
            Sesion sesion = new Sesion();
            ResultSet rs = template.executeQuery();
            while(rs.next()) {
                Integer sesion_id = rs.getInt("sesion_id");
                String path= rs.getString("path_a_folder");
                String fecha= rs.getString("fecha");
                Caso caso = Search_caso_by_id(rs.getInt("caso_id"));
                sesionesCaso.add(new Sesion(sesion_id,path,caso,fecha));
            }
            return sesionesCaso;


        } catch (SQLException list_sesionCaso_error) {
            list_sesionCaso_error.printStackTrace();
            return null;
        }
    }






}