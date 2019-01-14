package fr.univlyon1.tiw.tiw1.banque.data;

public class CompteInconnuException extends Exception {
    public CompteInconnuException() {
    }

    public CompteInconnuException(long idCompte) {
        this("Le compte "+idCompte+" n'est pas connu");
    }

    public CompteInconnuException(String s) {
        super(s);
    }

    public CompteInconnuException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public CompteInconnuException(Throwable throwable) {
        super(throwable);
    }

    public CompteInconnuException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
