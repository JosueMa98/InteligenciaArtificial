class Nodo {
    int valor;
    Nodo izquierda;
    Nodo derecha;

    public Nodo(int valor) {
        this.valor = valor;
        this.izquierda = null;
        this.derecha = null;
    }
}

public class ArbolBinario {
    Nodo raiz;

    public ArbolBinario() {
        raiz = null;
    }

    public boolean vacio() {
        return raiz == null;
    }

    public void insertar(int valor) {
        raiz = insertarRecursivo(raiz, valor);
    }

    private Nodo insertarRecursivo(Nodo nodo, int valor) {
        if (nodo == null) {
            return new Nodo(valor);
        }

        if (valor < nodo.valor) {
            nodo.izquierda = insertarRecursivo(nodo.izquierda, valor);
        } else if (valor > nodo.valor) {
            nodo.derecha = insertarRecursivo(nodo.derecha, valor);
        }

        return nodo;
    }

    public Nodo buscarNodo(int valor) {
        return buscarNodoRecursivo(raiz, valor);
    }

    private Nodo buscarNodoRecursivo(Nodo nodo, int valor) {
        if (nodo == null || nodo.valor == valor) {
            return nodo;
        }

        if (valor < nodo.valor) {
            return buscarNodoRecursivo(nodo.izquierda, valor);
        }

        return buscarNodoRecursivo(nodo.derecha, valor);
    }

    public void imprimirArbol() {
        imprimirArbolRecursivo(raiz);
    }

    private void imprimirArbolRecursivo(Nodo nodo) {
        if (nodo != null) {
            imprimirArbolRecursivo(nodo.izquierda);
            System.out.print(nodo.valor + " ");
            imprimirArbolRecursivo(nodo.derecha);
        }
    }

    public static void main(String[] args) {
        ArbolBinario arbol = new ArbolBinario();
        arbol.insertar(100);
        arbol.insertar(60);
        arbol.insertar(22);
        arbol.insertar(40);
        arbol.insertar(73);

        System.out.println("Árbol:");
        arbol.imprimirArbol();

        System.out.println("\n¿El árbol está vacío? " + arbol.vacio());

        int valorABuscar = 22;
        Nodo nodoBuscado = arbol.buscarNodo(valorABuscar);
        if (nodoBuscado != null) {
            System.out.println("Buscar " + valorABuscar + ": Encontrado");
        } else {
            System.out.println("Buscar " + valorABuscar + ": No Encontrado");
        }
    }
}
