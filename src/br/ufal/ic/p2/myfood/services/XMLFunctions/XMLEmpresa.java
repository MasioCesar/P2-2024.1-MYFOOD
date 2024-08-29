package br.ufal.ic.p2.myfood.services.XMLFunctions;

import br.ufal.ic.p2.myfood.models.entidades.Empresa;
import br.ufal.ic.p2.myfood.services.DataPersistenceManager;

import java.util.Map;

public class XMLEmpresa {
    private static final String FILE_NAME = "empresas.xml";
    private static final DataPersistenceManager DataPersistenceManager = new DataPersistenceManager(FILE_NAME);

    public static void save(Map<Integer, Empresa> empresas) {
        DataPersistenceManager.save(empresas);
    }

    @SuppressWarnings("unchecked")
    public static Map<Integer, Empresa> load() {
        return (Map<Integer, Empresa>) DataPersistenceManager.load();
    }
}
