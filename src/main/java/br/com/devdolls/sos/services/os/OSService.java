package br.com.devdolls.sos.services.os;

import br.com.devdolls.sos.dtos.OrdemDeServicoDTO;
import br.com.devdolls.sos.entities.OrdemDeServico;
import br.com.devdolls.sos.entities.Usuario;
import br.com.devdolls.sos.entities.enums.Status;

import java.util.List;
import java.util.Optional;

public interface OSService {
    List<OrdemDeServico> buscaTodos(Integer dev, Integer cliente, Status status);
    Optional<OrdemDeServico> buscaPorId(Integer id);
    OrdemDeServico cria(OrdemDeServicoDTO dto, Usuario cliente);
    OrdemDeServico atribuiADev(Integer id, Usuario dev, OrdemDeServicoDTO dto);
    void fechar(Integer id);
}
