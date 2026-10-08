import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record DadosCadastroConserto(
    @NotBlank @Pattern(regexp = "\\d{2}/\\d{2}/\\d{4}", message = "Use o formato dd/MM/yyyy") String dataEntrada,
    @Pattern(regexp = "\\d{2}/\\d{2}/\\d{4}", message = "Use o formato dd/MM/yyyy") String dataSaida,
    @NotNull @Valid DadosMecanico mecanico,
    @NotNull @Valid DadosVeiculo veiculo
) {
    public record DadosMecanico(
        @NotBlank String nome,
        @NotNull Integer anosExperiencia
    ) {}

    public record DadosVeiculo(
        @NotBlank String marca,
        @NotBlank String modelo,
        @NotBlank @Pattern(regexp = "\\d{4}", message = "Informe o ano com quatro dígitos") String ano,
        String cor
    ) {}
}
