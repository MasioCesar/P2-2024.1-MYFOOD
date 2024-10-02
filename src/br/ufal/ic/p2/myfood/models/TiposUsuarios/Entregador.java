package br.ufal.ic.p2.myfood.models.TiposUsuarios;

import br.ufal.ic.p2.myfood.models.entidades.Usuario;

public class Entregador extends Usuario {
    private String endereco;
    private String veiculo;
    private String placa;

    public Entregador() {
        super();
    }

    public Entregador(int id, String nome, String email, String senha, String endereco, String veiculo, String placa) {
        super(id, nome, email, senha);
        this.endereco = endereco;
        this.veiculo = veiculo;
        this.placa = placa;
    }

    @Override
    public String getEndereco() {
        return endereco;
    }

    public boolean possuiCpf() {
        return false;
    }


    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(String veiculo) {
        this.veiculo = veiculo;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    @Override
    public boolean isEntregador() {
        return true;
    }
}
