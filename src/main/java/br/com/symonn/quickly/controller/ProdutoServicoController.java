package br.com.symonn.quickly.controller;

import br.com.symonn.quickly.model.ProdutoOuServico;
import br.com.symonn.quickly.model.comuns.ProdutoServicoStatus;
import br.com.symonn.quickly.service.ProdutoServicoService;
import br.com.symonn.quickly.utils.Paginacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/produtoouservico")
public class ProdutoServicoController {

    @Autowired
    private ProdutoServicoService produtoServicoService;

    @PostMapping
    public ResponseEntity<String> criar(@Valid @RequestBody ProdutoOuServico produtoOuServico, HttpServletResponse response) {
        produtoServicoService.criar(produtoOuServico);
        return ResponseEntity.status(HttpStatus.CREATED).body("Produto/serviço cadastrado com sucesso. ID: ".concat(produtoOuServico.getId().toString()));
    }

    @GetMapping
    public Paginacao<ProdutoOuServico> buscaTodos(Integer pageIndex, Integer pageSize) {
        return produtoServicoService.buscaTodosprodutosServicos(pageIndex, pageSize);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoOuServico> buscaPeloId(@PathVariable String id) {
        return produtoServicoService.buscaPeloId(UUID.fromString(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<String> deletar(@PathVariable String id) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(produtoServicoService.deletar(UUID.fromString(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> atualizar(@PathVariable String id, @Valid @RequestBody ProdutoOuServico produtoOuServico) {
        return ResponseEntity.ok(produtoServicoService.atualizar(UUID.fromString(id), produtoOuServico));
    }

    @PutMapping("ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<String> setarAberto(@RequestBody ProdutoServicoStatus produtoServicoStatus) {
        produtoServicoService.setarAtivo(produtoServicoStatus.getId(), produtoServicoStatus.getAtivo());
        return ResponseEntity.status(HttpStatus.OK).body("Status do produto atualizado com sucesso.");
    }
}
