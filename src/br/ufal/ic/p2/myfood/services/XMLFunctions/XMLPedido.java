package br.ufal.ic.p2.myfood.services.XMLFunctions;

import br.ufal.ic.p2.myfood.models.Pedido;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class XMLPedido {
    private static final String PEDIDOS_FILE = "pedidos.xml";
    public static void savePedidos(Map<Integer, Pedido> pedidos) {
        try (XMLEncoder encoder = new XMLEncoder(new FileOutputStream(PEDIDOS_FILE))) {
            encoder.writeObject(pedidos);
        } catch (Exception ignored) {
        }
    }

    @SuppressWarnings("unchecked")
    public static Map<Integer, Pedido> loadPedidos() {
        File file = new File(PEDIDOS_FILE);
        if (!file.exists()) {
            return new HashMap<>();
        }

        try (XMLDecoder decoder = new XMLDecoder(new FileInputStream(PEDIDOS_FILE))) {
            return (Map<Integer, Pedido>) decoder.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo não encontrado: " + e.getMessage());
        } catch (ClassCastException e) {
            System.out.println("Erro ao converter dados do XML: " + e.getMessage());
        }
        return new HashMap<>();
    }
}
