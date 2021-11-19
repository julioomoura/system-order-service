package br.com.devdolls.sos.services;

import br.com.devdolls.sos.dtos.OrdemDeServicoDTO;
import br.com.devdolls.sos.entities.OrdemDeServico;
import br.com.devdolls.sos.entities.TipoUsuario;
import br.com.devdolls.sos.entities.Usuario;
import br.com.devdolls.sos.entities.enums.Status;
import br.com.devdolls.sos.repositories.OSRepository;
import br.com.devdolls.sos.services.os.OSServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class OSServiceTest {

    @InjectMocks
    private OSServiceImpl service;

    @Mock
    private OSRepository repository;

    @Test
    public void criaDeveRetornarUmaOrdemDeServico() {
        when(repository.save(any())).thenReturn(retornaOrdemDeServico());
        OrdemDeServico ordemDeServico = service.cria(retornaOrdemDeServicoDto(), retornaUsuario());
        Assertions.assertNotNull(ordemDeServico);
    }

    @Test
    public void atribuiADevDevTerUmDevAssociado() {
        OrdemDeServico os = retornaOrdemDeServico();
        os.setStatus(Status.ABERTA);
        when(repository.save(any())).thenReturn(os);
        when(repository.findById(anyInt())).thenReturn(Optional.of(os));
        OrdemDeServico ordemDeServico = service.atribuiADev(1, retornaUsuario(), retornaOrdemDeServicoDto());
        Assertions.assertNotNull(ordemDeServico.getDesenvolvedor());
        Assertions.assertEquals(Status.DESENVOLVIMENTO, ordemDeServico.getStatus());
    }

    @Test
    public void fecharOsDeveTerStatusFechada() {
        OrdemDeServico os = retornaOrdemDeServico();
        os.setStatus(Status.FECHADA);
        when(repository.save(any())).thenReturn(os);
        when(repository.findById(anyInt())).thenReturn(Optional.of(os));
        service.fechar(1);
    }

    private Usuario retornaUsuario() {
        Usuario usuario = new Usuario();
        usuario.setEmail("email@mail.com");
        usuario.setNome("nome");
        usuario.setTipo(new TipoUsuario());
        return usuario;
    }

    private OrdemDeServicoDTO retornaOrdemDeServicoDto() {
        OrdemDeServicoDTO ordemDeServicoDTO = new OrdemDeServicoDTO();
        ordemDeServicoDTO.setDescricao("Descrição");
        ordemDeServicoDTO.setPrazo(10);
        ordemDeServicoDTO.setClienteId(1);
        return ordemDeServicoDTO;
    }

    private OrdemDeServico retornaOrdemDeServico() {
        OrdemDeServico ordemDeServico = new OrdemDeServico();
        ordemDeServico.setStatus(Status.ABERTA);
        ordemDeServico.setCliente(retornaUsuario());
        ordemDeServico.setDesenvolvedor(retornaUsuario());
        ordemDeServico.setDataAbertura(LocalDate.now());
        ordemDeServico.setDataFechamento(LocalDate.now());
        ordemDeServico.setDataInicioAtendimento(LocalDate.now());
        ordemDeServico.setDescricao("Descrição");
        ordemDeServico.setAssunto("Assunto");
        ordemDeServico.setJustificativa("Justificativa");
        ordemDeServico.setPrazoParaConclusao(10);
        return ordemDeServico;
    }
}
