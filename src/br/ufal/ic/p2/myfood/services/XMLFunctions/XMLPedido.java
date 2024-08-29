package br.ufal.ic.p2.myfood.services.XMLFunctions;

import br.ufal.ic.p2.myfood.models.Pedido;
import br.ufal.ic.p2.myfood.services.DBManager;

import java.util.Map;

public class XMLPedido {
    private static final String FILE_NAME = "pedidos.xml";
    private static final DBManager DBManager = new DBManager(FILE_NAME);

    public static void save(Map<Integer, Pedido> pedidos) {
        DBManager.save(pedidos);
    }

    @SuppressWarnings("unchecked")
    public static Map<Integer, Pedido> load() {
        return (Map<Integer, Pedido>) DBManager.load();
    }
}
