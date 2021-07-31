

# ![5729950229_833346ff-a57c-4dd9-8a87-dc5973fa735a](https://user-images.githubusercontent.com/82155099/127607021-b8e350ef-0322-402f-9037-397e87cbbdb1.png)

## About the APP

Die App wurde im Rahmen einer Projektarbeit für das Modul Entwicklung mobiler Anwendungen <br>
an der Hochschule Worms von Nico Mai und Fabian Weinhold entwickelt.

Die Grundidee bestand darin einen einfach zu bedienenden Kalorien tracker zu implementieren.<br>
Zu der Grundidee kamen Features wie Wasser tracken, Graphen/Statistiken, User Profile, etc.<br>
<br>
Die aktuelle Version ist die 1.4 (https://github.com/gnamly/youarewhatyoueat/blob/master/YouAreWhatYouEat-1_4.apk)<br>

Mit YAWYE ist es möglich seine Kalorien jederzeit zu tracken.<br>
Man kann verschiedene Benutzerprofile anlegen, sodass die ganze Familie/Freunde mit einem Telefon getrackt werden können.<br>
Nach Eingabe von Größe & Gewicht berechnet die App den empfohlenen Tagesbedarf. (Aus Basis von https://www.tk.de/techniker/magazin/ernaehrung/uebergewicht-und-diaet/wie-viele-kalorien-pro-tag-2006758 )<br>
Dabei werden die gespeicherten Daten nur lokal auf dem Telefon gespeichert. <br>


## Funktionalität

- Benutzerprofile anlegen/löschen<br>
- Zwischen Benutzerprofilen wechseln<br>
- Farbe von Profilen ändern<br>
- Kalorien manuell eintragen<br>
- Graphen zu Kalorien anzeigen<br>
- Wassermenge eingeben <br> 
- Kalorien über Rezepte/Mahlzeiten eintragen (tbd.) <br>
- News Seite mit nützlichen Informationen (tbd.)
- Übersicht (Tag/Woche/Monat) (tbd.)

## Running the APP

- Clone the Project to your PC and open it in Android Studio
- Build the Project
- Run the App (Simulator - tested with Nexus 5X, Pixel 3A, Samsung Galaxy S10)

## Doku

## Technisch

Die App nutzt eine SQLite Datenbank.<br>
Für die Programmiersprache entschieden wir uns für Java.<br>

Die App nutzt Java 8, eine min SDK von  26 und eine Ziel SDK von 30  
Als externe Liberary nutzen wir noch https://github.com/PhilJay/MPAndroidChart für Graphen

Die App nutzt verschiedene Activities mit eingebundenen Fragments, dabei werden Menü Punkte in einzelnen Abschnitten erstellt.  
Die Kommunikation mit der Datenspeicherung in der Dantenbank funktioniert durch Database Helper klassen, welche teilweise als Singleton behandelt werden.

Die Datenbank struktur ist dem Entity Relation Diagramm zu entnehmen.

### Datenbank Modell
![grafik](https://user-images.githubusercontent.com/8366374/127735189-04171e41-1506-4b32-a476-8fc3eac03b08.png)

### Mockup

![YAWYE_mockup](https://user-images.githubusercontent.com/82155099/127733473-eb1b7761-096b-4670-b7a4-5d2039f4c4f7.PNG)

### Mindmap

![YAWYE_Map](https://user-images.githubusercontent.com/82155099/127733477-fc8a15e5-6762-48a9-a86c-06616e0973dc.PNG)

### Arbeitszeiten

[Arbeitszeiten EMA.xlsx](https://github.com/gnamly/youarewhatyoueat/files/6910873/Arbeitszeiten.EMA.xlsx)

### Präsentation

[YAWYE_Vorstellung.pptx](https://github.com/gnamly/youarewhatyoueat/files/6910874/YAWYE_Vorstellung.pptx)

### User Stories

[App User Stories.docx](https://github.com/gnamly/youarewhatyoueat/files/6910876/App.User.Stories.docx)


