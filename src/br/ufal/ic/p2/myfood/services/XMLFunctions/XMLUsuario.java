package br.ufal.ic.p2.myfood.services.XMLFunctions;

import br.ufal.ic.p2.myfood.models.entidades.Usuario;
import br.ufal.ic.p2.myfood.services.DataPersistenceManager;

import java.util.Map;

public class XMLUsuario {
    private static final String FILE_NAME = "usuarios.xml";
    private static final DataPersistenceManager DataPersistenceManager = new DataPersistenceManager(FILE_NAME);

    public static void save(Map<String, Usuario> usuarios) {
        DataPersistenceManager.save(usuarios);
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Usuario> load() {
        return (Map<String, Usuario>) DataPersistenceManager.load();
    }
}
