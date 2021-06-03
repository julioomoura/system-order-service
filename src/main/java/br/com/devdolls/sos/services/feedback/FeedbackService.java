package br.com.devdolls.sos.services.feedback;

import br.com.devdolls.sos.dtos.FeedbackDTO;
import br.com.devdolls.sos.entities.Feedback;
import br.com.devdolls.sos.entities.OrdemDeServico;
import br.com.devdolls.sos.entities.Usuario;

import java.util.List;

public interface FeedbackService {
    Feedback cria(FeedbackDTO dto, OrdemDeServico ordemDeServico, Usuario autor);
    List<Feedback> buscaTodasPelaOrdemDeServico(Integer ordemId);
}
