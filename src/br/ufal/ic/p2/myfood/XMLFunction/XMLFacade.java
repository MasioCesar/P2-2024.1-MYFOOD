package br.ufal.ic.p2.myfood.XMLFunction;

import br.ufal.ic.p2.myfood.tipousuario.Empresa;
import br.ufal.ic.p2.myfood.tipousuario.Pedido;
import br.ufal.ic.p2.myfood.tipousuario.Produto;
import br.ufal.ic.p2.myfood.tipousuario.User;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class XMLFacade {

    private static final String USERS_FILE = "users_data.xml";
    private static final String EMPRESAS_FILE = "empresas.xml";
    private static final String PRODUTOS_FILE = "produtos.xml";
    private static final String PEDIDOS_FILE = "pedidos.xml";

    public static void saveUsers(Map<String, User> users) {
        try (XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(USERS_FILE)))) {
            encoder.writeObject(users);
        } catch (FileNotFoundException e) {
            //throw new ErroAoSalvarDados();
        }
    }

    public static Map<String, User> loadUsers() {
        Map<String, User> loadedData = new HashMap<>();
        try (XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(USERS_FILE)))) {
            // Lê o conteúdo do arquivo XML
            Object obj = decoder.readObject();
            if (obj instanceof Map) {
                loadedData = (Map<String, User>) obj;
            }
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo não encontrado: " + e.getMessage());
        } catch (ClassCastException e) {
            System.out.println("Erro ao converter dados do XML: " + e.getMessage());
        }
        return loadedData;
    }

    public static void salvarEmpresas(Map<Integer, Empresa> empresas) {
        try (XMLEncoder encoder = new XMLEncoder(new FileOutputStream(EMPRESAS_FILE))) {
            encoder.writeObject(empresas);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Map<Integer, Empresa> loadEmpresas() {
        File file = new File(EMPRESAS_FILE);
        if (!file.exists()) {
            return new HashMap<>();
        }

        try (XMLDecoder decoder = new XMLDecoder(new FileInputStream(EMPRESAS_FILE))) {
            return (Map<Integer, Empresa>) decoder.readObject();
        } catch (IOException e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }

    public static void salvarProdutos(Map<Integer, Map<Integer, Produto>> produtosPorEmpresa) {
        try (XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(PRODUTOS_FILE)))) {
            encoder.writeObject(produtosPorEmpresa);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Map<Integer, Map<Integer, Produto>> loadProdutos() {
        File file = new File(PRODUTOS_FILE);
        if (!file.exists()) {
            return new HashMap<>();
        }

        try (XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(PRODUTOS_FILE)))) {
            Object obj = decoder.readObject();
            if (obj instanceof Map) {
                return (Map<Integer, Map<Integer, Produto>>) obj;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new HashMap<>();
    }

    public static void salvarPedidos(Map<Integer, Pedido> pedidos) {
        try (XMLEncoder encoder = new XMLEncoder(new FileOutputStream(PEDIDOS_FILE))) {
            encoder.writeObject(pedidos);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao salvar pedidos em XML", e);
        }
    }

    public static Map<Integer, Pedido> loadPedidos() {
        File file = new File(PEDIDOS_FILE);
        if (!file.exists()) {
            return new HashMap<>();
        }

        try (XMLDecoder decoder = new XMLDecoder(new FileInputStream(PEDIDOS_FILE))) {
            return (Map<Integer, Pedido>) decoder.readObject();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao carregar pedidos de XML", e);
        }
    }


}