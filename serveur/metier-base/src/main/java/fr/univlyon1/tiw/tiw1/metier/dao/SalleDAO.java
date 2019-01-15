/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fr.univlyon1.tiw.tiw1.metier.dao;

import fr.univlyon1.tiw.tiw1.metier.beans.Salle;

import java.io.IOException;
import java.util.List;

public interface SalleDAO {
    public static final String CTX_METIER = "/metier";
    public static final String CONTEXT = "salles";

    public List<Salle> load() throws IOException;

}
