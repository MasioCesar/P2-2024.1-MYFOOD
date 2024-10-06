package br.ufal.ic.p2.myfood.models.entidades;

import br.ufal.ic.p2.myfood.models.TiposUsuarios.Entregador;
import br.ufal.ic.p2.myfood.services.mediator.Mediator;

public class Entrega {
    private int entregaId;
    private Pedido pedido;
    private int entregadorId;
    private String destino;
    private Mediator mediator;

    public Entrega(int entregaId, Pedido pedido, int entregadorId, String destino, Mediator mediator) {
        this.entregaId = entregaId;
        this.pedido = pedido;
        this.entregadorId = entregadorId;
        this.destino = destino;
        this.mediator = mediator;
    }

    public Entrega() {}

    public int getEntregaId() {
        return entregaId;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public int getEntregadorId() {
        return entregadorId;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public void setEntregadorId(int entregadorId) {
        this.entregadorId = entregadorId;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public void setEntregaId(int entregaId) {
        this.entregaId = entregaId;
    }

    public void finalizarEntrega() throws Exception {
        Usuario entregador = mediator.getUsuarioById(this.entregadorId);

        ((Entregador) entregador).setOcupado(false);

        pedido.finalizarEntrega();
    }
}