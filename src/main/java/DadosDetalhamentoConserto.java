

public record DadosDetalhamentoConserto(Long id, String dataEntrada, String dataSaida,
    String nomeMecanico, Integer anosExperienciaMecanico,
    String marca, String modelo, String ano, String cor) {
    public DadosDetalhamentoConserto(Conserto c) {
        this(c.getId(), c.getDataEntrada(), c.getDataSaida(), c.getMecanico().getNome(),
             c.getMecanico().getAnosExperiencia(), c.getVeiculo().getMarca(),
             c.getVeiculo().getModelo(), c.getVeiculo().getAno(), c.getVeiculo().getCor());
    }
}
