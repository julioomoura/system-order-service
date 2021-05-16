package br.com.devdolls.sos.repositories;

import br.com.devdolls.sos.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
}
