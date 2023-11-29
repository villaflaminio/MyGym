# ING STFW Project - EITS

[Introduzione](ING%20STFW%20Project%20-%20EITS%20052ee1c7276246c3949a45e80b2bd24d/Introduzione%20beaf6c340ab141fa9ff70a3d83af19fa.md)

# Introduzione

EITS (Enterprise Infrastructure, Training and Strength Improvement) nasce come applicativo per semplificare la gestione di strutture di allenamento nella loro completezza.

Prendendo ispirazione da grosse multinazionali nell’ambito del fitness, ad esempio AnyTime Fitness o McFit, il progetto vuole agevolare la gestione di: abbonamenti, schede di allenamento e utenti di quelle piccole-medio imprese grazie ad un applicativo semplice ed accattivante.

## Descrizione del progetto

EITS fornisce funzionalità che permettono di avere sia all’atleta che al centro di allenamento tutti gli strumenti necessari per allenarsi o gestire il centro a portata di click!

Un atleta può registrarsi nella piattaforma e visualizzare i propri abbonamenti e le schede di allenamento assegnate dalla propria palestra, scaricando quest’ultima in formato PDF così da reperirla in qualsiasi momento sul proprio device.

Il centro di allenamento può visualizzare tutti i dati relativi alle strutture inserite, abbonamenti e schede di allenamento. Inoltre è presente una mappa che permette al centro di geolocalizare le strutture e visualizzare le informazioni principali.

[Architettura](ING%20STFW%20Project%20-%20EITS%20052ee1c7276246c3949a45e80b2bd24d/Architettura%207b65f196ca28428594d5d6af84d5af41.md)

# Architettura

Il progetto consiste in una Web Application che integra sia le funzioni di Back-End e Front-End


# Back-End

Lato Back-End ho optato per l’utilizzo di **SpringBoot,** estensione di **Spring** che rimuove alcuni aspetti del precedente creando un ecosistema dinamico e rapido perfetto per la creazione di applicazioni web utilizzando codice Java.

 I dati e le informazioni sono contenute in un database **SQL (MariaDB)**, gestito tramite **Spring JPA** e **Hibernate**.

Per quanto riguarda l’ambito sicurezza, ho utilizzato il modulo apposito **Spring Security**, grazie al quale ho potuto gestire autenticazione e controllo degli accessi alla piattaforma. Spring Security è altamente personalizzabile e modulare, dunque era la soluzione perfetta per il mio progetto. 

Ho strutturato il codice in modo tale da suddividere in diversi package i componenti più importanti, ossia:

- **Model**, dove vengono inserite tutte le classi che mappano le entità presenti nel Database
- **Controller**, in cui ci sono tutte le API suddivise in base alle entità e alle funzionalità che l’applicativo fornisce
- **Service**, un layer intermedio tra i controller e le repository, dove viene scritta la logica di business di ciascuna chiamata
- **Repository**, dove vengono scritte e strutturate le query da eseguire per le operazioni CRUD (Create, Read, Update, Delete) lato DB, in aggiunta alle query “dedicate” a particolari funzionalità.

[Pagine dell’applicativo](ING%20STFW%20Project%20-%20EITS%20052ee1c7276246c3949a45e80b2bd24d/Pagine%20dell%E2%80%99applicativo%207195a0257c2b492995ae5c3a2c070f70.md)

# Pagine dell’applicativo

L’applicativo consiste in una Web App che dispone diverse aree a seconda che l’utente sia autenticato o meno e in base al suo ruolo da fare meglio

### Area Pubblica

La pagina mostrata ad utente non autenticato consiste in una Landing-Page dove vi è spiegato in breve i servizi e le opportunità offerte dall’applicativo. 

Pagina **“Login”** permette ,mediante opportune API REST di autenticare l’utente e di reindirizzarlo all’area di competenza

 Pagina **“Registration”** permette ,mediante opportune API REST di registrare l’utente e di reindirizzarlo all’interfaccia

