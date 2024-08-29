package br.ufal.ic.p2.myfood.services.XMLFunctions;

import br.ufal.ic.p2.myfood.models.Usuario;
import br.ufal.ic.p2.myfood.services.DBManager;

import java.util.Map;

public class XMLUser {
    private static final String FILE_NAME = "usuarios.xml";
    private static final DBManager DBManager = new DBManager(FILE_NAME);

    public static void save(Map<String, Usuario> usuarios) {
        DBManager.save(usuarios);
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Usuario> load() {
        return (Map<String, Usuario>) DBManager.load();
    }
}
