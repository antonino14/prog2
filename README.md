# prog2
Descrizione delle entità coinvolte
Le entità coinvolte nel progetto sono: aziende, operatori, borse e azioni. Di seguito ne vengono illustrate le caratteristiche principali (le informazioni che le descrivono e le competenze che possiedono).

Attenzione: questa versione del tema contiene numerosi chiarimenti e differenze non trascurabili rispetto a quella del primo appello, se aveva iniziato a sviluppare la sua soluzione sulla scorta delle tracce precedenti, riveda con cura il suo lavoro per essere certo che sia in linea con quanto richiesto in questo documento.

Azienda
Una azienda è caratterizzata da un nome (non vuoto); ogni azienda può decidere di quotarsi in una o più delle borse presenti (al più una volta per ciascuna di esse), nel farlo decide per ciascuna borsa sia il numero di azioni che intende emettere che il loro prezzo unitario iniziale (entrambe le grandezze sono per semplicità rappresentate da numeri interi positivi). Ogni azienda tiene traccia delle borse in cui è quotata.

Operatore
Un operatore (di borsa) è caratterizzato da un nome (non vuoto) e da un budget (rappresentato per semplicità da un numero intero non negativo, inizialmente pari a 0).

Ogni operatore può decidere di acquistare o vendere azioni di una o più aziende presenti in una o più borse. L'acquisto di azioni a fonte di una certa cifra investita comporta una diminuzione del budget pari al prezzo corrente dell'azione per il numero di azioni acquistabili (che deve essere un numero intero positivo e non può eccedere quello di azioni disponibili ancora in possesso della borsa); tale operazione, nel caso in cui la cifra investita non sia un multiplo intero del prezzo corrente dell'azione, genera un resto (che non deve mai superare il valore corrente dell'azione) che resta nel budget dell'operatore. La vendita di un dato numero di azioni comporta viceversa un aumento del budget pari al prezzo corrente dell'azione per il numero di azioni vendute (che deve essere un numero intero positivo).

Il budget può essere inoltre aumentato grazie a un deposito e diminuito grazie a un prelievo (entrambi rappresentati da numeri interi positivi), ma in nessun caso (compreso l'acquisto di azioni) può diventare negativo; è responsabilità esclusiva dell'operatore garantire che il suo budget non diventi mai negativo.

L'operatore conosce le azioni da esso correntemente possedute (ma non necessariamente dell'intera storia degli acquisti e delle vendite); grazie a tale conoscenza è in grado di determinare il suo capitale totale dato dalla somma del suo budget e del valore corrente delle azioni da esso possedute.

Borsa e azione
Una borsa è caratterizzata da un nome (non vuoto); la borsa si occupa delle quotazioni delle varie aziende emettendo le relative azioni.

La borsa tiene inoltre traccia delle allocazioni delle azioni emesse agli operatori, in modo da poter sapere per ciascuna azienda quotata di quante azioni è in possesso ciascun operatore e quante siano ancora disponibili (ossia non siano allocate ad alcun operatore); è responsabilità esclusiva della borsa garantire che un operatore non venda più azioni di quante gliene siano correntemente allocate, o compri più azioni di quante siano disponibili al momento dell'acquisto.

