package fr.univlyon1.m2tiw.tiw1.metier;

public class Film {
    private final String titre;
    //Langue du film (VO, VF...)
    private final String version;
    //La fiche du film sur Linked Movie Database
    private final String fiche;

    public Film(String titre, String version, String fiche) {
        this.titre = titre;
        this.version = version;
        this.fiche = fiche;
    }

    public String getTitre() {
        return titre;
    }

    public String getVersion() {
        return version;
    }

    public String getFiche() {
        return fiche;
    }
}
