**SEMESTRÁLNÍ PRÁCE A + B + C**

- _Maximální možný bodový zisk za A: 4 body_ 
- _Maximální možný bodový zisk za B: 4 body_ 
- _Maximální možný bodový zisk za C: 3 body_ 


**_KST/BDATS: Datové struktury, Univerzita Pardubice - Fakulta Elektrotechniky a Informatiky_**

**Made by: Jiří Král - st67053@upce.cz [ZS-2024]**

- _A: získáno 3,5 bodu, nicméně primárně hodnocena architektura projektu._
- _B: získáno 3,5 bodu, hodnocen strom a základní práce s ním._
- _C: **Work in progress**, nicméně ideální postup by byl získat více jak 2 body = zápočet již před semestrální prací D._


____
### Semestrální práce A
**A)** Motivační příklad:

V rámci personální agendy jsou uchovávány statistické informace o počtu obyvatel
(muži/ženy) pro jednotlivé obce, přičemž jsou obce logicky organizovány do jednotlivých
krajů. Finální implementace bude realizována jako pole abstraktních lineárních seznamů.

**B)** Použité datové struktury:

V rámci modulu ABSTRDOUBLELIST implementujte abstraktní datovou strukturu (ADS)
obousměrně cyklicky zřetězený lineární seznam v dynamické paměti (stylizovaně
znázorněný v rámci obr. 1). Tato třída implementuje rozhraní IAbstrDoubleList,
které implementuje implicitní rozhraní Iterable. Rozhraní IAbstrDoubleList je
definováno následovně:

1) základní ovládání
- void zrus()-zrušení celého seznamu,
- boolean jePrazdny()-test naplněnosti seznamu,
- void vlozPrvni(T data)-vložení prvku do seznamu na první místo
- void vlozPosledni(T data)-vložení prvku do seznamu na poslední místo,
- void vlozNaslednika(T data)-vložení prvku do seznamu jakožto následníka
aktuálního prvku,
- void vlozPredchudce(T data)-vložení prvku do seznamu jakožto předchůdce
aktuálního prvku,

2) zpřístupnění seznamu
- T zpristupniAktualni()-zpřístupnění aktuálního prvku seznamu,
- T zpristupniPrvni()-zpřístupnění prvního prvku seznamu,
- T zpristupniPosledni()-zpřístupnění posledního prvku seznamu,
- T zpristupniNaslednika()-zpřístupnění následníka aktuálního prvku,
- T zpristupniPredchudce()-zpřístupnění předchůdce aktuálního prvku,
Pozn. Operace typu zpřístupni, přenastavují pozici aktuálního prvku

3) odebírání prvků
- T odeberAktualni()-odebrání (vyjmutí) aktuálního prvku ze seznamu poté je
aktuální prvek nastaven na první prvek
- T odeberPrvni()-odebrání prvního prvku ze seznamu,
- T odeberPosledni()-odebrání posledního prvku ze seznamu,
- T odeberNaslednika()-odebrání následníka aktuálního prvku ze seznamu,
- T odeberPredchudce()-odebrání předchůdce aktuálního prvku ze seznamu,
- Iterator<T> iterator() -vytvoří iterátor (dle rozhraní Iterable)


![obrazek](https://github.com/user-attachments/assets/0cbc4e7f-ac53-4532-a7b8-52b027828316)

Abstraktní lineární seznam slouží pro uchovávání jednotlivých obcí v daném kraji. Dále
bude aplikace využívat datovou strukturu pole, které bude reprezentovat jednotlivé kraje a
fixovat tak reference na jednotlivé obce v kraji, viz obr 2.

![obrazek](https://github.com/user-attachments/assets/9c310ae8-131b-455d-92e7-772ff43eb8c2)

**C)** Ověření funčnosti:

Pro ověření funkčnosti implementovaných ADS vytvořte modul Obyvatele. Tento modul
umožnuje správu seznamů obcí a implementuje následující rozhraní:


• int importData(String soubor) – provede import dat z datového souboru
kraje.csv, kde číslo kraje odpovídá indexu pole-1. Návratová hodnota přestavuje počet
úspěšně načtených záznamů.


• void vlozObec(Obec obec, enumPozice pozice, enumKraj kraj) -
vloží novou obec do seznamu obcí na příslušnou pozici (první, poslední, předchůdce,
následník), v odpovídajícím kraji

• Obec zpristupniObec(enumPozice pozice, enumKraj Kraj) -
zpřístupní obec z požadované pozice (první, poslední, předchůdce, následník, aktuální),
v odpovídajícím kraji

• Obec odeberObec(enumPozice pozice, enumKraj Kraj) - odebere
obec z požadované pozice (první, poslední, předchůdce, následník, aktuální),
v odpovídajícím kraji

• float zjistiPrumer(enumKraj Kraj) – zjistí průměrný počet obyvatel
v kraji, pokud je hodnota kraje rovna null, pak je průměr spočítán pro všechny kraje.

• Obec[]zobrazObce(enumKraj Kraj) – pomocí iterátoru provede výpis obcí
v daném kraji, pokud je hodnota kraje rovna null, pak jsou vypsány všechny kraje.
Alternativně může metoda vracet IAbstrDoubleList

• Obec[]zobrazObceNadPrumer(enumKraj Kraj) – pomocí iterátoru provede
výpis obcí, které mají v daném kraji nadprůměrný počet obyvatel. Pokud je hodnota kraje
rovna null, pak je průměr spočítán pro všechny kraje. Alternativně může metoda vracet
IAbstrDoubleList

• void zrus(enumKraj Kraj) – zruší všechny obce v kraji. Pokud je hodnota
kraje rovna null, pak zruší všechny obce.

![obrazek](https://github.com/user-attachments/assets/8504e73c-01a1-43e6-9127-3d9a6c5bcabb)

Modul Obec pracuje s typem
  - PSC
  - Obec
  - Počet mužů
  - Počet žen
  - celkem

**D)** GUI:

Pro obsluhu aplikace vytvořte uživatelské formulářové rozhraní ProgObyvatele, které
umožňuje obsluhu programu a volat požadované operace.
Zmíněný program, nechť umožňuje zadávání vstupních dat z klávesnice, ze souboru a
z generátoru, výstupy z programu nechť je možné zobrazit na obrazovce a uložit do souboru.

___
### Semestrální práce B

**E)** Motivační příklad:

