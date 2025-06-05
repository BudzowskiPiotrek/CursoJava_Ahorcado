package CursoJava_Ahorcado;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    public int addPalabra(String palabra, String tematica, int dificultad) {
        String sql = "INSERT INTO palabras (palabra, tematica, dificultad) VALUES ('" + palabra + "', '" + tematica
                + "', " + dificultad + ")";
        int resultado = IngresarDatos(sql);
        if (resultado > 0) {
            System.out.println("Palabra '" + palabra + "' añadida con éxito.");
        } else {
            System.out.println("Error al añadir la palabra.");
        }
        return resultado;
    }

    public int registrar(String usuario, String contrasena) {
        String sql = "INSERT INTO usuario (usuario, contraseña) VALUES ('" + usuario + "', '" + contrasena + "')";
        int resultado = IngresarDatos(sql);
        if (resultado > 0) {
            System.out.println("Usuario " + usuario + " registrado con éxito.");
            return login(usuario, contrasena);
        }
        return -1;
    }

    public int login(String usuario, String contrasena) {
        int idUsuario = -1; // -1 indica que no se encontró el usuario
        conectar();
        if (conn != null) {
            String sql = "SELECT id, usuario, contraseña FROM usuario WHERE usuario = '" + usuario
                    + "' AND contraseña = '" + contrasena + "'";
            try (Statement aux = conn.createStatement();
                    ResultSet res = aux.executeQuery(sql)) {
                if (res.next()) {
                    idUsuario = res.getInt("id");
                }
            } catch (SQLException e) {
                System.out.println("Error durante el login: " + e.getMessage());
            } finally {
                desconectar();
            }
        } else {
            System.out.println("La conexión está cerrada o no disponible.");
        }
        return idUsuario;
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

    public Palabra getPalabraAleatoria(String tematica, int dificultad) {
        conectar();
        Palabra palabraObj = null;
        if (conn != null) {
            String sql = "SELECT palabra, tematica, dificultad FROM palabras WHERE tematica = '" + tematica
                    + "' AND dificultad = " + dificultad + " ORDER BY RAND() LIMIT 1";
            try (Statement aux = conn.createStatement();
                    ResultSet res = aux.executeQuery(sql)) {
                if (res.next()) {
                    String palabra = res.getString("palabra");
                    String tema = res.getString("tematica");
                    int dific = res.getInt("dificultad");
                    palabraObj = new Palabra(palabra, tema, dific);
                }
            } catch (SQLException e) {
                System.out.println("Error al obtener palabra aleatoria: " + e.getMessage());
            } finally {
                desconectar();
            }
        } else {
            System.out.println("La conexión está cerrada o no disponible.");
        }
        return palabraObj;
    }

    public int guardarHistorial(int idUsuario, String palabra, boolean completada, int intentos) {
        // Obtenemos la fecha y hora actual para registrarla
        String fechaActual = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        int completadaInt = completada ? 1 : 0; // Convertir boolean a int para la base de datos
        String sql = "INSERT INTO historial (id_usuario, palabra, completada, fecha, intentos) VALUES (" + idUsuario
                + ", '" + palabra + "', " + completadaInt + ", '" + fechaActual + "', " + intentos + ")";
        int resultado = IngresarDatos(sql);
        if (resultado > 0) {
            System.out.println("Historial de partida guardado con éxito.");
        } else {
            System.out.println("Error al guardar el historial de partida.");
        }
        return resultado;
    }

    public void mostrarHistorial(int idUsuario) {
        conectar();
        if (conn != null) {
            String sql = "SELECT palabra, completada, fecha, intentos FROM historial WHERE id_usuario = " + idUsuario
                    + " ORDER BY fecha DESC";
            try (Statement aux = conn.createStatement();
                    ResultSet res = aux.executeQuery(sql)) {
                System.out.println("\n--- Historial de Partidas ---");
                if (!res.isBeforeFirst()) {
                    System.out.println("No hay partidas registradas para este usuario.");
                } else {
                    while (res.next()) {
                        String palabra = res.getString("palabra");
                        boolean completada = res.getBoolean("completada");
                        String fecha = res.getString("fecha");
                        int intentos = res.getInt("intentos");

                        System.out.println("Palabra: " + palabra +
                                ", Resultado: " + (completada ? "Ganada" : "Perdida") +
                                ", Intentos: " + intentos +
                                ", Fecha: " + fecha);
                    }
                }
                System.out.println("-----------------------------\n");
            } catch (SQLException e) {
                System.out.println("Error al mostrar historial: " + e.getMessage());
            } finally {
                desconectar();
            }
        } else {
            System.out.println("La conexión está cerrada o no disponible.");
        }
    }
}
