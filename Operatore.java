package borsanova;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Operatore {

    private final String nome;
    private int budget;
    private final Map<Azione, Integer> azioniPossedute;

    public Operatore(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Il nome dell'operatore non può essere vuoto.");
        }
        this.nome = nome;
        this.budget = 0;
        this.azioniPossedute = new HashMap<>();
    }

    public String getNome() {
        return nome;
    }

    public int getBudget() {
        return budget;
    }

    public void deposita(int importo) {
        if (importo <= 0) {
            throw new IllegalArgumentException("L'importo del deposito deve essere positivo.");
        }
        this.budget += importo;
    }

    public void preleva(int importo) {
        if (importo <= 0) {
            throw new IllegalArgumentException("L'importo del prelievo deve essere positivo.");
        }
        if (this.budget < importo) {
            throw new IllegalArgumentException("Budget insufficiente per il prelievo.");
        }
        this.budget -= importo;
    }

    public void acquistaAzioni(Borsa borsa, Azienda azienda, int cifraInvestita) {
        Azione azione = borsa.getQuotazioni().get(azienda);
        if (azione == null) {
            throw new IllegalArgumentException("L'azienda non è quotata in questa borsa.");
        }
        int prezzoUnitario = azione.getPrezzoUnitario();
        int quantitaAcquistabile = cifraInvestita / prezzoUnitario;
        int resto = cifraInvestita % prezzoUnitario;

        if (quantitaAcquistabile <= 0) {
            throw new IllegalArgumentException("La cifra investita non è sufficiente per acquistare azioni.");
        }
        if (quantitaAcquistabile > azione.getNumeroDisponibili()) {
            throw new IllegalArgumentException("Non ci sono abbastanza azioni disponibili per l'acquisto.");
        }

        this.riduciBudget(cifraInvestita - resto);
        azione.assegnaAzioni(this, quantitaAcquistabile);
        this.azioniPossedute.put(azione, this.azioniPossedute.getOrDefault(azione, 0) + quantitaAcquistabile);
    }

    public void vendiAzioni(Borsa borsa, Azienda azienda, int quantita) {
        Azione azione = borsa.getQuotazioni().get(azienda);
        if (azione == null) {
            throw new IllegalArgumentException("L'azienda non è quotata in questa borsa.");
        }
        int possedute = this.azioniPossedute.getOrDefault(azione, 0);
        if (quantita > possedute) {
            throw new IllegalArgumentException("Non possiedi abbastanza azioni per vendere.");
        }

        azione.rimuoviAzioni(this, quantita);
        this.incrementaBudget(azione.getPrezzoUnitario() * quantita);
        this.azioniPossedute.put(azione, possedute - quantita);
    }

    public int calcolaCapitaleTotale() {
        int valoreAzioniPossedute = this.azioniPossedute.entrySet().stream()
                .mapToInt(entry -> entry.getKey().getPrezzoUnitario() * entry.getValue())
                .sum();
        return this.budget + valoreAzioniPossedute;
    }

    public Map<Azione, Integer> getAzioniPossedute() {
        return new HashMap<>(azioniPossedute);
    }

    public void riduciBudget(int importo) {
        if (this.budget < importo) {
            throw new IllegalArgumentException("Budget insufficiente.");
        }
        this.budget -= importo;
    }

    public void incrementaBudget(int importo) {
        this.budget += importo;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Operatore operatore = (Operatore) obj;
        return nome.equals(operatore.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome);
    }

    @Override
    public String toString() {
        return "Operatore{" +
                "nome='" + nome + '\'' +
                ", budget=" + budget +
                '}';
    }
}