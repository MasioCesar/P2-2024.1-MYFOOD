package br.ufal.ic.p2.myfood.services.XMLFunctions;

import br.ufal.ic.p2.myfood.models.entidades.Produto;
import br.ufal.ic.p2.myfood.services.DataPersistenceManager;

import java.util.Map;

public class XMLProduto {
    private static final String FILE_NAME = "produtos.xml";
    private static final DataPersistenceManager DataPersistenceManager = new DataPersistenceManager(FILE_NAME);

    public static void save(Map<Integer, Map<Integer, Produto>> produtos) {
        DataPersistenceManager.save(produtos);
    }

    @SuppressWarnings("unchecked")
    public static Map<Integer, Map<Integer, Produto>> load() {
        return (Map<Integer, Map<Integer, Produto>>) DataPersistenceManager.load();
    }
}
