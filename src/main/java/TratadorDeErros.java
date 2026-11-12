import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.validation.FieldError;

@RestControllerAdvice
public class TratadorDeErros {
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<String> tratarErro401(AuthenticationException ex) {
        return ResponseEntity.status(401).body("Credenciais inválidas");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> tratarErro400(MethodArgumentNotValidException ex) {
        var lista = ex.getFieldErrors().stream().map(DadosErroValidacao::new).toList();
        return ResponseEntity.badRequest().body(lista);
    }

    private record DadosErroValidacao(String campo, String msgErro) {
        public DadosErroValidacao(FieldError erro) {
            this(erro.getField(), erro.getDefaultMessage());
        }
    }
}
