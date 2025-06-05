package CursoJava_Ahorcado;

public class Main {

    public static Conector conector = new Conector();
    public static int idUsuarioActual = -1; 

    public static void main(String[] args) {
        appStart();
    }

    private static void appStart() {
        String usuarioStr;
        String contrasenaStr;

        System.out.println("----Juego del Ahorcado----");

        String tieneCuenta = Basicos.leerCadena("¿Tienes ya una cuenta? (Si/No)");

        if (tieneCuenta.equalsIgnoreCase("Si")) {
            usuarioStr = Basicos.leerCadena("Escribe tu usuario");
            contrasenaStr = Basicos.leerCadena("Escribe tu contraseña");
            idUsuarioActual = conector.login(usuarioStr, contrasenaStr);

            if (idUsuarioActual != -1) {
                System.out.println("Bienvenido, " + usuarioStr);
                menuJuego();
            } else {
                System.out.println("Usuario o contraseña incorrectos. Saliendo de la aplicación...");
            }

        } else if (tieneCuenta.equalsIgnoreCase("No")) {
            String quiereRegistrarse = Basicos.leerCadena("¿Quieres registrarte? (Si/No)");
            if (quiereRegistrarse.equalsIgnoreCase("Si")) {
                usuarioStr = Basicos.leerCadena("Escribe tu nuevo usuario");
                contrasenaStr = Basicos.leerCadena("Escribe tu nueva contraseña");
                idUsuarioActual = conector.registrar(usuarioStr, contrasenaStr);

                if (idUsuarioActual != -1) {
                    System.out.println("¡Registro exitoso! Bienvenido, " + usuarioStr + ".");
                    menuJuego();
                } else {
                    System.out.println("No se pudo registrar el usuario. Saliendo de la aplicación...");
                }
            } else {
                System.out.println("Saliendo de la aplicación...");
            }
        } else {
            System.out.println("No se te entiende! Saliendo de la aplicación...");
        }
    }

    private static void menuJuego() {
        int opcion;
        do {
            System.out.println("\n--- MENÚ DEL JUEGO ---");
            System.out.println("1. Jugar");
            System.out.println("2. Añadir Palabra");
            System.out.println("3. Ver Historial de Partidas");
            System.out.println("4. Salir");
            opcion = Basicos.leerEntero("Elige una opción:");

            switch (opcion) {
                case 1:
                    Juego juego = new Juego(idUsuarioActual, conector);
                    juego.iniciarPartida();
                    break;
                case 2:
                    System.out.println("\n--- AÑADIR PALABRA ---");
                    String palabra = Basicos.leerCadena("Introduce la palabra");
                    String tematica = Basicos.leerCadena("Introduce la temática");
                    int dificultad = Basicos.leerEntero("Introduce la dificultad (1-5)");
                    conector.addPalabra(palabra, tematica, dificultad);
                    break;
                case 3:
                    conector.mostrarHistorial(idUsuarioActual);
                    break;
                case 4:
                    System.out.println("Saliendo del juego.");
                    break;
                default:
                    System.out.println("Intenta de nuevo.");
            }
        } while (opcion != 4);
    }
}
