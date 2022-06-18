package br.com.symonn.quickly.model.comuns;

import lombok.Data;

import java.util.UUID;

@Data
public class ProdutoServicoStatus {
    private UUID id;
    private Boolean ativo;
}
