package components;
import java.util.Collection;
import java.util.Scanner;

public class Menu<T> {

    private static final Scanner scanner = new Scanner(System.in);
    ;
    private String titulo;
    private String msgAjuda;
    private Collection<Opcao<T>> opcoes;

    public Menu(String titulo, String msgAjuda, Collection<Opcao<T>> opcoes) {
        this.titulo = titulo;
        this.msgAjuda = msgAjuda;
        this.opcoes = opcoes;
    }

    public void mostraMenu() {
        System.out.println(titulo);

        for (Opcao<T> opcao : opcoes) {
            System.out.printf("%s - %s \n", opcao.getId(), opcao.getTituloOpcao());
        }
        System.out.print("Digite a opção desejada: ");

        String opc = scanner.nextLine();

        for (Opcao<T> opcao : opcoes) {
            if (opcao.getId().toString().equals(opc)) {
                limparTela();
                opcao.run();
                scanner.nextLine();
                limparTela();
                return;
            }
        }

        System.out.printf("Opção %s não encontrada. Ajuda: %s", opc, msgAjuda);
        scanner.nextLine();
        limparTela();
    }

    private static void limparTela() {
        System.out.print("\033[H\033[2J");
        System.out.flush();

    }

    public String getTitulo() {
        return titulo;
    }

    public String getMsgAjuda() {
        return msgAjuda;
    }

    public Collection<Opcao<T>> getOpcoes() {
        return opcoes;
    }
}
