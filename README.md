[![Gitpod Ready-to-Code](https://img.shields.io/badge/Gitpod-Ready--to--Code-blue?logo=gitpod)](https://gitpod.idi.ntnu.no/#https://gitlab.stud.idi.ntnu.no/it1901/groups-2020/gr2038/gr2038/)

# Dinnerium  

![logo](http://folk.ntnu.no/anderobs/images/dinnerium.png "Our logo")  

Dette prosjektet er en del av emnet IT1901 gjennomført høsten 2020. Målet er å lage en trelagsapplikasjon med et domenelag, brukergrensesnitt og lagring. 
Man skal ha tester for alle lag, og prosjektet skal være konfigurert med maven. Bygget er rigget med jacoco plugin for å rapportere testdekningsgraden til prosjektet (mvn verify).
Det er også rigget opp med plugins for å sjekke kodekvalitet, samt for å se etter typiske bugs. Resultatet av sjekkene rapporteres inn til target mappen.

Vi ønsker å lage en applikasjon som skal hjelpe deg som bruker å planlegge middager. Dette skal skje gjennom en oversikt over varer man har tilgjengelig, 
samt oppskrifter man har brukt. Gjennom [brukerhistorie 2](brukerhistorier.md) vil man derfor kunne se varene sine og oppskrifter man tidligere har brukt og lagret
i applikasjonen. Applikasjonen skal til syvende og sist hjelpe deg å finne aktuelle oppskrifter basert på varene man allerede har, og bidra i planleggingen av ukesmenyen. 

<!-- Illustrerende skjermbilde (utkast av design) -->


## Innhold og organisering

Mappestrukturen til prosjektet er organisert følgende:
- **src/main/java** utgjør kodingsprosjektet. Videre har vi mapper for å skille koden som brukes til hva. 
- **src/main/resources** for ressurser som bilder, FXML-filer osv. 
- **src/test/java** for testkoden til applikasjonen
- **src/test/resources** for ressurser som brukes av testene.

## Prototype fra figma
![figma-prototype](http://folk.ntnu.no/anderobs/images/figmaProtoype1.png "The prototype")  

Vi jobber med et nytt design. Dette designet er rotete og fargene appelerer ikke, og kan heller ikke forbindes med mat. I det nye designet rydder vi opp i designet,  
men funksjonaliteten vil være den samme. Appen blir også mer intuitiv med det nye designet som dere kommer til å se når vi oppdaterer det i prod.  

## Applikasjonen
![App](http://folk.ntnu.no/anderobs/images/appInnlevering1.PNG "How the app looks in javaFX")    

Dette er hvordan applikasjonen ser ut i JavaFX nå, men vi kommer til å implementere det nye designet ved neste innlevering.




