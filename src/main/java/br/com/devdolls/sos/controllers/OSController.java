package br.com.devdolls.sos.controllers;

import br.com.devdolls.sos.dtos.FeedbackDTO;
import br.com.devdolls.sos.entities.Feedback;
import br.com.devdolls.sos.entities.OrdemDeServico;
import br.com.devdolls.sos.entities.Usuario;
import br.com.devdolls.sos.repositories.FeedbackRepository;
import br.com.devdolls.sos.repositories.OSRepository;
import br.com.devdolls.sos.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ordens")
@RequiredArgsConstructor
public class OSController {

    private final OSRepository repository;
    private final UsuarioRepository usuarioRepository;
    private final FeedbackRepository feedBackRepository;

    @GetMapping
    public ResponseEntity<List<OrdemDeServico>> buscarOrdensDeServico(@RequestParam(name = "dev", required = false) final Integer dev) {
        if (dev != null) {
            return ResponseEntity.ok(repository.findAllByDesenvolvedorId(dev));
        } else {
            return ResponseEntity.ok(repository.findAll());
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<OrdemDeServico> buscarOrdemDeServico(@PathVariable("id") final Integer id) {
        Optional<OrdemDeServico> optionalOrdemDeServico = repository.findById(id);

        return ResponseEntity.ok(optionalOrdemDeServico.get());
    }

    @PutMapping("/{ordemId}/devs/{devId}")
    public ResponseEntity<OrdemDeServico> distribuirOrdemDeServico(@PathVariable("ordemId") final Integer ordemId,
                                                                   @PathVariable("devId") final Integer devId) {

        Optional<Usuario> usuarioOptional = usuarioRepository.findById(devId);
        Optional<OrdemDeServico> ordemOptional = repository.findById(ordemId);

        // TODO() Muda status da OS

        if (usuarioOptional.isEmpty() || ordemOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        ordemOptional.get().setDesenvolvedor(usuarioOptional.get());
        ordemOptional.get().setDataInicioAtendimento(LocalDate.now());

        return ResponseEntity.ok(ordemOptional.get());
    }

    @GetMapping("/{ordemId}/feedbacks")
    public ResponseEntity<List<Feedback>> retornaFeedbacksDaOs(@PathVariable("ordemId") final Integer ordemId) {
        List<Feedback> feedbacks = feedBackRepository.findAllByOsId(ordemId);
        return ResponseEntity.ok(feedbacks);
    }

    @PostMapping("/{ordemId}")
    public ResponseEntity<Feedback> postarFeedback(@RequestBody final FeedbackDTO feedbackDTO,
                                                   @PathVariable("ordemId") final Integer ordemId) {

        Optional<Usuario> usuarioOptional = usuarioRepository.findById(feedbackDTO.getAutorId());
        Optional<OrdemDeServico> ordemOptional = repository.findById(ordemId);

        if (usuarioOptional.isEmpty() || ordemOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Feedback feedback = new Feedback();
        feedback.setAutor(usuarioOptional.get());
        feedback.setDataDeCriacao(LocalDateTime.now());
        feedback.setOrdemDeServico(ordemOptional.get());
        feedback.setDescricao(feedbackDTO.getMensagem());

        Feedback saved = feedBackRepository.save(feedback);

        return ResponseEntity.ok(saved);
    }
}
