package br.com.devdolls.sos.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrdemDeServicoDTO {
    private Integer clienteId;
    private String descricao;
    private Integer prazo;
}
