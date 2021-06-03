package br.com.devdolls.sos.repositories;

import br.com.devdolls.sos.entities.OrdemDeServico;
import br.com.devdolls.sos.entities.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OSRepository extends JpaRepository<OrdemDeServico, Integer> {

    @Query("SELECT o FROM os o WHERE (:dev is null or o.desenvolvedor.id = :dev) and "
                                + "(:status is null or o.status = :status) and "
                                + "(:cliente is null or o.cliente.id = :cliente)")
    List<OrdemDeServico> findAllByDesenvolvedorIdAndClienteIdAndStatus(@Param("dev")Integer dev,
                                                                       @Param("cliente")Integer cliente,
                                                                       @Param("status") Status status);
}
