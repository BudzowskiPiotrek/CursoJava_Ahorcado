package CursoJava_Ahorcado.a;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Conector {
    private static final String URL = "jdbc:mysql://localhost:3306/ahorcado";
    private static final String USUARIO = "root";
    private static final String CONTRASENA = "";

    private static Connection conn = null;

    public Conector() {
    }

    public void conectar() {
        if (conn == null) {
            try {
                conn = DriverManager.getConnection(URL, USUARIO, CONTRASENA);
                System.out.println("Conexión a ahorcado establecida.");
            } catch (SQLException e) {
                System.out.println("Error al conectar a la base de datos");
            }
        }
    }

    public void desconectar() {
        conn = null;
    }

    public int IngresarDatos(String sql) {
        int resultado = -1;
        conectar();
        if (conn != null) {
            try (Statement aux = conn.createStatement()) {
                resultado = aux.executeUpdate(sql);
            } catch (SQLException e) {
                System.out.println("Error");
            } finally {
                desconectar();
            }
        } else {
            System.out.println("No se pudo establecer la conexión a la base de datos.");
        }
        return resultado;
    }

    public void mostrarDatos() {
        conectar();
        if (conn != null) {
            String sql = "";
            try (Statement aux = conn.createStatement();
                    ResultSet res = aux.executeQuery(sql)) {
                while (res.next()) {
                    // guardamos datos el variables en cada vuelta
                }
            } catch (SQLException e) {
                System.out.println("Error");
            } finally {
                desconectar();
            }
        } else {
            System.out.println("La conexión está cerrada o no disponible.");
        }
    }
}
