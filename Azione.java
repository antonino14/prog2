package borsanova;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Azione {

    private final Azienda azienda;
    private final Borsa borsa;
    private final int prezzoUnitarioIniziale;
    private int prezzoUnitario;
    private final int numeroTotale;
    private int numeroDisponibili;
    private final Map<Operatore, Allocazione> allocazioni;

    public Azione(Azienda azienda, Borsa borsa, int numeroTotale, int prezzoUnitarioIniziale) {
        if (azienda == null || borsa == null) {
            throw new IllegalArgumentException("Azienda e Borsa non possono essere null.");
        }
        if (numeroTotale <= 0 || prezzoUnitarioIniziale <= 0) {
            throw new IllegalArgumentException("Il numero di azioni e il prezzo unitario devono essere positivi.");
        }
        this.azienda = azienda;
        this.borsa = borsa;
        this.prezzoUnitarioIniziale = prezzoUnitarioIniziale;
        this.prezzoUnitario = prezzoUnitarioIniziale;
        this.numeroTotale = numeroTotale;
        this.numeroDisponibili = numeroTotale;
        this.allocazioni = new HashMap<>();
    }

    public Azienda getAzienda() {
        return azienda;
    }

    public Borsa getBorsa() {
        return borsa;
    }

    public int getPrezzoUnitario() {
        return prezzoUnitario;
    }

    public int getNumeroTotale() {
        return numeroTotale;
    }

    public int getNumeroDisponibili() {
        return numeroDisponibili;
    }

    public Map<Operatore, Integer> getAllocazioni() {
        Map<Operatore, Integer> result = new HashMap<>();
        for (Map.Entry<Operatore, Allocazione> entry : allocazioni.entrySet()) {
            result.put(entry.getKey(), entry.getValue().getQuantita());
        }
        return result;
    }

    public void assegnaAzioni(Operatore operatore, int quantita) {
        if (quantita <= 0 || quantita > numeroDisponibili) {
            throw new IllegalArgumentException("Quantità di azioni non disponibile o non valida.");
        }
        Allocazione allocazione = allocazioni.computeIfAbsent(operatore, k -> new Allocazione(operatore, 0));
        allocazione.incrementaQuantita(quantita);
        numeroDisponibili -= quantita;
    }

    public void rimuoviAzioni(Operatore operatore, int quantita) {
        if (quantita <= 0) {
            throw new IllegalArgumentException("Quantità di azioni da rimuovere non valida.");
        }
        Allocazione allocazione = allocazioni.get(operatore);
        if (allocazione == null || quantita > allocazione.getQuantita()) {
            throw new IllegalArgumentException("L'operatore non ha abbastanza azioni per vendere.");
        }
        allocazione.decrementaQuantita(quantita);
        numeroDisponibili += quantita;
        if (allocazione.getQuantita() == 0) {
            allocazioni.remove(operatore);
        }
    }

    public void aggiornaPrezzo(int nuovoPrezzo) {
        if (nuovoPrezzo <= 0) {
            throw new IllegalArgumentException("Il nuovo prezzo deve essere positivo.");
        }
        this.prezzoUnitario = nuovoPrezzo;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Azione azione = (Azione) obj;
        return azienda.equals(azione.azienda) && borsa.equals(azione.borsa);
    }

    @Override
    public int hashCode() {
        return Objects.hash(azienda, borsa);
    }

    @Override
    public String toString() {
        return "Azione{" +
                "azienda=" + azienda +
                ", borsa=" + borsa +
                ", prezzoUnitario=" + prezzoUnitario +
                ", numeroTotale=" + numeroTotale +
                ", numeroDisponibili=" + numeroDisponibili +
                '}';
    }
}
