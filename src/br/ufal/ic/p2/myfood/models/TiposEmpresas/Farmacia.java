package br.ufal.ic.p2.myfood.models.TiposEmpresas;

import br.ufal.ic.p2.myfood.models.entidades.Empresa;

public class Farmacia extends Empresa {
    boolean aberto24Horas;
    int numeroFuncionarios;

    public Farmacia() {
        super();
    }

    public Farmacia(int id, String nome, String endereco, int donoId, String dono, String tipoEmpresa, boolean aberto24Horas, int numeroFuncionarios) {
        super(id, nome, endereco, donoId, dono, tipoEmpresa);
        this.aberto24Horas = aberto24Horas;
        this.numeroFuncionarios = numeroFuncionarios;
    }

    public boolean getAberto24Horas() {
        return aberto24Horas;
    }

    public int getNumeroFuncionarios() {
        return numeroFuncionarios;
    }

    public void setNumeroFuncionarios(int numeroFuncionarios) {
        this.numeroFuncionarios = numeroFuncionarios;
    }

    @Override
    public boolean isRestaurante() {
        return false;
    }

    @Override
    public boolean isMercado() {
        return false;
    }

    @Override
    public boolean isFarmacia() {
        return true;
    }
}
