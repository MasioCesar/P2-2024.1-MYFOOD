package br.ufal.ic.p2.myfood.utils;

import br.ufal.ic.p2.myfood.exceptions.Produto.CategoriaInvalidoException;
import br.ufal.ic.p2.myfood.exceptions.Produto.NomeInvalidoException;
import br.ufal.ic.p2.myfood.exceptions.Produto.ValorInvalidoException;
import br.ufal.ic.p2.myfood.exceptions.Usuario.EmailInvalidoException;
import br.ufal.ic.p2.myfood.exceptions.Usuario.EmailJaExisteException;
import br.ufal.ic.p2.myfood.exceptions.Usuario.EnderecoInvalidoException;
import br.ufal.ic.p2.myfood.exceptions.Usuario.SenhaInvalidaException;
import br.ufal.ic.p2.myfood.models.entidades.Usuario;

import java.util.Map;

public class Validate {
    public static void validarProduto(String nome, float valor, String categoria) throws Exception {
        if (nome == null || nome.trim().isEmpty()) {
            throw new NomeInvalidoException();
        }
        if (valor <= 0) {
            throw new ValorInvalidoException();
        }
        if (categoria == null || categoria.trim().isEmpty()) {
            throw new CategoriaInvalidoException();
        }
    }

    public static void validarUsuario(String nome, String email, String senha, String endereco, Map<String, Usuario> users) throws Exception {
        if (nome == null || nome.isEmpty()) {
            throw new NomeInvalidoException();
        }
        if (senha == null || senha.isEmpty()) {
            throw new SenhaInvalidaException();
        }
        if (email == null) {
            throw new EmailInvalidoException();
        }
        if (!isValidEmail(email) || email.isEmpty()) {
            throw new EmailInvalidoException();
        }
        if (endereco == null || endereco.isEmpty()) {
            throw new EnderecoInvalidoException();
        }
        if (users.containsKey(email)) {
            throw new EmailJaExisteException();
        }
    }

    private static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }
}
