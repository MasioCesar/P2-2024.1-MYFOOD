package br.ufal.ic.p2.myfood.tipousuario;

public class Cliente extends User {
    public Cliente() {
        super();
    }

    public Cliente(int id, String nome, String email, String senha, String endereco) {
        super(id, nome, email, senha, endereco);
    }
}
