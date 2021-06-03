package br.com.devdolls.sos.controllers;

import br.com.devdolls.sos.dtos.FeedbackDTO;
import br.com.devdolls.sos.dtos.OrdemDeServicoDTO;
import br.com.devdolls.sos.entities.Feedback;
import br.com.devdolls.sos.entities.OrdemDeServico;
import br.com.devdolls.sos.entities.Usuario;
import br.com.devdolls.sos.entities.enums.Status;
import br.com.devdolls.sos.repositories.UsuarioRepository;
import br.com.devdolls.sos.services.feedback.FeedbackService;
import br.com.devdolls.sos.services.os.OSService;
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

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ordens")
@RequiredArgsConstructor
public class OSController {

    private final OSService osService;
    private final UsuarioRepository usuarioRepository;
    private final FeedbackService feedbackService;

    @PostMapping
    public ResponseEntity<OrdemDeServico> criarOrdemDeServico(@RequestBody final OrdemDeServicoDTO dto) {

        Optional<Usuario> user = usuarioRepository.findById(dto.getClienteId());

        return user.map(usuario -> ResponseEntity.ok(osService.cria(dto, usuario)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<OrdemDeServico>> buscarOrdensDeServico(
            @RequestParam(name = "dev", required = false) final Integer dev,
            @RequestParam(name = "cliente", required = false) final Integer cliente,
            @RequestParam(name = "status", required = false) final String status
    ) {
        Status convertedStatus = Optional.ofNullable(status).map(Status::valueOf).orElse(null);

        return ResponseEntity.ok(osService.buscaTodos(dev, cliente, convertedStatus));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrdemDeServico> buscarOrdemDeServico(@PathVariable("id") final Integer id) {
        return osService.buscaPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{ordemId}/devs/{devId}")
    @Transactional
    public ResponseEntity<OrdemDeServico> distribuirOrdemDeServico(@PathVariable("ordemId") final Integer ordemId,
                                                                   @PathVariable("devId") final Integer devId,
                                                                   @RequestBody final OrdemDeServicoDTO dto) {

        Optional<Usuario> usuarioOptional = usuarioRepository.findById(devId);

        if (usuarioOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        OrdemDeServico ordemDeServico = osService.atribuiADev(ordemId, usuarioOptional.get(), dto);

        if (ordemDeServico == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(ordemDeServico);
    }

    @GetMapping("/{ordemId}/feedbacks")
    public ResponseEntity<List<Feedback>> retornaFeedbacksDaOs(@PathVariable("ordemId") final Integer ordemId) {
        return ResponseEntity.ok(feedbackService.buscaTodasPelaOrdemDeServico(ordemId));
    }

    @PostMapping("/{ordemId}/feedbacks")
    public ResponseEntity<Feedback> postarFeedback(@RequestBody final FeedbackDTO feedbackDTO,
                                                   @PathVariable("ordemId") final Integer ordemId) {

        Optional<Usuario> usuarioOptional = usuarioRepository.findById(feedbackDTO.getAutorId());
        Optional<OrdemDeServico> ordemOptional = osService.buscaPorId(ordemId);

        if (usuarioOptional.isEmpty() || ordemOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(feedbackService.cria(feedbackDTO, ordemOptional.get(), usuarioOptional.get()));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> fecharOs(@PathVariable Integer id) {
        osService.fechar(id);
        return ResponseEntity.noContent().build();
    }
}
