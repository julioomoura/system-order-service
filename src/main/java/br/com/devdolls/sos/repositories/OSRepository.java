package br.com.devdolls.sos.repositories;

import br.com.devdolls.sos.entities.OrdemDeServico;
import br.com.devdolls.sos.entities.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OSRepository extends JpaRepository<OrdemDeServico, Integer> {
    List<OrdemDeServico> findAllByDesenvolvedorIdAndClienteIdAndStatus(Integer dev, Integer cliente, Status status);

    List<OrdemDeServico> findByDesenvolvedorIdAndClienteId(Integer dev, Integer cliente);

    List<OrdemDeServico> findByDesenvolvedorIdAndStatus(Integer dev, Status valueOf);

    List<OrdemDeServico> findByDesenvolvedorId(Integer dev);

    List<OrdemDeServico> findAllByClienteIdAndStatus(Integer cliente, Status valueOf);

    List<OrdemDeServico> findAllByClienteId(Integer cliente);

    List<OrdemDeServico> findAllByStatus(Status valueOf);
}
