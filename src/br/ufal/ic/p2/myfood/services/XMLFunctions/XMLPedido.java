package br.ufal.ic.p2.myfood.services.XMLFunctions;

import br.ufal.ic.p2.myfood.models.entidades.Pedido;
import br.ufal.ic.p2.myfood.services.DataPersistenceManager;

import java.util.Map;

public class XMLPedido {
    private static final String FILE_NAME = "pedidos.xml";
    private static final DataPersistenceManager DataPersistenceManager = new DataPersistenceManager(FILE_NAME);

    public static void save(Map<Integer, Pedido> pedidos) {
        DataPersistenceManager.save(pedidos);
    }

    @SuppressWarnings("unchecked")
    public static Map<Integer, Pedido> load() {
        return (Map<Integer, Pedido>) DataPersistenceManager.load();
    }
}
