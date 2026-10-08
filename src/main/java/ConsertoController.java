import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/consertos")
public class ConsertoController {
    private final ConsertoRepository repository;

    public ConsertoController(ConsertoRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoConserto> cadastrar(
            @RequestBody @Valid DadosCadastroConserto dados,
            UriComponentsBuilder uriBuilder) {
        var conserto = new Conserto(dados);
        repository.save(conserto);
        var uri = uriBuilder.path("/consertos/{id}")
                .buildAndExpand(conserto.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhamentoConserto(conserto));
    }

    // Listagem completa paginada, conforme a etapa anterior.
    @GetMapping
    public ResponseEntity<Page<DadosDetalhamentoConserto>> listar(Pageable paginacao) {
        var pagina = repository.findAll(paginacao).map(DadosDetalhamentoConserto::new);
        return ResponseEntity.ok(pagina);
    }

    // Listagem resumida da atividade: inclui o ID e somente consertos ativos.
    @GetMapping("/algunsdados")
    public ResponseEntity<Page<DadosListagemConserto>> listarAlgunsDados(Pageable paginacao) {
        var pagina = repository.findAllByAtivoTrue(paginacao).map(DadosListagemConserto::new);
        return ResponseEntity.ok(pagina);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoConserto> buscarPorId(@PathVariable Long id) {
        var conserto = repository.findByIdAndAtivoTrue(id);
        if (conserto.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(new DadosDetalhamentoConserto(conserto.get()));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DadosDetalhamentoConserto> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid DadosAtualizacaoConserto dados) {
        var conserto = repository.findByIdAndAtivoTrue(id);
        if (conserto.isEmpty()) return ResponseEntity.notFound().build();
        conserto.get().atualizar(dados);
        return ResponseEntity.ok(new DadosDetalhamentoConserto(conserto.get()));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        var conserto = repository.findByIdAndAtivoTrue(id);
        if (conserto.isEmpty()) return ResponseEntity.notFound().build();
        conserto.get().excluir();
        return ResponseEntity.noContent().build();
    }
}
