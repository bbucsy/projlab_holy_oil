
**Ötlet**, kéne egy statikus osztály, ahol ember számára olvasható nevekkel hivatkozhatunk objektumokra (inverz Logger)

# Játék irányító parancsok
ami már az MVC controllernek szól 
tehát már figyelembe veszi, hogy egy körben csak egy akciója van egy játékosnak

* `do <settlerid> <method> <param>`

    methods:
	- `LookAround` -> Kiírunk minden infót, amihez egy játékos hozzáférne a végső játékban
	- `Move <asteroid name>` 
	- `Drill`
	- `Mine`
	- `PlaceResource <resource index>`
	- `PlaceTeleporter`
	- `Craft <robot | teleport>`
	
# "Pálya leíró parancsok"

* `create <object type> <name> <default values>` : Létrehoz és beregsiztrál egy objektumot.
	- Berci csinálhatna egy kis Reflection mágiát itt.
    - Alternatíva: Hardcode-oljuk az összes objektum típust

* `link <object name> <object name>` : Összeköt két objektumot a megfelelő metódushívással. Pl: Két aszteroidát a setNeighbourel, két Kaput a setPair-rel...
	- Szerintem ide is jöhet reflection feketemágia
    - Alternatívan a link típusát hardcode-olhatnánk :
	`link <link_type(e.g: ASTEROID_NEIGHBOURS)> <object1> <object2>`
	

# Működést változtató parancsok

* `step`
* `cause_sunstorm <starting asteroid>`
* `disable_random` Nem tudom mi a f*szt akarnak itt, mert nagyon sok helyen van random, és integrációs tesztek amúgy is készen vannak a skeletonban
* `explode_asteroid <asteroid>` ?? Talán kelleht ilyen

* `save/load` A modellt menti ki és tölti be..

# Kimeneti nyelv
Tudom hogy azt mondta ne xml legyen, de szerintem még mindíg egyszerűbb, mint saját kimeneti nyelvet definiálni