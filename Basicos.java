package CursoJava_Ahorcado;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Basicos {

    public static String leerCadena(String pregunta) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String cadena = null;
        boolean valido = false;

        while (!valido) {

            try {
                System.out.println(pregunta + ": ");
                cadena = reader.readLine();
                valido = true;
            } catch (IOException e) {
                System.out.println("Error al leer la entrada. Intenta nuevamente.");
            }
        }

        return cadena;
    }
    public static int leerEntero(String pregunta) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		int numero = 0;
		boolean valido = false;

		while (!valido) {
			try {
				System.out.print(pregunta + " ");
				String linea = reader.readLine();
				numero = Integer.parseInt(linea);
				valido = true;
			} catch (NumberFormatException e) {
				System.out.println("Por favor, ingresa un número entero válido.");
			} catch (IOException e) {
				System.out.println("Error al leer la entrada. Intenta nuevamente.");
			}
		}
		return numero;
	}

}
