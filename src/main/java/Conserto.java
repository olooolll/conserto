import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "consertos")
public class Conserto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data_entrada", nullable = false)
    private String dataEntrada;

    @Column(name = "data_saida")
    private String dataSaida;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "nome", column = @Column(name = "mecanico_nome", nullable = false)),
        @AttributeOverride(name = "anosExperiencia", column = @Column(name = "mecanico_anos_experiencia", nullable = false))
    })
    private Mecanico mecanico;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "marca", column = @Column(name = "veiculo_marca", nullable = false)),
        @AttributeOverride(name = "modelo", column = @Column(name = "veiculo_modelo", nullable = false)),
        @AttributeOverride(name = "ano", column = @Column(name = "veiculo_ano", nullable = false)),
        @AttributeOverride(name = "cor", column = @Column(name = "veiculo_cor"))
    })
    private Veiculo veiculo;

    @Column(nullable = false)
    private boolean ativo = true;

    protected Conserto() {
        // Construtor exigido pelo JPA.
    }

    public Conserto(DadosCadastroConserto dados) {
        this.dataEntrada = dados.dataEntrada();
        this.dataSaida = dados.dataSaida();
        this.mecanico = new Mecanico(dados.mecanico().nome(), dados.mecanico().anosExperiencia());
        this.veiculo = new Veiculo(dados.veiculo().marca(), dados.veiculo().modelo(), dados.veiculo().ano(), dados.veiculo().cor());
        this.ativo = true;
    }

    public void atualizar(DadosAtualizacaoConserto dados) {
        if (dados.dataSaida() != null) this.dataSaida = dados.dataSaida();
        if (dados.nomeMecanico() != null) this.mecanico.setNome(dados.nomeMecanico());
        if (dados.anosExperienciaMecanico() != null) this.mecanico.setAnosExperiencia(dados.anosExperienciaMecanico());
    }

    public void excluir() {
        this.ativo = false;
    }

    public Long getId() {
        return id;
    }

    public String getDataEntrada() {
        return dataEntrada;
    }

    public String getDataSaida() {
        return dataSaida;
    }

    public Mecanico getMecanico() {
        return mecanico;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public boolean isAtivo() {
        return ativo;
    }
}
