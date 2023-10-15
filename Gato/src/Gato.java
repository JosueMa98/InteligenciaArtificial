import java.io.*;
import java.util.*;

public class Gato {
    public static int INFINITO = 100;
    
    public Gato() {
    }

    public Nodo inicializarNodo() {
        return new Nodo();
    }

    public Nodo inicializarNodoConEntrada(String[][] tablero) {
        Nodo raiz = new Nodo();
        raiz.tablero = tablero;
        raiz.siguienteJugador = "X"; 
        return raiz;
    }

   
    public String[][] leerTableroDesdeArchivo(String nombreArchivo) throws IOException {
        File archivoEntrada = new File(nombreArchivo);
        BufferedReader lector = new BufferedReader(new FileReader(archivoEntrada));
        String[][] tablero = new String[3][3];
        String lineaEntrada;
        for (int fila = 0; ((lineaEntrada = lector.readLine()) != null); fila++) {
            tablero = agregarLineaEntradaAFila(fila, tablero, lineaEntrada);
        }
        return tablero;
    }

    public String[][] agregarLineaEntradaAFila(int fila, String[][] tablero, String lineaEntrada) {
        char unCaracter;
        int columna = 0;
        for (int indice = 0; indice < lineaEntrada.length(); indice = indice + 2) {
            unCaracter = lineaEntrada.charAt(indice);
            if (unCaracter == ' ') {
                tablero[fila][columna] = null;
                columna++;
            } else {
                tablero[fila][columna] = Character.toString(unCaracter);
                columna++;
            }
        }
        return tablero;
    }

    // Imprimir el tablero del juego.
    public void imprimirTablero(String[][] tablero) {
        int tamanoTablero = tablero.length;
        System.out.println("Tablero Actual: ");
        System.out.print("-------------");
        System.out.println();
        for (int fila = 0; fila < tamanoTablero; fila++) {
            System.out.print("|");
            for (int columna = 0; columna < tamanoTablero; columna++) {
                if (tablero[fila][columna] == null) {
                    System.out.print("  " + " |");
                } else {
                    System.out.print(" " + tablero[fila][columna] + " |");
                }
            }
            System.out.println();
            System.out.print("-------------");
            System.out.println();
        }
    }

    // Comprobar si el nodo es una hoja.
    public boolean esNodoHoja(Nodo nodoActual) {
        return this.comprobarVictoria(nodoActual) || (this.escanearCasillaVaciaEnTablero(nodoActual) == null);
    }

    // Evaluar el valor heurístico solo en los nodos hoja.
    public int evaluarValorHeuristico(Nodo nodoActual) {
        if (nodoActual.siguienteJugador.equals("X") && this.comprobarVictoria(nodoActual)) return -1;
        if (nodoActual.siguienteJugador.equals("O") && this.comprobarVictoria(nodoActual)) return 1;
        return 0;
    }

    // Comprobar victoria en el nodo.
    public boolean comprobarVictoria(Nodo nodoActual) {
        return (this.comprobarVictoriaEnFila(nodoActual)
                || this.comprobarVictoriaEnColumna(nodoActual)
                || this.comprobarVictoriaEnDiagonal(nodoActual));
    }

    public boolean comprobarVictoriaEnFila(Nodo nodoActual) {
        for (int fila = 0; fila < nodoActual.tablero.length; fila++) {
            int vecesQueSeRepiteElNodo = 0;
            String elementoEvaluado = nodoActual.tablero[fila][0];
            if (elementoEvaluado == null) break;
            for (int columna = 1; columna < nodoActual.tablero.length; columna++) {
                String siguienteCadena = nodoActual.tablero[fila][columna];
                if (siguienteCadena == null) break;
                else if (!elementoEvaluado.equals(siguienteCadena)) break;
                else vecesQueSeRepiteElNodo++;
            }
            if (vecesQueSeRepiteElNodo == 2) return true;
        }
        return false;
    }

    public boolean comprobarVictoriaEnColumna(Nodo nodoActual) {
        for (int columna = 0; columna < nodoActual.tablero.length; columna++) {
            int vecesQueSeRepiteElNodo = 0;
            String elementoEvaluado = nodoActual.tablero[0][columna];
            if (elementoEvaluado == null) break;
            for (int fila = 1; fila < nodoActual.tablero.length; fila++) {
                String siguienteCadena = nodoActual.tablero[fila][columna];
                if (siguienteCadena == null) break;
                else if (!elementoEvaluado.equals(siguienteCadena)) break;
                else vecesQueSeRepiteElNodo++;
            }
            if (vecesQueSeRepiteElNodo == 2) return true;
        }
        return false;
    }

