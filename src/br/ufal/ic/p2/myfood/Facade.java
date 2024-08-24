package br.ufal.ic.p2.myfood;

import br.ufal.ic.p2.myfood.XMLFunction.XMLFacade;
import br.ufal.ic.p2.myfood.tipousuario.Cliente;
import br.ufal.ic.p2.myfood.tipousuario.DonoRestaurante;
import br.ufal.ic.p2.myfood.tipousuario.User;


import java.beans.XMLEncoder;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Facade {
    private Map<String, User> usersByEmail = new HashMap<>(); // Mapeia o email para o objeto User
    private int nextUserId = 0; // Gerador de ID único
    private String sessionUser;
    private static final String DATA_FILE = "users_data.xml";

    public Facade() {
        loadState();
    }

    // Zera o sistema, removendo todos os dados e sessões
    public void zerarSistema() {
        nextUserId = 0;
        usersByEmail.clear();
        sessionUser = null;
        clearDataFile();
    }

    private static final String CPF_DEFAULT = "DEFAULT";

    public void criarUsuario(String nome, String email, String senha, String endereco) {
        criarUsuario(nome, email, senha, endereco, CPF_DEFAULT);
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
        if (usersByEmail.containsKey(email)) {
            throw new IllegalArgumentException("Conta com esse email ja existe");
        }


        User user;
        int id = nextUserId++;

        if (CPF_DEFAULT.equals(cpf)) {
            user = new Cliente(id, nome, email, senha, endereco);
        } else {
            user = new DonoRestaurante(id, nome, email, senha, endereco, cpf);
        }
        usersByEmail.put(email, user);

        saveState();
    }

    // Retorna o valor de um atributo específico do usuário
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
/*
    // Edita o perfil do usuário, alterando um atributo específico
    public void editarPerfil(String email, String atributo, String valor) {
        User user = getUsuarioPorEmail(email);

        switch (atributo) {
            case "nome":
                user.setNome(valor); // Use o setter para modificar o nome
                break;
            case "endereco":
                user.setEndereco(valor); // Use o setter para modificar o endereço
                break;
            case "senha":
                user.setSenha(valor); // Use o setter para modificar a senha
                break;
            case "cpf":
                if (user instanceof DonoRestaurante) {
                    ((DonoRestaurante) user).setCpf(valor); // Use o setter para modificar o CPF
                } else {
                    throw new IllegalArgumentException("Apenas Dono de Restaurante pode ter CPF.");
                }
                break;
            default:
                throw new IllegalArgumentException("Atributo desconhecido.");
        }

        saveState();
    }

 */

    public int login(String email, String senha) {
        User user = usersByEmail.get(email);
        if (user != null && user.getSenha().equals(senha)) {
            sessionUser = email; // Abre a sessão
            return user.getId(); // Retorna o ID do usuário
        } else {
            throw new IllegalArgumentException("Login ou senha invalidos");
        }
    }


    // Encerra o sistema, limpando a sessão
    public void encerrarSistema() {
        sessionUser = null;
        saveState();
    }

    private User findUserById(int id) {
        for (User user : usersByEmail.values()) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }

    // Salva o estado atual dos dados de usuários no arquivo XML
    public void saveState() {
        try (XMLEncoder encoder = new XMLEncoder(
                new BufferedOutputStream(
                        new FileOutputStream(DATA_FILE)))) {
            encoder.writeObject(usersByEmail);
            encoder.flush(); // Certifica-se de que todos os dados são escritos no arquivo
        } catch (FileNotFoundException e) {
            e.printStackTrace(); // Imprime o stack trace para ajudar a diagnosticar problemas
        }
    }

    // Carrega o estado dos dados de usuários do arquivo XML
    public void loadState() {
        Map<String, User> loadedData = XMLFacade.load();
        if (loadedData != null && !loadedData.isEmpty()) {
            usersByEmail = loadedData;

            // Atualiza o próximo ID baseado no maior ID dos usuários carregados
            nextUserId = usersByEmail.values().stream()
                    .mapToInt(User::getId)
                    .max()
                    .orElse(0) + 1;
        } else {
            System.out.println("Nenhum dado encontrado ou o arquivo está vazio.");
        }
    }


    private void clearDataFile() {
        File file = new File(DATA_FILE);
        if (file.exists()) {
            file.delete();
        }
    }

    private boolean isValidEmail(String email) {
        // Expressão regular simples para validação de email
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }
}