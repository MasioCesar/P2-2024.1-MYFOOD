package br.ufal.ic.p2.myfood.models.TiposUsuarios;

import br.ufal.ic.p2.myfood.models.Usuario;

public class DonoRestaurante extends Usuario {
    private String cpf;

    public DonoRestaurante() {
        super();
    }

    public DonoRestaurante(int id, String nome, String email, String senha, String endereco, String cpf) {
        super(id, nome, email, senha, endereco);
        this.cpf = cpf;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}