    public boolean comprobarVictoriaEnDiagonal(Nodo nodoActual) {
        String[][] unTablero = nodoActual.tablero;
        if (unTablero[1][1] != null) {
            if (unTablero[0][0] != null && unTablero[2][2] != null) return this.comprobarVictoriaEnDiagonalIzquierda(nodoActual);
            else if (unTablero[0][2] != null && unTablero[2][0] != null) return this.comprobarVictoriaEnDiagonalDerecha(nodoActual);
            else return false;
        } else return false;
    }

    public boolean comprobarVictoriaEnDiagonalIzquierda(Nodo nodoActual) {
        String[][] unTablero = nodoActual.tablero;
        return unTablero[1][1].equals(unTablero[0][0]) && unTablero[1][1].equals(unTablero[2][2]);
    }

    public boolean comprobarVictoriaEnDiagonalDerecha(Nodo nodoActual) {
        String[][] unTablero = nodoActual.tablero;
        return unTablero[1][1].equals(unTablero[0][2]) && unTablero[1][1].equals(unTablero[2][0]);
    }

    public int[] escanearCasillaVaciaEnTablero(Nodo nodoActual) {
        int tamanoTablero = nodoActual.tablero.length;
        for (int fila = 0; fila < tamanoTablero; fila++) {
            for (int columna = 0; columna < tamanoTablero; columna++) {
                if (nodoActual.tablero[fila][columna] == null) return this.agregarValorAArray(fila, columna);
            }
        }
        return null;
    }

    public ArrayList<int[]> escanearTodasLasCasillasVaciasEnTablero(Nodo nodoActual) {
        int tamanoTablero = nodoActual.tablero.length;
        ArrayList<int[]> unArrayList = new ArrayList<int[]>();
        for (int fila = 0; fila < tamanoTablero; fila++) {
            for (int columna = 0; columna < tamanoTablero; columna++) {
                if (nodoActual.tablero[fila][columna] == null) unArrayList.add(this.agregarValorAArray(fila, columna));
            }
        }
        return unArrayList;
    }

    public int[] agregarValorAArray(int unNumero, int otroNumero) {
        int[] arreglo = new int[2];
        arreglo[0] = unNumero;
        arreglo[1] = otroNumero;
        return arreglo;
    }

    public String[][] copiarTablero(String[][] unTablero) {
        int tamanoTablero = unTablero.length;
        String[][] nuevoTablero = new String[tamanoTablero][tamanoTablero];
        for (int fila = 0; fila < tamanoTablero; fila++) {
            for (int columna = 0; columna < tamanoTablero; columna++)
                nuevoTablero[fila][columna] = unTablero[fila][columna];
        }
        return nuevoTablero;
    }

    public String[][] actualizarTablero(Nodo nodoActual, int[] casillaVaciaEnTablero) {
        String[][] nuevoTablero = this.copiarTablero(nodoActual.tablero);
        nuevoTablero[casillaVaciaEnTablero[0]][casillaVaciaEnTablero[1]] = nodoActual.siguienteJugador;
        return nuevoTablero;
    }

    public Nodo obtenerSiguienteMovimiento(Nodo nodoActual, int[] casillaVaciaEnTablero) {
        if (this.esNodoHoja(nodoActual) == true) return null;
        else {
            if (nodoActual.siguienteJugador.equals("X")) return new Nodo(this.actualizarTablero(nodoActual, casillaVaciaEnTablero), nodoActual, this.evaluarValorHeuristico(nodoActual), nodoActual.enProfundidad + 1, "O");
            else return new Nodo(this.actualizarTablero(nodoActual, casillaVaciaEnTablero), nodoActual, this.evaluarValorHeuristico(nodoActual), nodoActual.enProfundidad + 1, "X");
        }
    }

    public Vector<Nodo> obtenerTodosLosSiguientesMovimientos(Nodo nodoActual) {
        Vector<Nodo> todosLosSiguientesMovimientos = new Vector<Nodo>();
        ArrayList<int[]> todasLasCasillasVaciasEnTablero = this.escanearTodasLasCasillasVaciasEnTablero(nodoActual);
        int numeroDeCasillasVaciasEnTablero = todasLasCasillasVaciasEnTablero.size();
        for (int i = 0; i < numeroDeCasillasVaciasEnTablero; i++) {
            todosLosSiguientesMovimientos.add(this.obtenerSiguienteMovimiento(nodoActual, todasLasCasillasVaciasEnTablero.get(i)));
        }
        return todosLosSiguientesMovimientos;
    }

    /*
     * Obtener máximo/mínimo.
     */
    public int obtenerMaximoDeDosEnteros(int unEntero, int otroEntero) {
        if (unEntero < otroEntero) return otroEntero;
        else return unEntero;
    }

    public int obtenerMinimoDeDosEnteros(int unEntero, int otroEntero) {
        if (unEntero > otroEntero) return otroEntero;
        else return unEntero;
    }

