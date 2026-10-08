public record DadosListagemConserto(Long id, String dataEntrada, String dataSaida,
                                     String nomeMecanico, String marca, String modelo) {
    public DadosListagemConserto(Conserto c) {
        this(c.getId(), c.getDataEntrada(), c.getDataSaida(), c.getMecanico().getNome(),
             c.getVeiculo().getMarca(), c.getVeiculo().getModelo());
    }
}
