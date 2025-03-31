package com.AgiBank.controller.elegibilidade;

import com.AgiBank.dao.contribuicao.ContribuicaoDAOImpl;
import com.AgiBank.model.Contribuicao;
import com.AgiBank.model.Pedagios;
import com.AgiBank.model.RegrasAposentadoria;

import java.util.List;

public class PedagiosController {
    private Pedagios pedagios;
    private String regraAplicada;
    private List<Contribuicao> contribuicoes;

    public PedagiosController(List<Contribuicao> contribuicoes, int idade, RegrasAposentadoria.Genero genero,
                              RegrasAposentadoria.Profissao profissao) {
        this.contribuicoes = contribuicoes;
        this.pedagios = new Pedagios(contribuicoes, idade, genero, profissao);
    }

    public double calcularBeneficio() {
        if (pedagios.isElegivelPedagio100()) {
            regraAplicada = "Pedágio 100% - Tempo de contribuição dobrado";
            return pedagios.calcularPedagio100();
        } else if (pedagios.isElegivelPedagio50()) {
            regraAplicada = "Pedágio 50% - Tempo adicional de 50% do que falta";
            return pedagios.calcularPedagio50();
        } else {
            regraAplicada = "Nenhuma regra aplicada";
            return 0;
        }
    }

    public String getRegraAplicada() {
        return regraAplicada;
    }
}