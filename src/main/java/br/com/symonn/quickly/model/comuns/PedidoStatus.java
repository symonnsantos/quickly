package br.com.symonn.quickly.model.comuns;

import br.com.symonn.quickly.model.DefinicaoStatusEnum;
import lombok.Data;

import java.util.UUID;

@Data
public class PedidoStatus {
    private UUID id;
    private DefinicaoStatusEnum definicaoStatusEnum;
}
