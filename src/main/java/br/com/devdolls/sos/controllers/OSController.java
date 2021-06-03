package br.com.devdolls.sos.controllers;

import br.com.devdolls.sos.dtos.FeedbackDTO;
import br.com.devdolls.sos.dtos.OrdemDeServicoDTO;
import br.com.devdolls.sos.entities.Feedback;
import br.com.devdolls.sos.entities.OrdemDeServico;
import br.com.devdolls.sos.entities.Usuario;
import br.com.devdolls.sos.entities.enums.Status;
import br.com.devdolls.sos.repositories.FeedbackRepository;
import br.com.devdolls.sos.repositories.OSRepository;
import br.com.devdolls.sos.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
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

    @PostMapping
    public ResponseEntity<OrdemDeServico> criarOrdemDeServico(@RequestBody final OrdemDeServicoDTO dto) {

        OrdemDeServico os = new OrdemDeServico();
        os.setDescricao(dto.getDescricao());
        os.setDataAbertura(LocalDate.now());
        os.setStatus(Status.ABERTA);

        Optional<Usuario> user = usuarioRepository.findById(dto.getClienteId());

        return user.map(usuario -> {
                    os.setCliente(usuario);
                    OrdemDeServico saved = repository.save(os);
                    return ResponseEntity.ok(saved);
                }
        ).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<OrdemDeServico>> buscarOrdensDeServico(
            @RequestParam(name = "dev", required = false) final Integer dev,
            @RequestParam(name = "cliente", required = false) final Integer cliente,
            @RequestParam(name = "status", required = false) final String status
    ) {
        Status convertedStatus = Optional.ofNullable(status).map(Status::valueOf).orElse(null);

        return ResponseEntity.ok(repository.findAllByDesenvolvedorIdAndClienteIdAndStatus(dev, cliente,convertedStatus));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrdemDeServico> buscarOrdemDeServico(@PathVariable("id") final Integer id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{ordemId}/devs/{devId}")
    @Transactional
    public ResponseEntity<OrdemDeServico> distribuirOrdemDeServico(@PathVariable("ordemId") final Integer ordemId,
                                                                   @PathVariable("devId") final Integer devId,
                                                                   @RequestBody final OrdemDeServicoDTO dto) {

        Optional<Usuario> usuarioOptional = usuarioRepository.findById(devId);
        Optional<OrdemDeServico> ordemOptional = repository.findById(ordemId);

        if (usuarioOptional.isEmpty() || ordemOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        ordemOptional.get().setDesenvolvedor(usuarioOptional.get());
        ordemOptional.get().setDataInicioAtendimento(LocalDate.now());
        ordemOptional.get().setPrazoParaConclusao(dto.getPrazo());

        return ResponseEntity.ok(ordemOptional.get());
    }

    @GetMapping("/{ordemId}/feedbacks")
    public ResponseEntity<List<Feedback>> retornaFeedbacksDaOs(@PathVariable("ordemId") final Integer ordemId) {
        List<Feedback> feedbacks = feedBackRepository.findAllByOrdemDeServicoId(ordemId);
        return ResponseEntity.ok(feedbacks);
    }

    @PostMapping("/{ordemId}/feedbacks")
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

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> fecharOs(@PathVariable Integer id) {
        repository.findById(id).ifPresent(os -> os.setStatus(Status.FECHADA));

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/usuarios")
    public ResponseEntity<List<Usuario>> buscaUsuarios(@RequestParam(value = "nome", required = false) final String nome,
                                                       @RequestParam(value = "email", required = false) final String email) {

        return ResponseEntity.ok(usuarioRepository.findByNomeAndEmail(nome, email));
    }
}
