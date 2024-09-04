package trabbimm.com;

// Armazem.java
import java.util.LinkedList;
import java.util.Queue;

public class Armazem {
    private Queue<Recurso> recursos;
    private int capacidade;

    public Armazem(int capacidade) {
        this.capacidade = capacidade;
        this.recursos = new LinkedList<>();
    }

    public synchronized boolean adicionarRecurso(Recurso recurso) {
        if (recursos.size() < capacidade) {
            recursos.add(recurso);
            return true;
        }
        return false;
    }

    public synchronized Recurso removerRecurso() {
        return recursos.poll();
    }

    public synchronized int getTamanho() {
        return recursos.size();
    }

    public synchronized Queue<Recurso> getRecursos() {
        return new LinkedList<>(recursos);
    }
}

