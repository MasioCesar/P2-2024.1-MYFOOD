package br.ufal.ic.p2.myfood.services.managers;

import br.ufal.ic.p2.myfood.exceptions.Usuario.*;
import br.ufal.ic.p2.myfood.models.TiposUsuarios.Entregador;
import br.ufal.ic.p2.myfood.models.entidades.Empresa;
import br.ufal.ic.p2.myfood.services.XMLFunctions.XMLUsuario;
import br.ufal.ic.p2.myfood.models.TiposUsuarios.Cliente;
import br.ufal.ic.p2.myfood.models.TiposUsuarios.DonoEmpresa;
import br.ufal.ic.p2.myfood.models.entidades.Usuario;
import br.ufal.ic.p2.myfood.services.mediator.Mediator;
import br.ufal.ic.p2.myfood.utils.Validate;

import java.util.HashMap;
import java.util.Map;

public class UsuarioManager {
    // Instância única da classe UsuarioManager
    private static UsuarioManager instance;

    private Map<String, Usuario> users;
    private final Map<Integer, Usuario> usersById;
    private int nextUserId = 0;

    public UsuarioManager() {
        this.users = XMLUsuario.load();
        this.usersById = new HashMap<>();

        for (Usuario usuario : users.values()) {
            usersById.put(usuario.getId(), usuario);
        }
    }

    public static UsuarioManager getInstance() {
        if (instance == null) {
            instance = new UsuarioManager();
        }
        return instance;
    }

    // DONO RESTAURANTE
    public void criarUsuario(String nome, String email, String senha, String endereco, String cpf) throws Exception {
        if (cpf == null || cpf.length() != 14) {
            throw new CPFInvalidoException();
        }
        Validate.validarUsuario(nome, email, senha, endereco, users);

        Usuario usuario;
        int id = nextUserId++;

        usuario = new DonoEmpresa(id, nome, email, senha, endereco, cpf);

        users.put(email, usuario);
        usersById.put(usuario.getId(), usuario);
    }

    // CLIENTE
    public void criarUsuario(String nome, String email, String senha, String endereco) throws Exception {
        Validate.validarUsuario(nome, email, senha, endereco, users);

        Usuario usuario;
        int id = nextUserId++;

        usuario = new Cliente(id, nome, email, senha, endereco);

        users.put(email, usuario);
        usersById.put(usuario.getId(), usuario);
    }

    // ENTREGADOR
    public void criarUsuario(String nome, String email, String senha, String endereco, String veiculo, String placa) throws Exception {
        if (veiculo == null || veiculo.isEmpty()) {
            throw new VeiculoInvalidoException();
        }
        if (placa == null || placa.isEmpty()) {
            throw new PlacaInvalidoException();
        }

        for (Usuario user : users.values()) {
            if (user.isEntregador()) {
                Entregador entregador = (Entregador) user;
                if (entregador.getPlaca().equals(placa)) {
                    throw new PlacaInvalidoException();
                }
            }
        }

        Validate.validarUsuario(nome, email, senha, endereco, users);

        Usuario usuario;
        int id = nextUserId++;

        usuario = new Entregador(id, nome, email, senha, endereco, veiculo, placa);

        users.put(email, usuario);
        usersById.put(usuario.getId(), usuario);
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
                return usuario.getEndereco();
            case "cpf":
                if (usuario.possuiCpf()) {
                    return ((DonoEmpresa) usuario).getCpf();
                } else {
                    throw new UsuarioSemCPFException();
                }
            case "veiculo":
                return ((Entregador) usuario).getVeiculo();
            case "placa":
                return ((Entregador) usuario).getPlaca();
            default:
                throw new AtributoDesconhecidoException();
        }
    }

    public Usuario getUser(int userId) throws Exception {
        for (Usuario usuario : users.values()) {
            if (usuario.getId() == userId) {
                return usuario;
            }
        }
        throw new UsuarioNaoEncontradoException();
    }


    private Usuario findUserById(int id) {
        return usersById.get(id);
    }

    public void zerarSistema() {
        users.clear();
        usersById.clear();
        nextUserId = 0;
        XMLUsuario.save(users);
    }

    // Método para salvar os dados no XML ao encerrar o sistema
    public void salvarDados() {
        XMLUsuario.save(users);
    }
}
