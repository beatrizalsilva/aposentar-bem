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

        System.out.println("Resultado do cálculo de benefício:");
        System.out.println("Regra aplicada: " + regra);
        System.out.println("Valor do benefício: " + beneficio);
    }
}