### Area Atleta

Pagina **“User Home”** permette di visualizzare i dati relativi agli abbonamenti e alle schede assegnate all’utente dalla palestra, dove mediante opportune API REST vi si possono visualizzare le seguenti funzioni:

- Visualizzare in breve i dati relativi all’utente (email, numero abbonamenti e schede)
- Visualizzare una tabella contente gli abbonamenti sottoscritti
- Filtrare i dati relativi alla tabella abbonamenti mediante nome e pagamento(se pagato o meno)
- Visualizzare una tabella contente le schede di allenamento assegnate
- Filtrare i dati relativi alla mediante il nome della scheda

Pagina **“Scheda Detail User”** permette di visualizzare in dettaglio le informazioni relative ad una determinata  scheda e agli esercizi associati alla stessa, dove mediante opportune API REST vi si possono visualizzare le seguenti funzioni:

- Visualizzare i dati principali della scheda
- Visualizzare gli esercizi mediante una tabella o a griglia a seconda dell’impostazione scelta
- Scaricare in formato **PDF** la scheda di allenamento assegnata

### Area Palestre

L’**Area Palestre** si apre con la pagina  **“Gym Home”** permette di visualizzare le informazioni principali riguardo le palestre ,dove mediante opportune API REST vi si possono visualizzare le seguenti funzioni:

- Ricerca Palestra per nome (cliccando su quest’ultima si sarà reindirizzati alla pagina di dettaglio)
- Visualizzazione di tutte le Palestre inserite (cliccando su quest’ultima si sarà reindirizzati alla pagina di dettaglio)
- Visualizzare una mappa dove sono geolocalizzate le palestre mediante dei “Marker”
- Aggiungere una Palestra
- Aggiungere un abbonamento

Pagina  **“Gym Detail”** permette di visualizzare in dettaglio le informazioni relative ad una determinata palestra, dove mediante opportune API REST vi si possono visualizzare le seguenti funzioni:

- Elimina/Modifica Palestra
- Visualizzare le informazioni principali della palestra
- Visualizzare la lista degli abbonamenti associati a questa palestra
- Eliminare/Inserire il nome del sensore associato alla palestra
- Visualizzare una mappa dove consultare l’indirizzo della palestra

Pagina **“Membership”** permette di visualizzare in breve i dati relativi agli abbonamenti sottoscritt**i,** dove mediante opportune API REST vi si possono visualizzare le seguenti funzioni:

- Aggiungere un abbonamento
- Visualizzare una tabella contenente i dati relativi degli abbonamenti
- filtrare i dati della tabella in base al nome e al pagamento (effettuato o meno)
- Eliminare/Modificare un abbonamento

Pagina **“Schede Allenamento”** permette di visualizzare i dati relativi alle schede e esercizi inseriti, dove mediante opportune API REST vi si possono visualizzare le seguenti funzioni:

- Aggiungere/Eliminare/Modificare/Visualizzare Schede di allenamento
- Aggiungere/Eliminare/Modificare/Visualizzare esercizi
- Visualizzare una tabella contente le Schede di allenamento
- Visualizzare una tabella contenente gli esercizi

Pagina **“Scheda Detail”** permette di visualizzare in dettaglio le informazioni relative ad una determinata  scheda e agli esercizi associati alla stessa, dove mediante opportune API REST vi si possono visualizzare le seguenti funzioni:

- Eliminare/Modificare Scheda di allenamento
- Modificare/Rimuovere esercizi associati alla scheda
- Filtrare gli esercizi presenti mediante il nome
- Visualizzare gli esercizi mediante una tabella o a griglia a seconda dell’impostazione scelta

Pagina **“Gym Users”** permette di visualizzare in breve i dati relativi agli  utenti iscritt**i**, dove mediante opportune API REST vi si possono visualizzare le seguenti funzioni:

