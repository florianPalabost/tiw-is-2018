package fr.univlyon1.m2tiw.tiw1;

import java.util.HashMap;
import java.util.Map;

public class AnnuaireImpl implements Annuaire {

    private Map<String, Object> store = new HashMap<>();

    private boolean isCompositePath(String path) {
        return path.contains("/");
    }

    private String getFirstDir(String path) {
        return path.substring(0, path.indexOf("/"));
    }

    private String getRemainder(String path) {
        return path.substring(path.indexOf("/") + 1);
    }

    @Override
    public void add(String path, Object value) {
        if (isCompositePath(path)) {
            String fd = getFirstDir(path);
            Annuaire a = (Annuaire) store.get(fd);
            if (a == null) {
                a = new AnnuaireImpl();
                store.put(fd, a);
            }
            a.add(getRemainder(path), value);
        } else {
            store.put(path, value);
        }
    }

    @Override
    public Object get(String path) {
        if (isCompositePath(path)) {
            Annuaire a = (Annuaire) get(getFirstDir(path));
            return a != null ? a.get(getRemainder(path)) : null;
        } else {
            return store.get(path);
        }
    }
}
