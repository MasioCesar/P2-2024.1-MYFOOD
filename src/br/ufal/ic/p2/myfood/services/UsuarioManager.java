package br.ufal.ic.p2.myfood.services;

import br.ufal.ic.p2.myfood.exceptions.Usuario.*;
import br.ufal.ic.p2.myfood.services.XMLFunctions.XMLUser;
import br.ufal.ic.p2.myfood.models.TiposUsuarios.Cliente;
import br.ufal.ic.p2.myfood.models.TiposUsuarios.DonoRestaurante;
import br.ufal.ic.p2.myfood.models.Usuario;
import br.ufal.ic.p2.myfood.utils.Validate;

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

    // DONO RESTAURANTE
    public void criarUsuario(String nome, String email, String senha, String endereco, String cpf) throws Exception {
        if (cpf == null || cpf.length() != 14) {
            throw new CPFInvalidoException();
        }
        Validate.validarUsuario(nome, email, senha, endereco, users);

        Usuario usuario;
        int id = nextUserId++;

        usuario = new DonoRestaurante(id, nome, email, senha, endereco, cpf);

        users.put(email, usuario);
        usersById.put(usuario.getId(), usuario);

        XMLUser.saveUsuarios(users);
    }

    // CLIENTE
    public void criarUsuario(String nome, String email, String senha, String endereco) throws Exception {
        Validate.validarUsuario(nome, email, senha, endereco, users);

        Usuario usuario;
        int id = nextUserId++;

        usuario = new Cliente(id, nome, email, senha, endereco);

        users.put(email, usuario);
        usersById.put(usuario.getId(), usuario);

        XMLUser.saveUsuarios(users);
    }



    public int login(String email, String senha) throws Exception {
        Usuario usuario = users.get(email);
        if (usuario != null && usuario.getSenha().equals(senha)) {
            return usuario.getId();
        } else {
            throw new LoginInvalidoException();
        }
    }

    public String getAtributoUsuario(int id, String atributo) throws Exception {
        Usuario usuario = findUserById(id);

        if (usuario == null) {
            throw new UsuarioNaoCadastradoException();
        }

        switch (atributo) {
            case "nome":
                return usuario.getNome();
            case "email":
                return usuario.getEmail();
            case "senha":
                return usuario.getSenha();
            case "endereco":
                if (usuario instanceof Cliente || usuario instanceof DonoRestaurante) {
                    return usuario.getEndereco();
                }
            case "cpf":
                if (usuario instanceof DonoRestaurante) {
                    return ((DonoRestaurante) usuario).getCpf();
                } else {
                    throw new UsuarioSemCPFException();
                }
            default:
                throw new AtributoDesconhecidoException();
        }
    }

    public Usuario getUser(int userId) throws Exception {
        Usuario usuario = usersById.get(userId); // Use o mapa de IDs
        if (usuario == null) {
            throw new UsuarioNaoEncontradoException();
        }
        return usuario;
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
