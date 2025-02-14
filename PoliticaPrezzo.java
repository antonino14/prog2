package borsanova;

public class PoliticaPrezzo {

    public enum TipoPolitica {
        INCREMENTO_COSTANTE,
        DECREMENTO_COSTANTE,
        VARIAZIONE_COSTANTE,
        ALTRE
    }

    private final TipoPolitica tipo;
    private final int k;
    private final int h;

    public PoliticaPrezzo(TipoPolitica tipo, int k, int h) {
        if (k <= 0 || h <= 0) {
            throw new IllegalArgumentException("I valori di k e h devono essere positivi.");
        }
        this.tipo = tipo;
        this.k = k;
        this.h = h;
    }

    public void aggiornaPrezzo(Azione azione, int quantita, boolean acquisto) {
        int nuovoPrezzo = azione.getPrezzoUnitario();
        switch (tipo) {
            case INCREMENTO_COSTANTE:
                if (acquisto) {
                    nuovoPrezzo += k;
                }
                break;
            case DECREMENTO_COSTANTE:
                if (!acquisto) {
                    nuovoPrezzo = Math.max(1, nuovoPrezzo - h);
                }
                break;
            case VARIAZIONE_COSTANTE:
                if (acquisto) {
                    nuovoPrezzo += k;
                } else {
                    nuovoPrezzo = Math.max(1, nuovoPrezzo - h);
                }
                break;
            case ALTRE:
                // Aggiungere qui altre logiche di prezzo
                break;
        }
        azione.aggiornaPrezzo(nuovoPrezzo);
    }
}