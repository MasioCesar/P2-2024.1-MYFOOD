package br.ufal.ic.p2.myfood.services.XMLFunctions;

import br.ufal.ic.p2.myfood.models.Empresa;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class XMLEmpresa {
    private static final String EMPRESAS_FILE = "empresas.xml";

    public static void saveEmpresas(Map<Integer, Empresa> empresas) {
        try (XMLEncoder encoder = new XMLEncoder(new FileOutputStream(EMPRESAS_FILE))) {
            encoder.writeObject(empresas);
        } catch (Exception ignored) {
        }
    }

    @SuppressWarnings("unchecked")
    public static Map<Integer, Empresa> loadEmpresas() {
        File file = new File(EMPRESAS_FILE);
        if (!file.exists()) {
            return new HashMap<>();
        }

        try (XMLDecoder decoder = new XMLDecoder(new FileInputStream(EMPRESAS_FILE))) {
            return (Map<Integer, Empresa>) decoder.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo não encontrado: " + e.getMessage());
        } catch (ClassCastException e) {
            System.out.println("Erro ao converter dados do XML: " + e.getMessage());
        }
        return new HashMap<>();
    }
}
