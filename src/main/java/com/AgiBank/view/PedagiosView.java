package com.AgiBank.view;

import com.AgiBank.controller.elegibilidade.PedagiosController;
import com.AgiBank.model.Contribuicao;
import com.AgiBank.model.RegrasAposentadoria;

import java.util.List;

public class PedagiosView {
    private PedagiosController pedagiosController;

    public PedagiosView(List<Contribuicao> contribuicoes, int idade, RegrasAposentadoria.Genero genero,
                        RegrasAposentadoria.Profissao profissao) {
        this.pedagiosController = new PedagiosController(contribuicoes, idade, genero, profissao);
    }

    public void exibirBeneficio() {
        double beneficio = pedagiosController.calcularBeneficio();
        String regra = pedagiosController.getRegraAplicada();

        System.out.println("\n=== Resultado do Cálculo de Aposentadoria ===");
        if (beneficio > 0) {
            System.out.println("Parabéns! Você se enquadra na seguinte regra: " + regra);
            System.out.printf("O valor estimado do seu benefício é: R$ %.2f%n", beneficio);
        } else {
            System.out.println("Infelizmente, você ainda não atende aos critérios para aposentadoria.");
            System.out.println("Recomenda-se revisar seu tempo de contribuição e idade.");
        }
    }
}