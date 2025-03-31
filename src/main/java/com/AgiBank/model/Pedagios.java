package com.AgiBank.model;
import com.AgiBank.model.Contribuicao;
import com.AgiBank.model.RegrasAposentadoria;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Pedagios extends RegrasAposentadoria {
    private List<Contribuicao> contribuicoes;
    private LocalDate DATA_REFERENCIA = LocalDate.of(1994, 7, 1);
    private Profissao profissao;

    public Pedagios(List<Contribuicao> contribuicoes, int idade, Genero genero, Profissao profissao) {
        super(idade, genero, contribuicoes.size() * 12, 0); // Initialize the superclass with total months
        this.contribuicoes = filtrarContribuicoesValidas(contribuicoes);
        this.profissao = profissao;
        this.setTempoContribuicaoEmMeses(calcularTempoContribuicaoEmMeses(this.contribuicoes));
    }

    private int calcularTempoContribuicaoEmMeses(List<Contribuicao> contribuicoes) {
        int totalMeses = 0;
        for (Contribuicao contribuicao : contribuicoes) {
            totalMeses += contribuicao.getPeriodoInicio().until(contribuicao.getPeriodoFim()).toTotalMonths();
        }
        return totalMeses;
    }

    private List<Contribuicao> filtrarContribuicoesValidas(List<Contribuicao> contribuicoes) {
        List<Contribuicao> contribuicoesValidas = new ArrayList<>();
        for (Contribuicao contribuicao : contribuicoes) {
            if (!contribuicao.getPeriodoInicio().isBefore(DATA_REFERENCIA)) {
                contribuicoesValidas.add(contribuicao);
            }
        }
        return contribuicoesValidas;
    }

    private double calcularSomaSalarios() {
        return Contribuicao.calcularSalarioTotal(contribuicoes);
    }

    private double calcularMediaSalarial() {
        int totalMeses = getTempoContribuicaoEmMeses();
        if (totalMeses <= 0) {
            return 0;
        }
        return calcularSomaSalarios() / totalMeses;
    }

    public boolean isElegivelPedagio50() {
        int idade = getIdade();
        int tempoTotal = getTempoContribuicaoEmMeses();

        int idadeMinima;
        int tempoMinimoContribuicao;
        if (getGenero() == Genero.MASCULINO) {
            idadeMinima = 57;
            tempoMinimoContribuicao = 420;
        } else {
            idadeMinima = 55;
            tempoMinimoContribuicao = 360;
        }

        if (idade < idadeMinima) {
            System.out.println("Você ainda não atingiu a idade mínima de " + idadeMinima + " anos.");
            return false;
        }

        int tempoFaltante = tempoMinimoContribuicao - tempoTotal;
        if (tempoFaltante < 0) {
            tempoFaltante = 0;
        }
        int tempoNecessarioComPedagio = tempoFaltante + (tempoFaltante / 2);

//        System.out.println("=== Testando Pedágio 50% ===");
//        System.out.println("Idade: " + idade);
//        System.out.println("Tempo total de contribuição (meses): " + tempoTotal);
//        System.out.println("Idade mínima necessária: " + idadeMinima);
//        System.out.println("Tempo mínimo de contribuição necessário: " + tempoMinimoContribuicao);
//        System.out.println("Tempo faltante: " + tempoFaltante);
//        System.out.println("Tempo necessário com Pedágio 50%: " + tempoNecessarioComPedagio);
        return tempoTotal >= tempoNecessarioComPedagio;
    }

    public boolean isElegivelPedagio100() {
        int idade = getIdade();
        int tempoContribuicao = getTempoContribuicaoEmMeses();

        int idadeMinima;
        int tempoMinimoContribuicao;
        if (getGenero() == Genero.MASCULINO) {
            if (profissao == Profissao.GERAL) {
                idadeMinima = 60;
                tempoMinimoContribuicao = 420;
            } else {
                idadeMinima = 55;
                tempoMinimoContribuicao = 360;
            }
        } else {
            if (profissao == Profissao.GERAL) {
                idadeMinima = 57;
                tempoMinimoContribuicao = 360;
            } else {
                idadeMinima = 52;
                tempoMinimoContribuicao = 300;
            }
        }

        if (idade < idadeMinima) {
            System.out.println("Você ainda não atingiu a idade mínima de " + idadeMinima + " anos.");
            return false;
        }

        int tempoFaltante = tempoMinimoContribuicao - tempoContribuicao;
        if (tempoFaltante < 0) {
            tempoFaltante = 0;
        }

        int tempoNecessario = tempoMinimoContribuicao + (2 * tempoFaltante);

//        System.out.println("=== Testando Pedágio 100% ===");
//        System.out.println("Idade: " + idade);
//        System.out.println("Tempo total de contribuição (meses): " + tempoContribuicao);
//        System.out.println("Idade mínima necessária: " + idadeMinima);
//        System.out.println("Tempo mínimo de contribuição necessário: " + tempoMinimoContribuicao);
//        System.out.println("Tempo faltante: " + tempoFaltante);
//        System.out.println("Tempo necessário com Pedágio 100%: " + tempoNecessario);
        return tempoContribuicao >= tempoNecessario;
    }

    public double calcularPedagio50() {
        double media = calcularMediaSalarial();
        FatorPrevidenciario fp = new FatorPrevidenciario(contribuicoes);
        double beneficio = media * fp.calcularFatorPrevidenciario();

        System.out.println("Seu benefício pelo Pedágio 50% será: R$ " + beneficio);
        return beneficio;
    }

    public double calcularPedagio100() {
        double beneficio = calcularMediaSalarial();

        System.out.println("Seu benefício pelo Pedágio 100% será: R$ " + beneficio);
        return beneficio;
    }
}