package br.ufal.ic.p2.myfood.exceptions.Empresa;

public class ProibidoCadastrarDuasEmpresasException extends Exception {
    public ProibidoCadastrarDuasEmpresasException() {
        super("Proibido cadastrar duas empresas com o mesmo nome e local");
    }
}