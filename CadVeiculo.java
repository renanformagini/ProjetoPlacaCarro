import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

import components.Menu;
import components.Opcao;
import entities.Carro;
import entities.Moto;
import entities.Veiculo;
import services.VeiculoService;

public class CadVeiculo {
    private static Scanner scan;
    private static VeiculoService veiculoService;

    private static AtomicBoolean executando = new AtomicBoolean(true);
    private static final ArrayList<Opcao<Integer>> opcaoPrincipal = new ArrayList<>();
    private static final Menu<Integer> menuPrincipal;

    static{
        opcaoPrincipal.add(new Opcao<Integer>(1, "Cadastrar Novo Veículo", () -> save()));
        opcaoPrincipal.add(new Opcao<Integer>(2, "Listar todos os Veículos", () -> imprimirVeiculos()));
        opcaoPrincipal.add(new Opcao<Integer>(3, "Pesquisar Veículo por Placa", () -> buscaVeiculo()));
        opcaoPrincipal.add(new Opcao<Integer>(4, "Remover Veículo", () -> deleteVeiculo()));
        opcaoPrincipal.add(new Opcao<Integer>(0, "Sair", () -> {executando.set(false); System.out.println("Volte sempre! ");}));

        menuPrincipal = new Menu<Integer>("Sistema de Gerenciamento de Frotas", "Digite uma opção válida! ", opcaoPrincipal);
    }

    public static void main(String[] args) {
        scan = new Scanner(System.in);
        veiculoService = new VeiculoService();
        do {
            menuPrincipal.mostraMenu();
        } while (executando.get());
    }

    public static void save() {
        Veiculo veiculoAdd;

        System.out.println("Qual o tipo de veículo");
        System.out.println("1 - Carro");
        System.out.println("2 - Moto");
        System.out.print("Digite a opção desejada: ");
        int tipoVeiculo;
        do {
            if (scan.hasNextInt()) {
                tipoVeiculo = scan.nextInt();
                if (tipoVeiculo >= 1 && tipoVeiculo <= 2)
                    break;
            }    
            scan.nextLine();
            System.out.print("Digite uma opção válida: ");
        } while (true);
        scan.nextLine();

        System.out.print("Digite a marca do veículo: ");
        String marca = scan.nextLine();
        System.out.print("Digite o modelo do veículo: ");
        String modelo = scan.nextLine();
        System.out.print("Digite o ano do veículo: ");
        int ano;
        do {
            if (scan.hasNextInt()) {
                ano = scan.nextInt();
                break;
            }    
            scan.nextLine();
            System.out.print("Digite um ano válido: ");
        } while (true);
        
        scan.nextLine();
        System.out.print("Digite a placa do veículo: ");
        String placa = scan.nextLine();


        if (tipoVeiculo == 1) {
            System.out.print("Digite o número de portas: ");
            int numeroPortas = scan.nextInt();
            scan.nextLine();
            veiculoAdd = new Carro(marca, modelo, ano, placa, numeroPortas);
        } else {
            System.out.println("A moto possui partida elétrica: 1-Sim, 2-Não");
            System.out.print("Digite a opção desejada: ");
            int partidaEletrica;
            do {
                if (scan.hasNextInt()) {
                    partidaEletrica = scan.nextInt();
                    if (partidaEletrica >= 1 && partidaEletrica <= 2)
                        break;
                }    
                scan.nextLine();
                System.out.print("Digite uma opção válida: ");
            } while (true);
            scan.nextLine();
    
            scan.nextLine();
            boolean partida = partidaEletrica == 1 ? true : false;
            veiculoAdd = new Moto(marca, modelo, ano, placa, partida);
        } 
        try {
            veiculoService.save(veiculoAdd);
            System.out.println("Veiculo salvo com sucesso!");
            System.out.print("press enter to continue ");
            scan.nextLine();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            scan.nextLine();
        }
    }

    private static void imprimirVeiculos() {
        List<Veiculo> veiculos = veiculoService.getVeiculosDB();
        for (Veiculo veiculo : veiculos) {
            int id = veiculos.indexOf(veiculo);
            System.out.print("Veiculo "+ (id + 1) + ": " +veiculo+"\n");
        }
    }
    
    private static void buscaVeiculo(){
        System.out.print(" Busca de veiculo! ");
        System.out.print("Digite a placa do veiculo que deseja buscar: ");
        String placa = scan.nextLine();
        List<Veiculo> veiculos = veiculoService.getVeiculosDB();
        for (Veiculo veiculo : veiculos) {
            if (veiculo.getPlaca().equals(placa)){
                int id = veiculos.indexOf(veiculo);
                System.out.print("Veiculo "+ (id + 1) + ": " +veiculo);
                return;
           };
        }
        System.out.println("Nenhum veiculo encontrado com está placa!");
            
        
    }
    private static void deleteVeiculo(){
        System.out.print("Digite a placa do veiculo que deseja excluir: ");
        String placa = scan.nextLine();
        List<Veiculo> veiculos = veiculoService.getVeiculosDB();
        for (Veiculo veiculo : veiculos) {
            if (veiculo.getPlaca().equals(placa)){
                int id = veiculos.indexOf(veiculo);
                veiculos.remove(id);
                System.out.println("Viculo "+ placa +" deletado com sucesso!");
                return;
           };
        }
        System.out.println("Nenhum veiculo encontrado com está placa!");
    }
}