package br.ufal.ic.p2.myfood.services;

import br.ufal.ic.p2.myfood.XMLFunction.XMLFacade;
import br.ufal.ic.p2.myfood.tipousuario.Cliente;
import br.ufal.ic.p2.myfood.tipousuario.DonoRestaurante;
import br.ufal.ic.p2.myfood.tipousuario.User;

import java.util.HashMap;
import java.util.Map;

public class UserManager {
    private Map<String, User> users;
    private Map<Integer, User> usersById;
    private int nextUserId = 0;
    private static final String CPF_DEFAULT = "DEFAULT";

    public UserManager() {
        this.users = XMLFacade.loadUsers();
        this.usersById = new HashMap<>();

        for (User user : users.values()) {
            usersById.put(user.getId(), user);
        }
    }

    public void setUsers(Map<String, User> users) {
        this.users = users;
        // Atualize o mapa de IDs quando os usuários forem definidos
        this.usersById.clear();
        for (User user : users.values()) {
            usersById.put(user.getId(), user);
        }
    }

    public void criarUsuario(String nome, String email, String senha, String endereco, String cpf) {
        if (cpf == null || !CPF_DEFAULT.equals(cpf) && cpf.length() != 14) {
            throw new IllegalArgumentException("CPF invalido");
        }
        if (nome == null || nome.isEmpty()) {
            throw new IllegalArgumentException("Nome invalido");
        }
        if (senha == null || senha.isEmpty()) {
            throw new IllegalArgumentException("Senha invalido");
        }
        if (email == null) {
            throw new IllegalArgumentException("Email invalido");
        }
        if (!isValidEmail(email) || email.isEmpty()) {
            throw new IllegalArgumentException("Formato de email invalido");
        }
        if (endereco == null || endereco.isEmpty()) {
            throw new IllegalArgumentException("Endereco invalido");
        }
        if (users.containsKey(email)) {
            throw new IllegalArgumentException("Conta com esse email ja existe");
        }

        User user;
        int id = nextUserId++;

        if (CPF_DEFAULT.equals(cpf)) {
            user = new Cliente(id, nome, email, senha, endereco);
        } else {
            user = new DonoRestaurante(id, nome, email, senha, endereco, cpf);
        }

        users.put(email, user);
        usersById.put(user.getId(), user); // Adicione ao mapa de IDs

        XMLFacade.saveUsers(users);
    }

    public int login(String email, String senha) {
        User user = users.get(email);
        if (user != null && user.getSenha().equals(senha)) {
            return user.getId(); // Retorna o ID do usuário
        } else {
            throw new IllegalArgumentException("Login ou senha invalidos");
        }
    }

    public String getAtributoUsuario(int id, String atributo) {
        User user = findUserById(id);

        if (user == null) {
            throw new IllegalArgumentException("Usuario nao cadastrado.");
        }

        switch (atributo) {
            case "nome":
                return user.getNome();
            case "email":
                return user.getEmail();
            case "senha":
                return user.getSenha();
            case "endereco":
                return user.getEndereco();
            case "cpf":
                if (user instanceof DonoRestaurante) {
                    return ((DonoRestaurante) user).getCpf();
                } else {
                    throw new IllegalArgumentException("Usuario nao possui CPF");
                }
            default:
                throw new IllegalArgumentException("Atributo desconhecido");
        }
    }

    public User getUser(int userId) {
        User user = usersById.get(userId); // Use o mapa de IDs
        if (user == null) {
            throw new IllegalArgumentException("Usuario nao encontrado");
        }
        return user;
    }

    private boolean isValidEmail(String email) {
        // Expressão regular simples para validação de email
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }

    private User findUserById(int id) {
        return usersById.get(id); // Use o mapa de IDs para procurar o usuário
    }

    public void zerarSistema() {
        users.clear();
        usersById.clear(); // Limpe também o mapa de IDs
        nextUserId = 0;
        XMLFacade.saveUsers(users);
    }
}