    public Nodo obtenerNodoMinimoEnLista(Vector<Nodo> unaListaNodo) {
        Nodo nodoMinimo = unaListaNodo.get(0);
        int tamanoDeLista = unaListaNodo.size();
        for (int indice = 0; indice < tamanoDeLista; indice++)
            if (nodoMinimo.valorHeuristico > unaListaNodo.get(indice).valorHeuristico) nodoMinimo = unaListaNodo.get(indice);
        return nodoMinimo;
    }

    public Nodo obtenerNodoMaximoEnLista(Vector<Nodo> unaListaNodo) {
        Nodo nodoMaximo = unaListaNodo.get(0);
        int tamanoDeLista = unaListaNodo.size();
        for (int indice = 0; indice < tamanoDeLista; indice++)
            if (nodoMaximo.valorHeuristico < unaListaNodo.get(indice).valorHeuristico) nodoMaximo = unaListaNodo.get(indice);
        return nodoMaximo;
    }

    /*
     * Minimax
     */
    Vector<Nodo> nodosSiguientesPosibles = new Vector<Nodo>();

    public int obtenerMinimax(Nodo nodoActual) {
        if (this.esNodoHoja(nodoActual) == true) return this.miniMaxNodoHoja(nodoActual);
        else return this.miniMaxNodoNoHoja(nodoActual);
    }

    public int miniMaxNodoNoHoja(Nodo nodoActual) {
        Vector<Nodo> todosLosSiguientesMovimientos = this.obtenerTodosLosSiguientesMovimientos(nodoActual);
        for (int indice = 0; indice < todosLosSiguientesMovimientos.size(); indice++) {
            Nodo SiguienteMovimiento = todosLosSiguientesMovimientos.get(indice);
            if (nodoActual.siguienteJugador.equals("O"))
                nodoActual.valorHeuristico = this.obtenerMinimoDeDosEnteros(nodoActual.valorHeuristico, this.obtenerMinimax(SiguienteMovimiento));
            else
                nodoActual.valorHeuristico = this.obtenerMaximoDeDosEnteros(nodoActual.valorHeuristico, this.obtenerMinimax(SiguienteMovimiento));
        }
        if (this.obtenerNodosSiguientesPosibles(nodoActual) != null) nodosSiguientesPosibles.add(nodoActual);
        return nodoActual.valorHeuristico;
    }

    public int miniMaxNodoHoja(Nodo nodoActual) {
        if (this.obtenerNodosSiguientesPosibles(nodoActual) != null) nodosSiguientesPosibles.add(nodoActual);
        return this.evaluarValorHeuristico(nodoActual);
    }

    /*
     * Poda alfa beta.
     */
    public int inicializarAlfa(Nodo nodoActual) {
        if (this.esNodoHoja(nodoActual) == true) return this.evaluarValorHeuristico(nodoActual);
        else return -INFINITO;
    }

    public int inicializarBeta(Nodo nodoActual) {
        if (this.esNodoHoja(nodoActual) == true) return this.evaluarValorHeuristico(nodoActual);
        else return INFINITO;
    }

    public int minimaxPodadoAlfaBeta(Nodo nodoActual, int alfa, int beta) {
        int alfaDelNodoActual = alfa;
        int betaDelNodoActual = beta;
        if (this.esNodoHoja(nodoActual) == true) return this.miniMaxNodoHoja(nodoActual);
        else if (nodoActual.siguienteJugador.equals("O"))
            return this.minimaxAlfa_ActualNodoMax(nodoActual, alfaDelNodoActual, betaDelNodoActual);
        else
            return this.minimaxBeta_ActualNodoMin(nodoActual, alfaDelNodoActual, betaDelNodoActual);
    }

    public int minimaxAlfa_ActualNodoMax(Nodo nodoActual, int alfaDelNodoActual, int betaDelNodoActual) {
        Vector<Nodo> todosLosSiguientesMovimientos = this.obtenerTodosLosSiguientesMovimientos(nodoActual);
        for (int indice = 0; indice < todosLosSiguientesMovimientos.size(); indice++) {
            Nodo unSiguienteMovimiento = todosLosSiguientesMovimientos.get(indice);
            int minActual = this.minimaxPodadoAlfaBeta(unSiguienteMovimiento, alfaDelNodoActual, betaDelNodoActual);
            betaDelNodoActual = this.obtenerMinimoDeDosEnteros(betaDelNodoActual, minActual);
            nodoActual.valorHeuristico = this.obtenerMinimoDeDosEnteros(nodoActual.valorHeuristico, betaDelNodoActual);
            if (alfaDelNodoActual >= betaDelNodoActual) break;
        }
        if (this.obtenerNodosSiguientesPosibles(nodoActual) != null) nodosSiguientesPosibles.add(nodoActual);
        return betaDelNodoActual;
    }