La borsa può cambiare il prezzo delle azioni in seguito ad ogni vendita, o acquisto; per farlo segue una politica di prezzo che, data l'azione e il numero di quante ne sono state vendute, o comprate, indica il nuovo prezzo dell'azione; la politica di prezzo può cambiare in ogni momento, viceversa il prezzo delle azioni può cambiare esclusivamente nel momento di una compravendita (ed esclusivamente tramite l'applicazione della politica di prezzo corrente). Ad esempio, una politica di prezzo

ad incremento costante 
 è tale per cui ad ogni acquisto, il prezzo dell'azione aumenta di 
 unità, restando invariato in caso di vendita;

a decremento costante 
 è tale per cui ad ogni vendita il prezzo dell'azione diminuisce di 
 unità (se maggiore dI 
, o diventando 
 viceversa), restando invariato in caso di acquisto;

a variazione costante 
 che si comporta come un incremento costante 
 (in caso di acquisto) e come un decremento costante 
 (in caso di vendita);

altre politiche possono cambiare il prezzo a seconda dell'azione e del numero di esse che vengono scambiate: ad esempio aumentando in modo lineare il prezzo tanto più quante azioni sono vendute, oppure diminuendo il prezzo delle azioni di aziende il cui nome inizia per vocale.

Ovviamente l'azione dipende, oltre che dall'azienda, dalla borsa che l'ha emessa e il suo prezzo può essere cambiato esclusivamente da essa (esclusivamente a seguito di un acquisto, o vendita); qualunque altra entità oltre alla borsa che l'ha emessa venga a conoscenza di una azione potrà interrogarla, ma non dovrà poter modificare direttamente le informazioni che essa conserva!

Un suggerimento su identità e confronti e omonimia
Le entità sopra descritte hanno tutte un nome, può essere molto utile ritenere identiche due entità che hanno il medesimo nome (per semplificare i confronti tra esse, mantenerle in ordine lessicografico e in genere adoperarle all'interno delle Collections); come è ovvio, questo sarebbe però del tutto insensato se fosse possibile creare più entità omonime (ossia che abbiano lo stesso nome)!

Per fare in modo che non possano esistere omonimi di una entità è possibile sostituire i suoi costruttori pubblici con metodi di fabbricazione che, grazie ad un attributo statico che tenga traccia dei nomi già usati, impediscano la creazione di istanze omonime.

public class NameBasedExample implements Comparable<NameBasedExample> {

  private static final SortedSet<String> USED_NAMES = new TreeSet<>();

  public final String name;

  //  other fields may appear here

  public static NameBasedExample of(final String name) {
    if (Objects.requireNonNull(name, "Name must not be null.").isBlank())
      throw new IllegalArgumentException("Name must not be empty.");
    if (USED_NAMES.contains(name)) 
      throw new IllegalArgumentException("Name already used.");
    USED_NAMES.add(name);
    return new NameBasedExample(name);
  }

  private NameBasedExample(final String name) {
    this.name = name;
    // other initializations may appear here
  }

  // other methods may appear here

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof NameBasedExample other)) return false;
    return name.equals(other.name);
  }

  @Override
  public int hashCode() {
    return name.hashCode();
  }

  @Override
  public int compareTo(NameBasedExample other) {
    return name.compareTo(other.name);
  }
}
Ovviamente sono possibili molte altre strategie per gestire l'omonimia così come è possibile confrontare le entità con criteri più ricchi di quello basato sul solo nome.

Attenzione: sarebbe difficilmente plausibile definire una gerarchia in cui le entità "con nome" del progetto siano sottoclassi di NameBasedExample: non solo sarebbe più difficile controllare che l'unicità del nome valga solo tra istanze della stessa classe (ad es. tra borse, ma non tra borse e operatori), ma sopratutto diventerebbe pressoché inevitabile consentire la comparazione di istanze di entità di sottoclassi diverse tra loro. Inoltre, osservi che nel codice d'esempio l'attributo USED_NAMES serve solo allo scopo di impedire la creazione di istanze omonime, non sarebbe sensato utilizzarlo per elencare, o reperire, i nomi delle istanze già create: aspettarsi che sia competenza di una istanza di NameBasedExample conoscere i nomi di tutte le altre sarebbe molto difficilmente giustificabile.

Nota bene: può accadere che se più test vengono eseguiti nella medesima istanza della JVM l'unicità del nome non sia garantita, in quanto le istanze create in un test potrebbero persistere e interferire con quelle create in un altro test. Per questa ragione i test del docente non contengono mai lo stesso nome in più di un test. Se intende sviluppare test aggiuntivi, è conveniente che segua il medesimo approccio.

Cosa è necessario implementare
Dovrà implementare una gerarchia di oggetti utili a:

rappresentare le entità fondamentali aziende, operatori, borse e azioni e, tra le altre, le informazioni accessorie come le allocazioni e le politiche di prezzo;

gestire (oltre alla costruzione delle entità) le competenze essenziali come la quotazione in borsa e le compravendite di azioni senza dimenticare le altre competenze che rendano adeguate (nel preciso senso dato a questo termine nel contesto dell'insegnamento) le specifiche dei vari oggetti (con particolare riferimento all'osservabilità);

implementare tutti i client, ossia le classi di test (come descritto di seguito).

Al fine di evitare confusione con i client (descritti nella prossima sottosezione), è consigliabile che il suo codice sia contenuto in una gerarchia di pacchetti, ad esempio nel pacchetto borsanova, i cui sorgenti dovranno essere nella directory src/main/java/borsanova.

Le classi client
Secondo quanto illustrato nelle istruzioni, per guidarla nel processo di sviluppo, in src/main/java/clients/ le sono forniti alcuni scheletri di test sotto forma di "client" di cui lei dovrà produrre una implementazione (secondo le specifiche che troverà nella documentazione contenuta nel sorgente di ciascuno di essi) facendo uso delle classi della sua soluzione; per ciascun client, nella sottodirectory di tests/clients/ avente nome uguale a quello della classe client, si trovano un corredo di file di input e output che le permetteranno di verificare il corretto funzionamento del suo codice.

Faccia attenzione al fatto che i client hanno non a caso questo nome: il loro compito è usare le classi da lei implementate per svolgere il loro compito, dovrebbero contenere poco e semplice codice al loro interno, praticamente solo quello strettamente necessario a leggere i dati di input, istanziare ed esercitare le classi da lei implementate e quindi per scrivere i dati di output ottenuti tramite di esse.

Si ricorda che il progetto non sarà valutato a meno che tutti i test svolti tramite il comando gradle test diano esito positivo .
