import jakarta.validation.constraints.Pattern;

public record DadosAtualizacaoConserto(
    @Pattern(regexp = "\\d{2}/\\d{2}/\\d{4}", message = "Use o formato dd/MM/yyyy") String dataSaida,
    String nomeMecanico,
    Integer anosExperienciaMecanico
) {}
