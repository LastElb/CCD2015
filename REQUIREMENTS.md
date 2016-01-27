# Anforderungen

## Vorgeschriebene Anforderungen
* [x] Das Projekt http://sourceforge.net/projects/javaopenchess/ so erweitern, dass ein Dreipersonenschach spielbar ist
* [x]  Der Originalquellcode sollte am besten nach einem Refactoring weiter genutzt werden.
* [ ] ca. 50% Testcoverage in allen Modulen
 * [ ] Client
 * [x] Server
* [x] Codedokumentation in Doxygen
 * [x] (Bessere Dokumentation in Javadoc)
* [x] Testcoverage im Buildserver anzeigen
* [x] Sonarqube integrieren

## Unsere Anforderungen
* [x] Trennung der bisherigen Spiellogik vom Client in ein eigenes Serverpaket.
* [x] Server
 * [x] Kommunikation über REST-Schnittstellen und Websockets
 * [x] REST-Schnittstellen sollen so generisch sein, dass auch andere Spielmodi (neben Dreipersonenschach) gespielt werden können
 * [x] Spielmodi sollen ein von einem generischen Spiel erben.
* [x] Einen Client auf einer anderen Plattform erstellen, um Möglichkeiten der REST-Schnittstellen zu zeigen
* [x] Der ursprüngliche Javaclient soll nur noch das Spielfeld anzeigen und Zugbefehle an den Server schicken.
* [x] Die Zeichenfläche des Clients soll sich auch ohne Veränderung der Fenstergröße aktualisieren!
