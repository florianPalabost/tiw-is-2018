package fr.univlyon1.m2tiw.tiw1.metier;

public class Film {
    private final String titre;
    private final String version;

    public Film(String titre, String version) {
        this.titre = titre;
        this.version = version;
    }

    public String getTitre() {
        return titre;
    }

    public String getVersion() {
        return version;
    }
}