    public int minimaxBeta_ActualNodoMin(Nodo nodoActual, int alfaDelNodoActual, int betaDelNodoActual) {
        Vector<Nodo> todosLosSiguientesMovimientos = this.obtenerTodosLosSiguientesMovimientos(nodoActual);
        for (int indice = 0; indice < todosLosSiguientesMovimientos.size(); indice++) {
            Nodo SiguienteMovimiento = todosLosSiguientesMovimientos.get(indice);
            int maxActual = this.minimaxPodadoAlfaBeta(SiguienteMovimiento, alfaDelNodoActual, betaDelNodoActual);
            alfaDelNodoActual = this.obtenerMaximoDeDosEnteros(alfaDelNodoActual, maxActual);
            nodoActual.valorHeuristico = this.obtenerMaximoDeDosEnteros(nodoActual.valorHeuristico, alfaDelNodoActual);
            if (alfaDelNodoActual >= betaDelNodoActual) break;
        }
        if (this.obtenerNodosSiguientesPosibles(nodoActual) != null) nodosSiguientesPosibles.add(nodoActual);
        return alfaDelNodoActual;
    }

    // Obtener el próximo movimiento.
    public Nodo obtenerNodosSiguientesPosibles(Nodo nodoActual) {
        if (nodoActual.enProfundidad == 1) return nodoActual;
        else return null;
    }

    public Nodo obtenerSiguienteNodoAMover(Nodo nodoActual) {
        this.minimaxPodadoAlfaBeta(nodoActual, this.inicializarAlfa(nodoActual), this.inicializarBeta(nodoActual));
        Nodo nuevoNodo = this.obtenerNodoMaximoEnLista(nodosSiguientesPosibles);
        nodosSiguientesPosibles.removeAllElements();
        return nuevoNodo;
    }

    /*
     * Juego
     */
    public void movimientoHumano(Nodo nodoActual) {
        Nodo nuevoNodo = new Nodo();
        int[] entradaHumana = this.obtenerEntradaCorrectaDelMovimientoHumano(nodoActual);
        nuevoNodo = this.obtenerSiguienteMovimiento(nodoActual, entradaHumana);
        this.imprimirTablero(nuevoNodo.tablero);
        if (this.comprobarVictoria(nuevoNodo)) System.out.println("¡Has ganado!");
        else if (this.esNodoHoja(nuevoNodo)) System.out.println("Empate");
        else this.movimientoMaquina(nuevoNodo);
    }

    public void movimientoMaquina(Nodo nodoActual) {
        Nodo nuevoNodo = this.inicializarNodoConEntrada(nodoActual.tablero);
        nuevoNodo = this.obtenerSiguienteNodoAMover(nuevoNodo);
        this.imprimirTablero(nuevoNodo.tablero);
        if (this.comprobarVictoria(nuevoNodo)) System.out.println("La IA ganó");
        else if (this.esNodoHoja(nuevoNodo)) System.out.println("Empate");
        else this.movimientoHumano(nuevoNodo);
    }

    public void jugar() {
        Nodo raiz = this.inicializarNodo();
        this.imprimirTablero(raiz.tablero);
        int jugador = this.obtenerJugador();
        if (jugador == 1) {
            raiz.siguienteJugador = "O";
            this.movimientoHumano(raiz);
        } else {
            raiz.siguienteJugador = "X";
            this.movimientoMaquina(raiz);
        }
    }

    /*
     * Métodos obtener quien empieza, validar casilla
     */
    public int obtenerJugador() {
        Scanner jugador = new Scanner(System.in);
        System.out.print("¿Quieres jugar primero? Sí (1). No (0): ");
        return jugador.nextInt();
    }

    public int[] obtenerEntradaCorrectaDelMovimientoHumano(Nodo nodoActual) {
        Scanner entrada = new Scanner(System.in);
        System.out.println("Introduce tus coordenadas (fila y columna) separadas por espacios.");
        int[] entradaJugador = new int[2];
        int casillaVacia = -1;
        while (casillaVacia == -1) {
            try {
                entradaJugador[0] = entrada.nextInt();
                entradaJugador[1] = entrada.nextInt();
                if (entradaJugador[0] >= 0 && entradaJugador[0] <= 2 && entradaJugador[1] >= 0 && entradaJugador[1] <= 2 && nodoActual.tablero[entradaJugador[0]][entradaJugador[1]] == null) casillaVacia = 0;
                else {
                    System.out.println("Celda no válida. Elije otra.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada no válida. Introduce dos números separados por espacios.");
                entrada = new Scanner(System.in);
            }
        }
        return entradaJugador;
    }

    public static void main(String[] args) {
        Gato juego = new Gato();
        juego.jugar();
    }
}
