package components;

public class Opcao<T> {

    private final T id;
    private final String tituloOpcao;
    private final Runnable acao;

    public Opcao(T id, String tituloOpcao, Runnable acao) {
        this.id = id;
        this.tituloOpcao = tituloOpcao;
        this.acao = acao;
    }

    public void run() {
        acao.run();
    }

    public T getId() {
        return id;
    }

    public String getTituloOpcao() {
        return tituloOpcao;
    }

    public Runnable getAcao() {
        return acao;
    }
}
