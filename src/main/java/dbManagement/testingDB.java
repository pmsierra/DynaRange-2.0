package dbManagement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class testingDB {
    public static void main(String[] args) throws SQLException {

        SQLiteManager manager = new SQLiteManager();
        boolean everything_ok = manager.Connect();
        boolean tables_ok = manager.CreateTables();
        System.out.println(manager.getSqlite_connection().getWarnings());

        System.out.println(everything_ok + " ");

        SQLiteMethods methods= new SQLiteMethods(manager.getSqlite_connection());

        // -----Creation----------------------
        // Users
        User Pablo = new User("Pablo", 22, "HOMBRE");
        User Alvaro = new User("Alvaro", 22, "HOMBRE");
        User Nuria = new User("Nuria", 22, "MUJER");
        User German = new User("German", 22, "HOMBRE");

        // Nodos
        Nodo munecaDch = new Nodo("Muñeca - Derecha");
        Nodo codoDch = new Nodo("Codo - Derecha");
        Nodo hombroDch = new Nodo("Hombro - Derecha");
        Nodo cervical = new Nodo("Cervical");
        Nodo munecaIzq = new Nodo("Muñeca - Izquierda");
        Nodo codoIzq = new Nodo("Codo - Izquierda");
        Nodo hombroIzq = new Nodo("Hombro - Izquierda");
        Nodo caderaDch = new Nodo("Cadera - Derecha");
        Nodo rodillaDch = new Nodo("Rodilla - Derecha");
        Nodo tobilloDch = new Nodo("Tobillo - Derecha");
        Nodo lumbar = new Nodo("Lumbar");
        Nodo caderaIzq = new Nodo("Cadera - Izquierda");
        Nodo rodillaIzq = new Nodo("Rodilla - Izquierda");
        Nodo tobilloIzq = new Nodo("Tobillo - Izquierda");


        //Casos
        Caso PabloCodo = new Caso("PabloCodo", codoDch, Pablo,"");
        Caso PabloHombro = new Caso("PabloHombro", hombroDch, Pablo,"");
        Caso NuriaCodo = new Caso("NuriaCodo", codoDch, Nuria,"");

        //Sesiones
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String fecha = now.toString();
        String path = "C:\\Users\\pmsie\\Downloads\\openpose-master3d\\openpose-master\\jsons20";
        String path2 = "C:\\Users\\pmsie\\Downloads\\openpose-master3d\\openpose-master\\jsons19";
        Sesion sesion1 = new Sesion(fecha, path, PabloCodo);
        Sesion sesion2 = new Sesion(fecha, path2, PabloCodo);

        methods.Insertar_nuevo_usuario(Pablo);
        methods.Insertar_nuevo_usuario(Alvaro);
        methods.Insertar_nuevo_usuario(Nuria);
        methods.Insertar_nuevo_usuario(German);

        methods.Insertar_nuevo_nodo(munecaDch);
        methods.Insertar_nuevo_nodo(codoDch);
        methods.Insertar_nuevo_nodo(hombroDch);
        methods.Insertar_nuevo_nodo(cervical);
        methods.Insertar_nuevo_nodo(munecaIzq);
        methods.Insertar_nuevo_nodo(codoIzq);
        methods.Insertar_nuevo_nodo(hombroIzq);
        methods.Insertar_nuevo_nodo(caderaDch);
        methods.Insertar_nuevo_nodo(rodillaDch);
        methods.Insertar_nuevo_nodo(tobilloDch);
        methods.Insertar_nuevo_nodo(lumbar);
        methods.Insertar_nuevo_nodo(caderaIzq);
        methods.Insertar_nuevo_nodo(rodillaIzq);
        methods.Insertar_nuevo_nodo(tobilloIzq);

        methods.Insertar_nuevo_caso(PabloCodo);
        methods.Insertar_nuevo_caso(PabloHombro);
        methods.Insertar_nuevo_caso(NuriaCodo);

        methods.Insertar_nueva_sesion(sesion1);
        methods.Insertar_nueva_sesion(sesion2);

    }
    }
