package br.ufal.ic.p2.myfood;
/*
import br.ufal.ic.p2.myfood.XMLFunction.XMLFacade;
import br.ufal.ic.p2.myfood.tipousuario.User;

import java.util.HashMap;
import java.util.Map;

public class DBmanager {

    private static DBmanager session; // Instância única do banco de dados
    private static HashMap<String, User> users; // HashMap de usuários
    private static int key; // Próximo ID para novos usuários
    private static String sessionUser; // Usuário da sessão


    private DBmanager() {
        users = XMLFacade.load(); // Carregar os usuários do XML
        key = determineNextKey(); // Determinar o próximo ID disponível
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

    public static String add(User user) { // Adiciona novos usuários ao HashMap de usuários
        key++;
        String id = Integer.toString(key);
        user.setId(id);
        users.put(id, user);
        return id;
    }

    public static User getUser(String id) { // Retorna um usuário do HashMap a partir da chave
        return users.get(id);
    }

    private int determineNextKey() {
        // Lógica para determinar o próximo ID disponível
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