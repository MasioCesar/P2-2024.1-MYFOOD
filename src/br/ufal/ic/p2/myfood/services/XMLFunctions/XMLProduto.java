package br.ufal.ic.p2.myfood.services.XMLFunctions;

import br.ufal.ic.p2.myfood.models.Produto;
import br.ufal.ic.p2.myfood.services.DBManager;

import java.util.Map;

public class XMLProduto {
    private static final String FILE_NAME = "produtos.xml";
    private static final DBManager DBManager = new DBManager(FILE_NAME);

    public static void save(Map<Integer, Map<Integer, Produto>> produtos) {
        DBManager.save(produtos);
    }

    @SuppressWarnings("unchecked")
    public static Map<Integer, Map<Integer, Produto>> load() {
        return (Map<Integer, Map<Integer, Produto>>) DBManager.load();
    }
}
