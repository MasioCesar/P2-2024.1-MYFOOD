package br.ufal.ic.p2.myfood.services.XMLFunctions;

import br.ufal.ic.p2.myfood.models.entidades.Empresa;
import br.ufal.ic.p2.myfood.services.DBManager;

import java.util.Map;

public class XMLEmpresa {
    private static final String FILE_NAME = "empresas.xml";
    private static final DBManager DBManager = new DBManager(FILE_NAME);

    public static void save(Map<Integer, Empresa> empresas) {
        DBManager.save(empresas);
    }

    @SuppressWarnings("unchecked")
    public static Map<Integer, Empresa> load() {
        return (Map<Integer, Empresa>) DBManager.load();
    }
}
