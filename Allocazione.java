package borsanova;

import java.util.Objects;

public class Allocazione {
    private final Operatore operatore;
    private int quantita;

    public Allocazione(Operatore operatore, int quantita) {
        this.operatore = Objects.requireNonNull(operatore, "Operatore non può essere null");
        if (quantita < 0) {
            throw new IllegalArgumentException("La quantità non può essere negativa");
        }
        this.quantita = quantita;
    }

    public Operatore getOperatore() {
        return operatore;
    }

    public int getQuantita() {
        return quantita;
    }

    public void incrementaQuantita(int incremento) {
        if (incremento < 0) {
            throw new IllegalArgumentException("L'incremento non può essere negativo");
        }
        this.quantita += incremento;
    }

    public void decrementaQuantita(int decremento) {
        if (decremento < 0 || decremento > quantita) {
            throw new IllegalArgumentException("Il decremento non può essere negativo o maggiore della quantità posseduta");
        }
        this.quantita -= decremento;
    }
}