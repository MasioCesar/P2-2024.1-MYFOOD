package br.ufal.ic.p2.myfood.services.mediator;

import br.ufal.ic.p2.myfood.models.entidades.Empresa;
import br.ufal.ic.p2.myfood.models.entidades.Produto;
import br.ufal.ic.p2.myfood.models.entidades.Usuario;

import java.util.Map;

public interface Mediator {
    Usuario getUsuarioById(int id) throws Exception;
    Empresa getEmpresaById(int id) throws Exception;
    boolean isDonoEmpresa(int clienteId, int empresaId) throws Exception;
    Produto getProdutoById(int id) throws Exception;
    Map<Integer, Empresa> getAllEmpresas() throws Exception;
}