# Architettura

Il progetto consiste in una Web Application che integra sia le funzioni di Back-End e Front-End

# Front-End

**Angular** è il framework scelto per lo sviluppo lato Front-End dell’applicativo ed è stato utilizzato in combinazione con Typescript. Questo è un framework *component-based* creato per sviluppare applicazoni web, librerie e strumenti per developer, esso si fonda appunto sul concetto di *Components* che possono essere visti come vari blocchi che uniti formano la nostra applicazione. Un Component include: una classe TypeScrpt con un @Component() decorator, un Template HTML, e uno style CSS.

La forza di Angular risiede proprio in questo concetto, in quanto un *Component* può essere riutilizzato più volte nello stesso progetto, ottimizzando e alleggerendo la struttura del progetto stesso.

[Esempio di riutilizzo di un component](Architettura%207b65f196ca28428594d5d6af84d5af41/Esempio%20di%20riutilizzo%20di%20un%20component%20a795e2fc9e8c4713a98ef4e1e4772018.md)

Per una visualizzazione accattivante delle pagine create mi sono affidato ad **Angular Material,** una libreria di componenti per la User Interface (UI) che si può utilizzare in un progetto Angular per importare interfacce eleganti e costantemente aggiornate

Per la creazione e interazione con le mappe presenti nell’applicazione la scelta è ricaduta su **Leaflet**, ovvero una libreria Javascript che permette di mostrare mappe , punti d interesse aree e strutture rappresentati come file GeoJson

In seguito alcun aspetti fondamentali di Angular utilizzati per creare l’applicativo: 

[Routing](Architettura%207b65f196ca28428594d5d6af84d5af41/Routing%20009dfe510ccc4ed28f277f5cee87077d.md)

[Interceptor](Architettura%207b65f196ca28428594d5d6af84d5af41/Interceptor%20c68cb32b913b446fbb9b8fce5e2f3a26.md)

[Guard](Architettura%207b65f196ca28428594d5d6af84d5af41/Guard%200038b167d89b46ca877ef91a1eb27988.md)

# Back-End

Lato Back-End ho optato per l’utilizzo di **SpringBoot,** estensione di **Spring** che rimuove alcuni aspetti del precedente creando un ecosistema dinamico e rapido perfetto per la creazione di applicazioni web utilizzando codice Java.

 I dati e le informazioni sono contenute in un database **SQL (MariaDB)**, gestito tramite **Spring JPA** e **Hibernate**.

Per quanto riguarda l’ambito sicurezza, ho utilizzato il modulo apposito **Spring Security**, grazie al quale ho potuto gestire autenticazione e controllo degli accessi alla piattaforma. Spring Security è altamente personalizzabile e modulare, dunque era la soluzione perfetta per il mio progetto. 

Ho strutturato il codice in modo tale da suddividere in diversi package i componenti più importanti, ossia:

- **Model**, dove vengono inserite tutte le classi che mappano le entità presenti nel Database
- **Controller**, in cui ci sono tutte le API suddivise in base alle entità e alle funzionalità che l’applicativo fornisce
- **Service**, un layer intermedio tra i controller e le repository, dove viene scritta la logica di business di ciascuna chiamata
- **Repository**, dove vengono scritte e strutturate le query da eseguire per le operazioni CRUD (Create, Read, Update, Delete) lato DB, in aggiunta alle query “dedicate” a particolari funzionalità.