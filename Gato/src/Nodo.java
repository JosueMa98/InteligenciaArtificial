public class Nodo {
    String[][] tablero = new String[3][3];
    String siguienteJugador;
    Nodo padre;
    int valorHeuristico;
    int enProfundidad;

    public Nodo() {}

    public Nodo(String[][] tablero, Nodo padre, int valorHeuristico, int enProfundidad, String siguienteJugador) {
        this.tablero = tablero;
        this.padre = padre;
        this.valorHeuristico = valorHeuristico;
        this.enProfundidad = enProfundidad;
        this.siguienteJugador = siguienteJugador;
    }

    public Nodo(String[][] tablero) {
        this(tablero, null, 0, 0, null);
    }
}
