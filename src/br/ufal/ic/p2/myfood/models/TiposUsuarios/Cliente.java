package br.ufal.ic.p2.myfood.models.TiposUsuarios;

import br.ufal.ic.p2.myfood.models.Usuario;

public class Cliente extends Usuario {
    String endereco;
    public Cliente() {
        super();
    }

    public Cliente(int id, String nome, String email, String senha, String endereco) {
        super(id, nome, email, senha);
        this.endereco = endereco;
    }

    @Override
    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public boolean possuiCpf() {
        return false; // Cliente não possui CPF
    }
}
