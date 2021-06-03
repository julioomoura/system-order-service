package br.com.devdolls.sos.services.os;

import br.com.devdolls.sos.dtos.OrdemDeServicoDTO;
import br.com.devdolls.sos.entities.OrdemDeServico;
import br.com.devdolls.sos.entities.Usuario;
import br.com.devdolls.sos.entities.enums.Status;
import br.com.devdolls.sos.repositories.OSRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OSServiceImpl implements OSService {

    private final OSRepository repository;

    @Override
    public List<OrdemDeServico> buscaTodos(final Integer dev, final Integer cliente, final Status status) {
        return repository.findAllByDesenvolvedorIdAndClienteIdAndStatus(dev, cliente, status);
    }

    @Override
    public Optional<OrdemDeServico> buscaPorId(final Integer id) {
        return repository.findById(id);
    }

    @Override
    public OrdemDeServico cria(final OrdemDeServicoDTO dto, final Usuario cliente) {
        OrdemDeServico os = new OrdemDeServico();
        os.setDescricao(dto.getDescricao());
        os.setDataAbertura(LocalDate.now());
        os.setStatus(Status.ABERTA);
        os.setCliente(cliente);

        return repository.save(os);
    }

    @Override
    @Transactional
    public OrdemDeServico atribuiADev(final Integer id, final Usuario dev, final OrdemDeServicoDTO dto) {
        return repository.findById(id).map(ordemDeServico -> {
            ordemDeServico.setDesenvolvedor(dev);
            ordemDeServico.setDataInicioAtendimento(LocalDate.now());
            ordemDeServico.setPrazoParaConclusao(dto.getPrazo());
            ordemDeServico.setStatus(Status.DESENVOLVIMENTO);
            return ordemDeServico;
        }).orElse(null);
    }

    @Override
    @Transactional
    public void fechar(final Integer id) {
        repository.findById(id).ifPresent(os -> {
            os.setStatus(Status.FECHADA);
            os.setDataFechamento(LocalDate.now());
        });
    }
}
