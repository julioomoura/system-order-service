package br.com.devdolls.sos.services.feedback;

import br.com.devdolls.sos.dtos.FeedbackDTO;
import br.com.devdolls.sos.entities.Feedback;
import br.com.devdolls.sos.entities.OrdemDeServico;
import br.com.devdolls.sos.entities.Usuario;
import br.com.devdolls.sos.repositories.FeedbackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class FeedbackServiceImpl implements FeedbackService{

    private final FeedbackRepository repository;


    @Override
    public Feedback cria(final FeedbackDTO dto, final OrdemDeServico ordemDeServico, final Usuario autor) {
        Feedback feedback = new Feedback();
        feedback.setAutor(autor);
        feedback.setDataDeCriacao(LocalDateTime.now());
        feedback.setOrdemDeServico(ordemDeServico);
        feedback.setDescricao(dto.getMensagem());

        return repository.save(feedback);
    }

    @Override
    public List<Feedback> buscaTodasPelaOrdemDeServico(final Integer ordemId) {
        return repository.findAllByOrdemDeServicoId(ordemId);
    }
}
