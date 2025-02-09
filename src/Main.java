import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

class Pessoa {
    private String nome;
    private LocalDate dataNascimento;

    public Pessoa(String nome, LocalDate dataNascimento) {
        this.nome = nome;
        this.dataNascimento = dataNascimento;
    }

    public String getNome() {
        return nome;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }


    public String getDataNascimentoFormatada() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return this.dataNascimento.format(formatter);
    }
}

class Funcionario extends Pessoa {
    private BigDecimal salario;
    private String funcao;

    public Funcionario(String nome, LocalDate dataNascimento, BigDecimal salario, String funcao) {
        super(nome, dataNascimento);
        this.salario = salario;
        this.funcao = funcao;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }

    public String getFuncao() {
        return funcao;
    }


    public int getIdade() {
        LocalDate hoje = LocalDate.now();
        return Period.between(this.getDataNascimento(), hoje).getYears();
    }


    public String getSalarioFormatado() {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("pt", "BR"));
        symbols.setDecimalSeparator(',');
        symbols.setGroupingSeparator('.');
        DecimalFormat df = new DecimalFormat("#,##0.00", symbols);
        return df.format(this.salario);
    }
}

public class Main {
    public static void main(String[] args) {


        List<Funcionario> funcionarios = new ArrayList<>();
        funcionarios.add(new Funcionario("Maria",   LocalDate.of(2000,10,18), BigDecimal.valueOf(2009.44), "Operador"));
        funcionarios.add(new Funcionario("João",    LocalDate.of(1990, 5,12), BigDecimal.valueOf(2284.38), "Operador"));
        funcionarios.add(new Funcionario("Caio",    LocalDate.of(1961, 5, 2), BigDecimal.valueOf(9836.14), "Coordenador"));
        funcionarios.add(new Funcionario("Miguel",  LocalDate.of(1988,10,14), BigDecimal.valueOf(19119.88),"Diretor"));
        funcionarios.add(new Funcionario("Alice",   LocalDate.of(1995, 1, 5), BigDecimal.valueOf(2234.68), "Recepcionista"));
        funcionarios.add(new Funcionario("Heitor",  LocalDate.of(1999,11,19), BigDecimal.valueOf(1582.72), "Operador"));
        funcionarios.add(new Funcionario("Arthur",  LocalDate.of(1993, 3,31), BigDecimal.valueOf(4071.84), "Contador"));
        funcionarios.add(new Funcionario("Laura",   LocalDate.of(1994, 7, 8), BigDecimal.valueOf(3017.45), "Gerente"));
        funcionarios.add(new Funcionario("Heloísa", LocalDate.of(2003, 5,24), BigDecimal.valueOf(1606.85), "Eletricista"));
        funcionarios.add(new Funcionario("Helena",  LocalDate.of(1996, 9, 2), BigDecimal.valueOf(2799.93), "Gerente"));


        funcionarios.removeIf(f -> f.getNome().equalsIgnoreCase("João"));


        System.out.println("3.3 - Funcionários (dados originais, exceto a remoção de João):");
        imprimirFuncionarios(funcionarios);


        for (Funcionario f : funcionarios) {
            BigDecimal novoSalario = f.getSalario().multiply(BigDecimal.valueOf(1.10));
            f.setSalario(novoSalario);
        }

        System.out.println("\n3.4 - Funcionários após 10% de aumento:");
        imprimirFuncionarios(funcionarios);


        Map<String, List<Funcionario>> funcionariosPorFuncao = new HashMap<>();
        for (Funcionario f : funcionarios) {
            funcionariosPorFuncao
                    .computeIfAbsent(f.getFuncao(), k -> new ArrayList<>())
                    .add(f);
        }


        System.out.println("\n3.6 - Funcionários agrupados por função:");
        for (Map.Entry<String, List<Funcionario>> entry : funcionariosPorFuncao.entrySet()) {
            System.out.println("Função: " + entry.getKey());
            for (Funcionario f : entry.getValue()) {
                imprimirInfoFuncionario(f);
            }
            System.out.println();
        }


        System.out.println("3.8 - Funcionários que fazem aniversário em outubro (10) ou dezembro (12):");
        funcionarios.stream()
                .filter(f -> {
                    int mes = f.getDataNascimento().getMonthValue();
                    return (mes == 10 || mes == 12);
                })
                .forEach(Main::imprimirInfoFuncionario);


        Funcionario maisVelho = funcionarios.stream()
                .min(Comparator.comparing(Funcionario::getDataNascimento))
                .orElse(null);
        if (maisVelho != null) {
            System.out.println("\n3.9 - Funcionário com maior idade:");
            System.out.println("Nome: " + maisVelho.getNome() + ", Idade: " + maisVelho.getIdade());
        }


        System.out.println("\n3.10 - Funcionários em ordem alfabética:");
        List<Funcionario> ordenados = funcionarios.stream()
                .sorted(Comparator.comparing(Funcionario::getNome))
                .collect(Collectors.toList());
        imprimirFuncionarios(ordenados);

        BigDecimal totalSalarios = funcionarios.stream()
                .map(Funcionario::getSalario)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println("3.11 - Soma total dos salários (após reajuste 10%): " + formatarValor(totalSalarios));


        System.out.println("\n3.12 - Quantos salários mínimos cada funcionário recebe:");
        BigDecimal salarioMinimo = BigDecimal.valueOf(1212.00);
        for (Funcionario f : funcionarios) {

            BigDecimal qtdSalarios = f.getSalario().divide(salarioMinimo, 2, BigDecimal.ROUND_HALF_UP);
            System.out.println(f.getNome() + " recebe " + qtdSalarios + " salários mínimos.");
        }
    }


    private static void imprimirFuncionarios(List<Funcionario> lista) {
        for (Funcionario f : lista) {
            imprimirInfoFuncionario(f);
        }
    }


    private static void imprimirInfoFuncionario(Funcionario f) {
        System.out.println(
                "Nome: " + f.getNome() +
                        " | Data Nascimento: " + f.getDataNascimentoFormatada() +
                        " | Salário: " + f.getSalarioFormatado() +
                        " | Função: " + f.getFuncao()
        );
    }

  
    private static String formatarValor(BigDecimal valor) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("pt", "BR"));
        symbols.setDecimalSeparator(',');
        symbols.setGroupingSeparator('.');
        DecimalFormat df = new DecimalFormat("#,##0.00", symbols);
        return df.format(valor);
    }
}