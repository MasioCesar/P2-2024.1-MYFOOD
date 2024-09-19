package br.ufal.ic.p2.myfood.models.TiposUsuarios;

import br.ufal.ic.p2.myfood.models.entidades.Usuario;

public class DonoEmpresa extends Usuario {
    private String cpf;
    private String endereco;

    public DonoEmpresa() {
        super();
    }

    public DonoEmpresa(int id, String nome, String email, String senha, String endereco, String cpf) {
        super(id, nome, email, senha);
        this.endereco  = endereco;
        this.cpf = cpf;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    @Override
    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    @Override
    public boolean possuiCpf() {
        return true; // DonoEmpresa possui CPF
    }
}
