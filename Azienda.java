package borsanova;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Azienda {

    private static final Map<String, Azienda> aziende = new HashMap<>();

    private final String nome;
    private final Map<Borsa, Azione> quotazioni;

    private Azienda(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Il nome dell'azienda non può essere vuoto.");
        }
        this.nome = nome;
        this.quotazioni = new HashMap<>();
    }

    public static Azienda of(String nome) {
        return aziende.computeIfAbsent(nome, Azienda::new);
    }

    public String getNome() {
        return nome;
    }

    public void quotaInBorsa(Borsa borsa, int numeroAzioni, int prezzoUnitario) {
        if (borsa == null) {
            throw new IllegalArgumentException("La borsa non può essere null.");
        }
        if (numeroAzioni <= 0 || prezzoUnitario <= 0) {
            throw new IllegalArgumentException("Il numero di azioni e il prezzo unitario devono essere positivi.");
        }
        if (quotazioni.containsKey(borsa)) {
            throw new IllegalArgumentException("L'azienda è già quotata in questa borsa.");
        }
        Azione azione = new Azione(this, borsa, numeroAzioni, prezzoUnitario);
        quotazioni.put(borsa, azione);
        borsa.aggiungiQuotazione(azione);
    }

    public Map<Borsa, Azione> getQuotazioni() {
        return new HashMap<>(quotazioni); // Restituisce una copia per evitare modifiche esterne
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Azienda azienda = (Azienda) obj;
        return nome.equals(azienda.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome);
    }

    @Override
    public String toString() {
        return "Azienda{" +
                "nome='" + nome + '\'' +
                '}';
    }
}