V rámci **rozšíření semestrální práce A** vybudujte vyhledávací strukturu, která bude
obsahovat obce všech krajů. Vyhledávání bude realizováno podle klíče, název obce.

**F)** Použité datové struktury:

Abstraktní datová struktura umožňující vyhledávání dle klíče je realizována jako binární
vyhledávací strom (BVS) v dynamické paměti (tabulka na binárním stromu).
Komponenta ABSTRTABLE pracuje s generickým prvkem
 - K extends Comparable<K>, V (K-key,V-value) a implementuje rozhraní:
 - void zrus() zrušení celé tabulky
 - boolean jePrazdny() test prázdnosti tabulky
 - V najdi(K key) vyhledá prvek dle klíče
 - void vloz(K key, V value) vloží prvek do tabulky
 - V odeber(K key) odebere prvek dle klíče z tabulky
 - Iterator vytvorIterator (eTypProhl typ)vytvoří iterátor, který umožňuje
 - procházení stromu do šířky/hloubky (in-order)


Iterátor využívá ADS zásobník/fronta (ABSTRLIFO/ ABSTRFIFO) postavenou nad ADS
ze semestrální práce A (jako nová samostatná třída)
 - void zrus() zrušení celé fronty/zásobníku
 - boolean jePrazdny() test prázdnosti
 - void vloz(T data) vloží prvek do zásobníku/fronty
 - T odeber() odebere prvek ze zásobníku/fronty
 - Iterator vytvorIterator vrací iterátor zásobníku/fronty

**G)** Nové rozhraní pro ověření implementace:

Pro ověření funkčnosti implementovaných ADS vytvořte modul AgendaKraj. Tento
modul implementuje rozhraní odpovídající následující operacím:
 - Najdi/Vloz/Odeber() vyhledání/vložení/odebrání obce
 - Vybuduj() vybuduje BVS tak, aby byl strom vyvážený (na
 - základně znalosti dimenze prvků a jejich klíčů)
 - VytvorIterátor() vrací iterátor tabulky
 - Generuj umožnuje generovat jednotlivé obce

**H)** Rozšíření GUI:

Pro obsluhu aplikace vytvořte uživatelské formulářové rozhraní ProgAgendaKraj, která
umožňuje obsluhu programu a volat operace agendy kraje.

ProgAgendaKraj nechť dále umožňuje zadávání vstupních dat z klávesnice, ze souboru a
z generátoru, výstupy z programu nechť je možné zobrazit na obrazovce a uložit do souboru.

___
### Semestrální práce C

**CH)** Motivační příklad:

Rozšiřte semestrální práci A/B o prioritní frontu. Priorita bude definována (i) počtem
obyvatel nebo (ii) názvem obce a bude ji možné měnit za běhu aplikace.

**I)** Použité datové struktury:

Abstraktní datová struktura umožňující vyhledávání dle klíče je realizována jako binární
vyhledávací strom (BVS) v dynamické paměti (tabulka na binárním stromu).

Komponenta ABSTRTABLE pracuje s generickým prvkem
K extends Comparable<K>, V (K-key,V-value) a implementuje rozhraní:

 - void zrus() zrušení celé tabulky
 - boolean jePrazdny() test prázdnosti tabulky
 - V najdi(K key) vyhledá prvek dle klíče
 - void vloz(K key, V value) vloží prvek do tabulky
 - V odeber(K key) odebere prvek dle klíče z tabulky
 - Iterator vytvorIterator (eTypProhl typ)vytvoří iterátor, který umožňuje procházení stromu do šířky/hloubky (in-order)


Iterátor využívá ADS zásobník/fronta (ABSTRLIFO/ ABSTRFIFO) postavenou nad ADS ze semestrální práce A (jako nová samostatná třída)
 - void zrus() zrušení celé fronty/zásobníku
 - boolean jePrazdny() test prázdnosti
 - void vloz(T data) vloží prvek do zásobníku/fronty
 - T odeber() odebere prvek ze zásobníku/fronty
 - Iterator vytvorIterator vrací iterátor zásobníku/fronty

**J)** Ověření funkčnosti:

Pro ověření funkčnosti implementovaných ADS vytvořte modul AgendaKraj. Tento
modul implementuje rozhraní odpovídající následující operacím:

 - Najdi/Vloz/Odeber() vyhledání/vložení/odebrání obce
 - Vybuduj() vybuduje BVS tak, aby byl strom vyvážený (na základně znalosti dimenze prvků a jejich klíčů)
 - VytvorIterátor() vrací iterátor tabulky
 - Generuj umožnuje generovat jednotlivé obce

**K)** GUI:

Pro obsluhu aplikace vytvořte uživatelské formulářové rozhraní ProgAgendaKraj, která
umožňuje obsluhu programu a volat operace agendy kraje.

ProgAgendaKraj nechť dále umožňuje zadávání vstupních dat z klávesnice, ze souboru a
z generátoru, výstupy z programu nechť je možné zobrazit na _obrazovce_ a uložit do _souboru_.
