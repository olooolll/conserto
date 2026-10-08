import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ConsertoRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public Conserto save(Conserto conserto) {
        entityManager.persist(conserto);
        return conserto;
    }

    public Page<Conserto> findAll(Pageable paginacao) {
        return buscarPagina("select c from Conserto c", "select count(c) from Conserto c", paginacao);
    }

    public Page<Conserto> findAllByAtivoTrue(Pageable paginacao) {
        return buscarPagina("select c from Conserto c where c.ativo = true",
                "select count(c) from Conserto c where c.ativo = true", paginacao);
    }

    public Optional<Conserto> findByIdAndAtivoTrue(Long id) {
        return entityManager.createQuery(
                        "select c from Conserto c where c.id = :id and c.ativo = true", Conserto.class)
                .setParameter("id", id)
                .getResultStream()
                .findFirst();
    }

    private Page<Conserto> buscarPagina(String jpql, String jpqlTotal, Pageable paginacao) {
        var consulta = entityManager.createQuery(jpql + " order by c.id", Conserto.class);
        if (paginacao.isPaged()) {
            consulta.setFirstResult(Math.toIntExact(paginacao.getOffset()));
            consulta.setMaxResults(paginacao.getPageSize());
        }

        var total = entityManager.createQuery(jpqlTotal, Long.class).getSingleResult();
        return new PageImpl<>(consulta.getResultList(), paginacao, total);
    }
}
