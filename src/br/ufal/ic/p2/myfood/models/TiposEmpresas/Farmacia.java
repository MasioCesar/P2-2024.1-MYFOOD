package br.ufal.ic.p2.myfood.models.TiposEmpresas;

import br.ufal.ic.p2.myfood.models.entidades.Empresa;

public class Farmacia extends Empresa {
    boolean aberto24Horas;

    public Farmacia() {
        super();
    }

    public Farmacia(int id, String nome, String endereco, int donoId, String dono, String tipoEmpresa, boolean aberto24Horas) {
        super(id, nome, endereco, donoId, dono, tipoEmpresa);
        this.aberto24Horas = aberto24Horas;
    }

    public boolean getAberto24Horas() {
        return aberto24Horas;
    }

    public void setAberto24Horas(boolean aberto24Horas) {
        this.aberto24Horas = aberto24Horas;
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
