package br.com.devdolls.sos.controllers;

import br.com.devdolls.sos.entities.Usuario;
import br.com.devdolls.sos.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/devs")
@RequiredArgsConstructor
public class DevController {

    private final UsuarioRepository usuarioRepository;

    @GetMapping
    public ResponseEntity<List<Usuario>> buscaDesenvolvedores() {

        return ResponseEntity.ok(usuarioRepository.findAllByTipoNome("dev"));
    }
}
