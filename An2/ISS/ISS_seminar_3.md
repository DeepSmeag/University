# Generare de cod pe baza diagramelor de clasa UML

// Vorbim de domeniul asigurarilor auto

Concepte (de baza + alte asocieri):
- autovehicul (fundamental)
- persoana

### **Succedere / Asociere bidirectionala**
- persoana are referinta spre autovehicul
- si vehiculul are referinta spre persoana

### Scriem niste cod
```java

public class Autovehicul {
    private String nrInregistrare;
    private String model;
    private Integer anFabricatie;
    private Persoana proprietar;
    private Set<Persoana> soferi;
     public Autovehicul(String nrInregistrare, String model, Integer anFabricatie, Persoana proprietar) {
        this.nrInregistrare = nrInregistrare;
        this.model = model;
        this.anFabricatie = anFabricatie;
        this.proprietar = proprietar;
        this.soferi = new HashSet();
    }
}
```

```java

public class Persoana {
    private String CNP;
    private String nume;
    private Set<Autovehicul> autoD;
    private Set<Autovehicul> autoC;
    // sarim constructor, parametrii simpli sunt dati, ceilalti init cu new HashSet stuff
    // se mai fac diverse setCeva ca functii
}
```