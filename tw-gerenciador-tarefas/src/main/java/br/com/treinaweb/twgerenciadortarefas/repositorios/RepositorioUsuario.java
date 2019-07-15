package br.com.treinaweb.twgerenciadortarefas.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.treinaweb.twgerenciadortarefas.modelos.Usuario;

public interface RepositorioUsuario extends JpaRepository<Usuario, Long> {

	Usuario findByEmail(String email);
}
