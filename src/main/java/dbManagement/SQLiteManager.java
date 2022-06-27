package dbManagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLiteManager {
    private Connection sqlite_connection;
    private SQLiteMethods methods;

    public SQLiteManager() {

    }


    public SQLiteMethods getMethods() {
        return methods;
    }


    public void setMethods(SQLiteMethods methods) {
        this.methods = methods;
    }


    public Connection getSqlite_connection() {
        return sqlite_connection;
    }




    public boolean Connect() {
        // TODO Auto-generated method stub
        try {
            Class.forName("org.sqlite.JDBC");
            this.sqlite_connection = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\pmsie\\git\\DynaRange2\\target\\classes\\dbManagement\\database.db");
            sqlite_connection.createStatement().execute("PRAGMA foreign_keys=ON");
            this.methods = new SQLiteMethods(sqlite_connection);
            return true;

        } catch (ClassNotFoundException | SQLException connection_error) {
            connection_error.printStackTrace();
            return false;
        }
    }


    public boolean CreateTables() {
        try {

            Statement stmt = this.sqlite_connection.createStatement();
            String sql = " CREATE TABLE User " + "(user_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + " user_name TEXT NOT NULL UNIQUE, " + " sexo TEXT CHECK( sexo IN ('HOMBRE','MUJER','NA') )," + " edad INTEGER)";
            stmt.execute(sql);
            stmt.close();

            Statement stmt0 = sqlite_connection.createStatement();
            String sql0 = "CREATE TABLE Caso " + "(caso_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + " nombre TEXT NOT NULL, " + "nodo_id  FOREING KEY REFERENCES Nodo(nodo_id) , " + " notas TEXT, " + " user_id FOREING KEY REFERENCES User(user_id))";
            stmt0.execute(sql0);
            stmt0.close();

            Statement stmt1 = sqlite_connection.createStatement();
            String sql1 = "CREATE TABLE Nodo " + "(nodo_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + " nombre TEXT NOT NULL)";
            stmt1.execute(sql1);
            stmt1.close();

            Statement stmt2 = sqlite_connection.createStatement();
            String sql2 = "CREATE TABLE Sesion " + "(sesion_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "path_a_folder TEXT NOT NULL," +" fecha TEXT NOT NULL, "  +" caso_id FOREING KEY REFERENCES Caso(caso_id) ON DELETE CASCADE)";

            stmt2.execute(sql2);
            stmt2.close();

            return true;
        }catch (SQLException tables_error) {
            if (tables_error.getMessage().contains("ya existe")) {
                System.out.println("La base de datos ya existe.");
                return false;
            } else {
                System.out.println("Error al crear las tablas! Abortando.");
                tables_error.printStackTrace();
                return false;
            }
        }
    }




    // -------> CLOSE DATABASE CONNECTION <---------

    public boolean Close_connection() {
        // TODO Auto-generated method stub
        try {
            this.sqlite_connection.close();
            return true;
        } catch (SQLException close_connection_error) {
            close_connection_error.printStackTrace();
            return false;
        }
    }

}
