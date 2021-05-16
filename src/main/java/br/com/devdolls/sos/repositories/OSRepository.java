package br.com.devdolls.sos.repositories;

import br.com.devdolls.sos.entities.OrdemDeServico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OSRepository extends JpaRepository<OrdemDeServico, Integer> {

    List<OrdemDeServico> findAllByDesenvolvedorId(Integer devId);
}
