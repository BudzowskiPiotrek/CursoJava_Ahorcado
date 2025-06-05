package CursoJava_Ahorcado;

public class Palabra {
    private String palabra;
    private String tematica;
    private int dificultad;

    public Palabra(String palabra, String tematica, int dificultad) {
        this.palabra = palabra;
        this.tematica = tematica;
        this.dificultad = dificultad;
    }

    public String getPalabra() {
        return palabra;
    }

    public void setPalabra(String palabra) {
        this.palabra = palabra;
    }

    public String getTematica() {
        return tematica;
    }

    public void setTematica(String tematica) {
        this.tematica = tematica;
    }

    public int getDificultad() {
        return dificultad;
    }

    public void setDificultad(int dificultad) {
        this.dificultad = dificultad;
    }

    @Override
    public String toString() {
        return "Palabra=" + palabra + ", tematica=" + tematica + ", dificultad=" + dificultad;
    }

}
