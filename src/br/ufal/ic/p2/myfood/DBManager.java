package br.ufal.ic.p2.myfood;
/*
import br.ufal.ic.p2.myfood.XMLFunction.XMLFacade;
import br.ufal.ic.p2.myfood.tipousuario.User;

import java.util.HashMap;
import java.util.Map;

public class DBmanager {

    private static DBmanager session; // Inst�ncia �nica do banco de dados
    private static HashMap<String, User> users; // HashMap de usu�rios
    private static int key; // Pr�ximo ID para novos usu�rios
    private static String sessionUser; // Usu�rio da sess�o


    private DBmanager() {
        users = XMLFacade.load(); // Carregar os usu�rios do XML
        key = determineNextKey(); // Determinar o pr�ximo ID dispon�vel
    }

    public static DBmanager getDatabase() {
        if (session == null) {
            session = new DBmanager();
        }
        return session;
    }

    public static HashMap<String, User> getUsers() {
        return users;
    }

    public static String getSessionUser() {
        return sessionUser;
    }

    public static void setSessionUser(String sessionUser) {
        DBmanager.sessionUser = sessionUser;
    }

    public static int getNextKey() {
        return key;
    }

    public static void setNextKey(int nextKey) {
        key = nextKey;
    }

    public static String add(User user) { // Adiciona novos usu�rios ao HashMap de usu�rios
        key++;
        String id = Integer.toString(key);
        user.setId(id);
        users.put(id, user);
        return id;
    }

    public static User getUser(String id) { // Retorna um usu�rio do HashMap a partir da chave
        return users.get(id);
    }

    private int determineNextKey() {
        // L�gica para determinar o pr�ximo ID dispon�vel
        return users.size();
    }

    public void save() {
        XMLFacade.save(users, key, sessionUser);
    }

    public void load() {
        Map<String, User> loadedData = XMLFacade.load();
        if (loadedData != null) {
            users = new HashMap<>(loadedData);
        }
    }
}
*/