- Aggiungere Utente
- Visualizzare una tabella contente i dati principali degli utenti
- Filtrare i dati dell’utente in base al nome, ruolo e abbonamento (attivo o meno)
- Aggiungere un abbonamento all’utente
- Visualizzare gli abbonamenti sottoscritti dall’utente (se presenti)

Infine per ogni Interfaccia dell’Area Palestre sono predisposti dei Dialog che permettono di effettuare le operazioni di Eliminazione/Modifica/Inserimento

[Descrizione API](ING%20STFW%20Project%20-%20EITS%20052ee1c7276246c3949a45e80b2bd24d/Descrizione%20API%20ed2f1af6ec904928888a921fdcb9c368.md)

[Class Diagram](ING%20STFW%20Project%20-%20EITS%20052ee1c7276246c3949a45e80b2bd24d/Class%20Diagram%206d6a32c877924dacb95b5e2569a8fe63.md)

# Class Diagram

Il Class Diagram mostra le classi che compongono il progetto e come queste si relazionano tra loro

![class_diagram-Eits.jpeg](ING%20STFW%20Project%20-%20EITS%20052ee1c7276246c3949a45e80b2bd24d/Class%20Diagram%206d6a32c877924dacb95b5e2569a8fe63/class_diagram-Eits.jpeg)

![2581a32e-af53-4ad4-b635-1f4756961212.jpg](ING%20STFW%20Project%20-%20EITS%20052ee1c7276246c3949a45e80b2bd24d/Class%20Diagram%206d6a32c877924dacb95b5e2569a8fe63/2581a32e-af53-4ad4-b635-1f4756961212.jpg)

La classe **User** contiene le informazioni degli utenti della piattaforma, ad ogni **User** è associato una List di **Abbonamento** e **SchedaAllenamento**.

La classe **Abbonamento** contiene le informazioni degli abbonamenti inserti in piattaforma, ad ogni **Abbonamento** è associato un **User** e una **Palestra**.

La classe **SchedaAllenamento** contiene le informazioni delle schede di allenamento della piattaforma, ogni **schedaAllenamento** è associata ad un **User** e ad una List di **Esercizio** 

La classe **Palestra** contiene le informazioni delle palestre presenti, ad ogni **Palestra** è associato una List di **Abbonamento**

La classe **Esercizio** contiene le informazioni agli esercizi inseriti dai Personal Trainer, ad ogni **Esercizio** è associato una List di  **SchedaAllenamento**

La classe **Sensore** contiene le informazioni al sensore presente in palestra,  ogni **Sensore** è associato una **Palestra**

Mentre per le relazioni tra le varie classi:

![WhatsApp Image 2022-06-19 at 18.23.13.jpeg](ING%20STFW%20Project%20-%20EITS%20052ee1c7276246c3949a45e80b2bd24d/Class%20Diagram%206d6a32c877924dacb95b5e2569a8fe63/WhatsApp_Image_2022-06-19_at_18.23.13.jpeg)

[Sequence Diagram](ING%20STFW%20Project%20-%20EITS%20052ee1c7276246c3949a45e80b2bd24d/Sequence%20Diagram%205a8b473b42e64f4587833789b741fe86.md)

# Sequence Diagram

Il Sequence Diagram in figura mostra come avviene  l’autenticazione nell’applicativo.

Gli Attori coinvolti sono Atleta e Amministratore, entrambi al momento del Login inseriscono le credenziali, username-password, le quali verranno mandate al Back-End.

Quest’ultimo, se le credenziali sono valide, risponderà con un idToken e l’oggetto User che conterrà le authorities per proseguire nelle pagne di merito (Palestra o Atleta).

Mentre se le credenziali non sono valide il Back-End risponderà con un Errore, di conseguenza all’User verrà mostratp un messaggio di errore.

![sequence diagram.png](ING%20STFW%20Project%20-%20EITS%20052ee1c7276246c3949a45e80b2bd24d/Sequence%20Diagram%205a8b473b42e64f4587833789b741fe86/sequence_diagram.png)

