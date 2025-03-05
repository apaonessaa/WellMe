# WellMe - App di Salute e Benessere

## Descrizione
WellMe è un'applicazione mobile progettata per aiutare gli utenti a monitorare, analizzare e gestire la propria attività fisica e il proprio benessere mentale. Attraverso l'uso di sensori e funzionalità context-aware, WellMe fornisce suggerimenti personalizzati per migliorare la consapevolezza delle proprie abitudini e promuovere uno stile di vita sano.

## Obiettivi
- **Monitoraggio attività fisica**: registrazione automatica di passi, calorie bruciate e tempo di inattività.
- **Monitoraggio benessere mentale**: registrazione dello stato d'animo giornaliero con analisi della correlazione tra attività fisica e benessere emotivo.
- **Suggerimenti personalizzati**: notifiche e raccomandazioni basate sul contesto e sulle abitudini dell'utente.
- **Archiviazione dati**: visualizzazione della cronologia delle attività svolte e degli stati d'animo registrati.

## Features
- **Monitoraggio salute fisica**: utilizzo di sensori per rilevare il numero di passi e il tipo di attività svolta (camminata, corsa, ecc.).
- **Monitoraggio benessere mentale**: interfaccia dedicata per registrare il proprio umore con opzioni predefinite e commenti personalizzati.
- **Suggerimenti context-aware**:
  - Promemoria per ridurre i periodi di inattività.
  - Raccomandazioni su attività rilassanti o energizzanti.
  - Attività consigliate in base alle abitudini settimanali.
- **Interfaccia intuitiva e user-friendly** con statistiche e report dettagliati.

## Struttura dell'Applicazione
L'applicazione è composta dalle seguenti sezioni principali:
- **MainActivity**: punto di ingresso principale dell'app.
- **FocusActivity**: gestione delle attività monitorate.
- **MoodActivity**: registrazione dello stato d'animo.
- **ViewItemActivity**: visualizzazione dei dettagli di un'attività registrata.
- **StatsFragment**: riepilogo dei dati raccolti.
- **ProfileFragment**: gestione del profilo utente.

### Database e Gestione Dati (ROOM)
- **Entities**: ActivityStat, MoodStat
- **DAO (Data Access Objects)**: ActivityStatsDao, MoodStatsDao
- **ViewModels**: ActivityViewModel, MoodViewModel
- **Adapters**: ActivityAdapter, MoodAdapter
- **Database**: WellMeDatabase

## Funzionalità Context-Aware
L'applicazione utilizza dati ambientali e sensori per personalizzare i suggerimenti:
- **Rilevamento posizione**: suggerimenti basati sulla posizione dell'utente.
- **Rilevamento attività**: monitoraggio automatico tramite accelerometro.
- **Analisi abitudini settimanali**: suggerimenti basati su attività svolte in giorni specifici.

## Licenza
Questo progetto è distribuito sotto licenza MIT. Consulta il file `LICENSE` per maggiori dettagli.


