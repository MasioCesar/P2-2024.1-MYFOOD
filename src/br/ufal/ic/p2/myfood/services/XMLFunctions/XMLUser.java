package br.ufal.ic.p2.myfood.services.XMLFunctions;

import br.ufal.ic.p2.myfood.models.Usuario;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class XMLUser {

    private static final String USERS_FILE = "users.xml";


    public static void saveUsuarios(Map<String, Usuario> users) {
        try (XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(USERS_FILE)))) {
            encoder.writeObject(users);
        } catch (Exception ignored) {
        }
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Usuario> loadUsuarios() {
        File file = new File(USERS_FILE);
        if (!file.exists()) {
            return new HashMap<>();
        }

        try (XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(USERS_FILE)))) {
            return (Map<String, Usuario>) decoder.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo não encontrado: " + e.getMessage());
        } catch (ClassCastException e) {
            System.out.println("Erro ao converter dados do XML: " + e.getMessage());
        }
        return new HashMap<>();
    }
}