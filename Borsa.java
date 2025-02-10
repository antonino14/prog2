package borsanova;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Borsa {

    private final String nome;
    private final Map<Azienda, Azione> quotazioni;
    private PoliticaPrezzo politicaPrezzo;

    public Borsa(String nome, PoliticaPrezzo politicaPrezzo) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Il nome della borsa non può essere vuoto.");
        }
        this.nome = nome;
        this.quotazioni = new HashMap<>();
        this.politicaPrezzo = politicaPrezzo;
    }

    public String getNome() {
        return nome;
    }

    public void aggiungiQuotazione(Azione azione) {
        Azienda azienda = azione.getAzienda();
        if (quotazioni.containsKey(azienda)) {
            throw new IllegalArgumentException("L'azienda è già quotata in questa borsa.");
        }
        quotazioni.put(azienda, azione);
    }

    public Map<Azienda, Azione> getQuotazioni() {
        return new HashMap<>(quotazioni); // Restituisce una copia per evitare modifiche esterne
    }

    public void cambiaPoliticaPrezzo(PoliticaPrezzo nuovaPolitica) {
        this.politicaPrezzo = nuovaPolitica;
    }

    public void acquistaAzioni(Operatore operatore, Azienda azienda, int quantita) {
        Azione azione = quotazioni.get(azienda);
        if (azione == null) {
            throw new IllegalArgumentException("L'azienda non è quotata in questa borsa.");
        }
        int prezzoTotale = azione.getPrezzoUnitario() * quantita;
        operatore.riduciBudget(prezzoTotale);
        azione.assegnaAzioni(operatore, quantita);
        politicaPrezzo.aggiornaPrezzo(azione, quantita, true);
    }

    public void vendiAzioni(Operatore operatore, Azienda azienda, int quantita) {
        Azione azione = quotazioni.get(azienda);
        if (azione == null) {
            throw new IllegalArgumentException("L'azienda non è quotata in questa borsa.");
        }
        azione.rimuoviAzioni(operatore, quantita);
        int prezzoTotale = azione.getPrezzoUnitario() * quantita;
        operatore.incrementaBudget(prezzoTotale);
        politicaPrezzo.aggiornaPrezzo(azione, quantita, false);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Borsa borsa = (Borsa) obj;
        return nome.equals(borsa.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome);
    }

    @Override
    public String toString() {
        return "Borsa{" +
                "nome='" + nome + '\'' +
                '}';
    }
}
