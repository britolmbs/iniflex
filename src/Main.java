import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

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

            public String getDataNascimentoFormadata() {
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
        }



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
        System.out.println(funcionarios);

        for (Funcionario f: funcionarios) {
            BigDecimal novoSalario = f.getSalario().multiply(BigDecimal.valueOf(1.10));
            f.setSalario(novoSalario);
        }
        System.out.println(funcionarios);

        Map<String, List<Funcionario>> funcionariosPorFuncao = new HashMap<>();
        for (Funcionario f : funcionarios ) {
            funcionariosPorFuncao
                    .computeIfAbsent(f.getFuncao(), k -> new ArrayList<>())
                    .add(f);
        }
        for (Map.Entry<String, List<Funcionario>> entry : funcionariosPorFuncao.entrySet()) {
            System.out.println("Função" + entry.getKey());
            for (Funcionario f: entry.getValue()) {
            }
            System.out.println();

        }
    }

}