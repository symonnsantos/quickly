package br.com.symonn.quickly.controller;

import br.com.symonn.quickly.model.Pedido;
import br.com.symonn.quickly.model.comuns.PedidoStatus;
import br.com.symonn.quickly.service.PedidoService;
import br.com.symonn.quickly.utils.Paginacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/pedido")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<String> criarPedido(@Valid @RequestBody Pedido pedido, HttpServletResponse response) throws Exception {
        return ResponseEntity.ok(pedidoService.criarPedido(pedido, response));
    }

    @GetMapping
    public Paginacao<Pedido> buscaTodos(Integer pageIndex, Integer pageSize){
        return pedidoService.buscaTodosPedidos(pageIndex, pageSize);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> buscaPeloId(@PathVariable String id){
        return pedidoService.buscaPedidoPeloId(UUID.fromString(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<String> deletar(@PathVariable String id){
        pedidoService.deletar(UUID.fromString(id));
        return ResponseEntity.status(HttpStatus.OK).body("Pedido excluído com sucesso");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> atualizar(@Valid @RequestBody Pedido pedido) throws Exception {
        return ResponseEntity.ok(pedidoService.atualizar(pedido));
    }

    @PutMapping("status")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<String> setarAberto(@RequestBody PedidoStatus pedidoStatus) {
        return ResponseEntity.status(HttpStatus.OK).body(pedidoService.setStatus(pedidoStatus.getId(), pedidoStatus.getDefinicaoStatusEnum()));
    }
}
