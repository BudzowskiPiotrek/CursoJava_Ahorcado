package CursoJava_Ahorcado;

import java.util.ArrayList;
import java.util.List;

public class Juego {

    private String palabraSecreta;
    private StringBuilder palabraActual;
    private int intentosRestantes;
    private List<Character> letrasAdivinadas;
    private List<Character> letrasFallidas;
    private int idUsuario;
    private Conector conector;

    public Juego(int idUsuario, Conector conector) {
        this.idUsuario = idUsuario;
        this.conector = conector;
        this.letrasAdivinadas = new ArrayList<>();
        this.letrasFallidas = new ArrayList<>();
        this.intentosRestantes = 6; 
    }

    public void iniciarPartida() {
        String tematica = Basicos.leerCadena("Introduce la tematica para la palabra:");
        int dificultad = Basicos.leerEntero("Introduce la dificultad (1-5)");

        Palabra palabraDB = conector.getPalabraAleatoria(tematica, dificultad);

        if (palabraDB != null) {
            this.palabraSecreta = palabraDB.getPalabra().toLowerCase();
            this.palabraActual = new StringBuilder();
            System.out.println("La palabra tiene " + palabraSecreta.length() + " letras.");
            jugar();
        } else {
            System.out.println("No se encontraron palabras con esa temática y dificultad. Intenta de nuevo.");
        }
    }

    private void jugar() {
        boolean partidaTerminada = false;

        while (!partidaTerminada) {
            mostrarEstadoJuego();
            String entrada = Basicos.leerCadena("Introduce una letra").toLowerCase();

            if (entrada.length() == 1) { // El jugador ingresó una letra
                char letra = entrada.charAt(0);
                if (letrasAdivinadas.contains(letra) || letrasFallidas.contains(letra)) {
                    System.out.println("Ya has intentado con esa letra, espabila");
                } else if (palabraSecreta.contains(String.valueOf(letra))) {
                    System.out.println("La letra '" + letra + "' está en la palabra.");
                    actualizarPalabraActual(letra);
                    letrasAdivinadas.add(letra);
                } else {
                    System.out.println("La letra '" + letra + "' no esta en la palabra");
                    letrasFallidas.add(letra);
                    intentosRestantes--;
                }
            } else if (entrada.length() > 1) { // El jugador intentó adivinar la palabra
                if (entrada.equals(palabraSecreta)) {
                    palabraActual = new StringBuilder(palabraSecreta);
                    System.out.println("Has adivinado la palabra");
                    partidaTerminada = true;
                } else {
                    System.out.println("Palabra incorrecta");
                    intentosRestantes--;
                }
            } else {
                System.out.println("Por favor, introduce una letra o una palabra.");
            }

            if (palabraActual.toString().equals(palabraSecreta)) {
                System.out.println("Has adivinado la palabra: " + palabraSecreta);
                conector.guardarHistorial(idUsuario, palabraSecreta, true, (6 - intentosRestantes));
                partidaTerminada = true;
            } else if (intentosRestantes <= 0) {
                System.out.println("Perdisteeee. La palabra secreta era: " + palabraSecreta);
                conector.guardarHistorial(idUsuario, palabraSecreta, false, (6 - intentosRestantes));
                partidaTerminada = true;
            }
        }
    }

    private void mostrarEstadoJuego() {
        System.out.println("\nPalabra: " + palabraActual);
        System.out.println("Intentos restantes: " + intentosRestantes);
        System.out.println("Letras fallidas: " + letrasFallidas);
    }

    private void actualizarPalabraActual(char letra) {
        for (int i = 0; i < palabraSecreta.length(); i++) {
            if (palabraSecreta.charAt(i) == letra) {
                palabraActual.setCharAt(i, letra);
            }
        }
    }
}
