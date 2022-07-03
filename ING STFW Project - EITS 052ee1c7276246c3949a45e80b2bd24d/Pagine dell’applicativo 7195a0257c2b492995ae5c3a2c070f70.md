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