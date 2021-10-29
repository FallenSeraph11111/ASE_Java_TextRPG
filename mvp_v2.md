Alle () inhalte sind optional und nicht direkt für den mvp notwendig, es wird aber im design der anwendung darauf geachtet das diese im nachhinein noch eingeführt werden können

- Equipment
    - (Stat beinflussung durch ausgerüstete gegenstände)
    - 3 (6) Slots
        - Rüstung [reduziert genommenen schaden]
        - (Helm)
        - Waffe [ bestimmt zu machenden schaden]
        - Schild [ erhöht chance das blocken erfolgreich ist / senkt genommenen schaden / reduziert schaden den man macht/ (verhindert ausrüsten von zweihändigen waffen]
        - (Ring 1)
        - (Ring 2)
   
- Charcter Ersteller
    - Namen festlegen
    - (Stats nach point buy prinzip verteilen)
        - (Str) [bonus für stärke waffen]
        - (Dex) [bonus für geschickwaffen]
        - Con [lebensmenge]
        - (Int) [bonus für magiewaffen]
        - (Wis) [für MVP nutzloser stat]
        - (Cha) [bonus % bei händler]
- Encountern mit NPC
    - bsp.: (Händler), Lore person
    Konversation, im mvp speichert es nicht was schon gefragt wurde (standart frage antwort dialog mit optionen, keine mehrebnigen konversationen immer nur eine frage -> anbtwort ->wieder am angfang der Konversation
    (Händler -> menü zum verkaufen von equipment im inventar -> spieler erhält gold)
    (Händler -> menü zum kaufen von equipment aus fester liste von dem Händler-> landet im Inventar)
- Dungeons:
    - Pro Raum ein encounter (genauer unter encounter)
    - Mehrere Rüume zwischen denen durch spieler input gewechselt werden kann
    - am ende des Dungeons boss (bessere Stats, stärkere Angriffe)
 - Encounter
    - Kiste
        - Loot -> lootscreen
        - Gegner -> kampf
    - (Schalter) [öffnet weg im Dungeon]
    - Gegner [bei MVP nur 1 gegner pro encounter] -> kampf
    - NPC
- Kampf
    - Round based [im MVP ist die reihenfolge nicht statbasiert sondern statisch]
    - im MVP hat spieler nur option anzugreifen oder zu blocken
        - Blocken reduziert schaden basiert auf equipment und (stats & mit glück garkein schaden) 
        - Angreifen -> schaden basiert auf waffe( mit stats als modifier) -> angriffe des spielers treffen immer ( stattdesen trefferwahrscheinlichkeit)
    - Gegner hat selbe optionen wie spieler [Entscheidung ob blockt oder angreift basiert auf ranom zahl im MVP -> fake random chance zu blocken / anzugreifen]
- Gegner
    - 1 boss
    - 2 standart gegner
    - 1 kisten gegner
    - Spieler muss gegner besiegen um weiter zu kommen
- Loot
    - Objekte die in der Item Datei sind -> für mvp 2 arten jedes Equipments und 2 Tiers an waffen jeder art
    - spieler hat option loot nicht mitzunehmen, oder ins Inventar zu bekommen
- Test Inhalt
    - Dateiformat: json
    - ein dungeon hat eine eigene Datei
        -auflistung der Räume mit art des encounters als attribute für die räume
        Im falle von NPC als encounter -> id aus npc datei
        gegner als encounter -> id aus gegner datei
    - Gegner Datei
        id des gegners
        stats
        name
        loot -> item ids
    - Npc Datei
        dict aller npc, key = id value=dict
            auflistung der conversation als dict key=spielerauswahl, value=antwort
            falls trader shop dict item id -> price
    - Item Datei
        dict item id-> dict
            item name
            equipment type
                stats (dict of values for each player stat for amount that it changes it)
- (Spiel Speicher)
    encodieren des spielerstats ,Inventars und Equipments anhand der jeweiligen ids(oder werte im fall der Spielerstats in einen String mit teilungszeichen zwischengestellt
    vortschritt im dungeon wird pro dungeon durch eine liste geclearter räume anhand der raum ids in einen String konvertiert (umgelegt schalter bei encountern werden zusätlich in eine zweite getrennte liste zu einem string konvertiert)
    Das ganze wird als datei abgespeichert -> im MVP wird die intergrität des files nicht überprüft -> änderungen die der spieler an dem file macht werden nicht abgefangen