Come secondo esempio di Sequence Diagram viene illustrato in figura come avviene l’inserimento  di una nuova palestra da parte di un utente Admin

![Untitled](ING%20STFW%20Project%20-%20EITS%20052ee1c7276246c3949a45e80b2bd24d/Sequence%20Diagram%205a8b473b42e64f4587833789b741fe86/Untitled.png)

[Use Case Diagram](ING%20STFW%20Project%20-%20EITS%20052ee1c7276246c3949a45e80b2bd24d/Use%20Case%20Diagram%207a0eb8fe4b6c4fca9811960eb1d4427a.md)
# Use Case Diagram

Lo Use Case Diagram permette di visualizzare graficamente il comportamento del sistema, illustrandoci quali sono  requisiti funzionali.

L’attore coinvolto nello Use Case in figura è User, ovvero l’atleta iscritto in palestra, in dettaglio le funzionalità a cui può accedere

![Untitled](ING%20STFW%20Project%20-%20EITS%20052ee1c7276246c3949a45e80b2bd24d/Use%20Case%20Diagram%207a0eb8fe4b6c4fca9811960eb1d4427a/Untitled.png)

L’attore coinvolto in questo secondo Use Case è Admin, ovvero il gestore di palestre, in dettaglio le funzionalità a cui può accedere

![Untitled](ING%20STFW%20Project%20-%20EITS%20052ee1c7276246c3949a45e80b2bd24d/Use%20Case%20Diagram%207a0eb8fe4b6c4fca9811960eb1d4427a/Untitled%201.png)

[Testing](ING%20STFW%20Project%20-%20EITS%20052ee1c7276246c3949a45e80b2bd24d/Testing%20c073e54ba7354bd1ad319521a27fb440.md)

[Conclusioni](ING%20STFW%20Project%20-%20EITS%20052ee1c7276246c3949a45e80b2bd24d/Conclusioni%208bcd1d9a1a9f434983c0016954d94671.md)
# Conclusioni

In base alla mia esperienza nel settore, il progetto permette di “svecchiare” quei software gestionali utilizzati nell’ambito, grazie ad un’interfaccia semplice e intuitiva facilita il lavoro nelle strutture e alleggerisce i tempi di attesa dovuti all’iscrizione di un’atleta e alla gestione delle palestre stesse.

Tuttavia, con alcune migliorie il progetto sarà sicuramente pronto per essere lanciato sul mercato.

## Futuri sviluppi

Lato Back-End non ci sono migliorie significative da apportare mentre lato Front-End bisogna considerare alcune soluzioni  per rendere l’applicativo il più funzionale possibile.

L’aggiornamento cardine da apportare al progetto è quello di dividere completamente le modalità di utilizzo del progetto per l’Atleta e per il Gestore di Palestre.

Per l’Atleta le modalità di utilizzo non cambiano, in quanto l’utilizzo di un’applicazione Web risulta il modo più efficace per consultare  propri Abbonamenti e Schede di Allenamento, inoltre avendo la possibilità di scaricare quest’ultima direttamente sul proprio device permette di consultare gli esercizi in qualsiasi momento.

Per le Palestre invece la modifica da effettuare è quello di rendere l’applicativo una vera e propria applicazione Desktop. Questo può essere effettato senza stravolgere il progetto, utilizzando [**ElectronJs**](https://www.electronjs.org/), un framework utilizzato per costruire applicazioni Desktop utilizzando Javascript, HTML e CSS dove non è richiesta esperienza di sviluppo nel settore.

Per esperienza personale Electron mi ha colpito molto per la sua semplicità, poiché con uno script di codice Javascript da “attaccare” al codice Angular permette di creare Applicazioni Desktop  leggere e molto potenti.

[Creazione Desktop Applcation ](Conclusioni%208bcd1d9a1a9f434983c0016954d94671/Creazione%20Desktop%20Applcation%2072940f1fe83844579697fe816ff69110.md)

gitHub Front-End: https://github.com/salvatoreChiacchio0/ing-sftw-eits-fe
