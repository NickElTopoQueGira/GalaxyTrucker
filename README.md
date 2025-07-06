# Galaxy Trucker Game
Progetto tema d'esame di programmazione ad oggetti 2024-2025 ing. Informatica UniBg

Il progetto è stato creato dal gruppo 9, formato dai seguenti membri:
- Matteo Poli
- Niccolò Sesana
- Andriy Shvets 

"L'unico gioco di strategia dove non conta vincere, ma conta non perdere!".cit

Link al regolamento del gioco: https://www.craniocreations.it/prodotto/galaxy-trucker 

# Gestione del progetto

## Scaricare il progetto:

Per scaricare il progetto per lavorarci al meglio, è consigliabile utilizzare l’applicazione di GitHub desktop. 

Dopo averla installata ed eseguito il login, si può procedere a scaricare il repository (per non avere strani problemi è consigliato lasciare la posizione di default).

## Aprire il progetto su Eclipse

Aprire il vostro IDE del cuore per Java, e fare:

1. File → Import
2. General → Existing Projects into Workspace
3. In select root directory dovete mettere la posizione di dove si trova il progetto scaricato da GitHub e specificare la cartella del progetto

## Apportare modifiche al progetto

<aside>
⚠️

Prima di apportare modifiche, controllare sempre di lavorare sull’ultima versione

</aside>


# Impostazione del progetto

Il gioco è stato sviluppato interamente in ambiente Java, utilizzando il paradigma di programmazione ad oggetti.

<aside> ⚠️

Il gioco non è fornito di interfaccia grafica, ma solo di quella testuale.

</aside>

## Dettagli tecnici

Il progetto è stato creato utilizzando la versione di Java 21-LTS, per poi aggiornarsi alla 24.

### Strumenti utilizzati:

- Eclipse IDE - for Java developers: Ambiente di programmazione integrato, utilizzato per scrivere il codice e per eseguire il debug dello stesso.
- Git: Programma utilizzato per tenere la cronologia delle modifiche
- GitHub: Utilizzato per sincronizzare il progetto con la cronologie delle modifiche ai vari componenti del team.

### Debug:

Il progetto è stato testato a mano, attraverso l’utilizzo di main temporanei poi rimossi e anche attraverso il main ufficiale del programma.

NON sono state utilizzate unità di test automatiche.

## Struttura del Gameplay

Per quanto riguarda il gameplay, si è cercato di mantenere il senso e lo scopo del gioco originale, attuando, dove necessario, piccole variazioni alla struttura del gameplay dovute al formato virtuale.

I giocatori inizialmente possono scegliere alcuni parametri di configurazione della partita, il proprio nome ed il colore della pedina con cui intraprendere il proprio viaggio intergalattico. 

Sono state implementate 2 modalità:
- Modalità trasvolata singola: in cui i giocatori svolgono solo un livello dei tre presenti.
- Modalità trasvolata multipla: in cui i giocatori intraprendono un’avventura in tutti e tre i livelli in ordine crescente.
Come nel gioco ufficiale, sono state rispecchiate le caratteristiche e le specificità dei tre livelli come: dimensione della nave e composizione dei mazzi delle carte evento.

## Principali oggetti

Dal gioco fisico, abbiamo ricreato i seguenti oggetti, mentre altri li abbiamo ideati noi per il corretto funzionamento del gameplay:

- Giocatore -> Utente umano
- Configurazione -> Classi di configurazione delle navi, giocatori e partita
- Tabellone → il tabellone dove si svolge la fase secondaria della partita
- Pedina → Identificatore del giocatore sul tabellone di gioco
- Carte → carte azione
- Tessera → Componenti necessari per costruire la nave(con relative classe concrete: tessere più specifiche)
- Nave → Nave di gioco, nella quale vengono posizionate le tessere. Sono presenti poi le relative classi concrete NaveLvl1, NaveLvl2 e NaveLvl3
- Troncamento → Oggetto della nave che contiene effettivamente le tessere
