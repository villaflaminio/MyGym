# Class Diagram

Il Class Diagram mostra le classi che compongono il progetto e come queste si relazionano tra loro

![class_diagram-Eits.jpeg](Class%20Diagram%206d6a32c877924dacb95b5e2569a8fe63/class_diagram-Eits.jpeg)

![2581a32e-af53-4ad4-b635-1f4756961212.jpg](Class%20Diagram%206d6a32c877924dacb95b5e2569a8fe63/2581a32e-af53-4ad4-b635-1f4756961212.jpg)

La classe **User** contiene le informazioni degli utenti della piattaforma, ad ogni **User** è associato una List di **Abbonamento** e **SchedaAllenamento**.

La classe **Abbonamento** contiene le informazioni degli abbonamenti inserti in piattaforma, ad ogni **Abbonamento** è associato un **User** e una **Palestra**.

La classe **SchedaAllenamento** contiene le informazioni delle schede di allenamento della piattaforma, ogni **schedaAllenamento** è associata ad un **User** e ad una List di **Esercizio** 

La classe **Palestra** contiene le informazioni delle palestre presenti, ad ogni **Palestra** è associato una List di **Abbonamento**

La classe **Esercizio** contiene le informazioni agli esercizi inseriti dai Personal Trainer, ad ogni **Esercizio** è associato una List di  **SchedaAllenamento**

La classe **Sensore** contiene le informazioni al sensore presente in palestra,  ogni **Sensore** è associato una **Palestra**

Mentre per le relazioni tra le varie classi:

![WhatsApp Image 2022-06-19 at 18.23.13.jpeg](Class%20Diagram%206d6a32c877924dacb95b5e2569a8fe63/WhatsApp_Image_2022-06-19_at_18.23.13.jpeg)