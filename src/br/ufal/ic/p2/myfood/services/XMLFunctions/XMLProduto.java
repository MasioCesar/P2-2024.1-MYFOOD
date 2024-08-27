package br.ufal.ic.p2.myfood.services.XMLFunctions;

import br.ufal.ic.p2.myfood.models.Empresa;
import br.ufal.ic.p2.myfood.models.Produto;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class XMLProduto {
    private static final String PRODUTOS_FILE = "produtos.xml";
    public static void saveProdutos(Map<Integer, Map<Integer, Produto>> produtosPorEmpresa) {
        try (XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(PRODUTOS_FILE)))) {
            encoder.writeObject(produtosPorEmpresa);
        } catch (Exception ignored) {
        }
    }

    @SuppressWarnings("unchecked")
    public static Map<Integer, Map<Integer, Produto>> loadProdutos() {
        File file = new File(PRODUTOS_FILE);
        if (!file.exists()) {
            return new HashMap<>();
        }

        try (XMLDecoder decoder = new XMLDecoder(new FileInputStream(PRODUTOS_FILE))) {
            return (Map<Integer, Map<Integer, Produto>>) decoder.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo não encontrado: " + e.getMessage());
        } catch (ClassCastException e) {
            System.out.println("Erro ao converter dados do XML: " + e.getMessage());
        }
        return new HashMap<>();
    }
}
