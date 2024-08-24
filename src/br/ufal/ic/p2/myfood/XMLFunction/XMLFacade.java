package br.ufal.ic.p2.myfood.XMLFunction;

import br.ufal.ic.p2.myfood.tipousuario.User;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class XMLFacade {

    private static final String DATA_FILE = "users_data.xml";

    public static void save(Map<String, User> users) {
        try (XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(DATA_FILE)))) {
            encoder.writeObject(users);
        } catch (FileNotFoundException e) {
            //throw new ErroAoSalvarDados();
        }
    }

    public static Map<String, User> load() {
        Map<String, User> loadedData = new HashMap<>();
        try (XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(DATA_FILE)))) {
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


}