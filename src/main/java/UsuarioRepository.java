import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
@Repository
public class UsuarioRepository {
    @PersistenceContext
    private EntityManager entityManager;
    public Usuario findByLogin(String login) {
        var lista = entityManager.createQuery(
                        "select u from Usuario u where u.login = :login", Usuario.class)
                .setParameter("login", login)
                .getResultList();
        return lista.isEmpty() ? null : lista.get(0);
    }
}
