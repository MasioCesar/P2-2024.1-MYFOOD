package br.ufal.ic.p2.myfood.services;

import br.ufal.ic.p2.myfood.services.XMLFunctions.XMLUser;
import br.ufal.ic.p2.myfood.models.TiposUsuarios.Cliente;
import br.ufal.ic.p2.myfood.models.TiposUsuarios.DonoRestaurante;
import br.ufal.ic.p2.myfood.models.Usuario;

import java.util.HashMap;
import java.util.Map;

public class UsuarioManager {
    private Map<String, Usuario> users;
    private final Map<Integer, Usuario> usersById;
    private int nextUserId = 0;
    private static final String CPF_DEFAULT = "DEFAULT";

    public UsuarioManager() {
        this.users = XMLUser.loadUsuarios();
        this.usersById = new HashMap<>();

        for (Usuario usuario : users.values()) {
            usersById.put(usuario.getId(), usuario);
        }
    }

    public void setUsers(Map<String, Usuario> users) {
        this.users = users;
        this.usersById.clear();
        for (Usuario usuario : users.values()) {
            usersById.put(usuario.getId(), usuario);
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

        Usuario usuario;
        int id = nextUserId++;

        if (CPF_DEFAULT.equals(cpf)) {
            usuario = new Cliente(id, nome, email, senha, endereco);
        } else {
            usuario = new DonoRestaurante(id, nome, email, senha, endereco, cpf);
        }

        users.put(email, usuario);
        usersById.put(usuario.getId(), usuario);

        XMLUser.saveUsuarios(users);
    }

    public int login(String email, String senha) {
        Usuario usuario = users.get(email);
        if (usuario != null && usuario.getSenha().equals(senha)) {
            return usuario.getId();
        } else {
            throw new IllegalArgumentException("Login ou senha invalidos");
        }
    }

    public String getAtributoUsuario(int id, String atributo) {
        Usuario usuario = findUserById(id);

        if (usuario == null) {
            throw new IllegalArgumentException("Usuario nao cadastrado.");
        }

        switch (atributo) {
            case "nome":
                return usuario.getNome();
            case "email":
                return usuario.getEmail();
            case "senha":
                return usuario.getSenha();
            case "endereco":
                return usuario.getEndereco();
            case "cpf":
                if (usuario instanceof DonoRestaurante) {
                    return ((DonoRestaurante) usuario).getCpf();
                } else {
                    throw new IllegalArgumentException("Usuario nao possui CPF");
                }
            default:
                throw new IllegalArgumentException("Atributo desconhecido");
        }
    }

    public Usuario getUser(int userId) {
        Usuario usuario = usersById.get(userId); // Use o mapa de IDs
        if (usuario == null) {
            throw new IllegalArgumentException("Usuario nao encontrado");
        }
        return usuario;
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }

    private Usuario findUserById(int id) {
        return usersById.get(id);
    }

    public void zerarSistema() {
        users.clear();
        usersById.clear();
        nextUserId = 0;
        XMLUser.saveUsuarios(users);
    }
}
