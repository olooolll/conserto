import jakarta.persistence.Embeddable;

@Embeddable
public class Mecanico {
    private String nome;
    private Integer anosExperiencia;

    protected Mecanico() {
        // Construtor exigido pelo JPA.
    }

    public Mecanico(String nome, Integer anosExperiencia) {
        this.nome = nome;
        this.anosExperiencia = anosExperiencia;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getAnosExperiencia() {
        return anosExperiencia;
    }

    public void setAnosExperiencia(Integer anosExperiencia) {
        this.anosExperiencia = anosExperiencia;
    }
}
