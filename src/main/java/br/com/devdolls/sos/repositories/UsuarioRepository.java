package br.com.devdolls.sos.repositories;

import br.com.devdolls.sos.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    List<Usuario> findAllByTipoNome(String nome);
}
