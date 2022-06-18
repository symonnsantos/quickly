package br.com.symonn.quickly.service;

import br.com.symonn.quickly.model.ItensPedido;
import br.com.symonn.quickly.model.ProdutoOuServico;
import br.com.symonn.quickly.repository.ItensPedidoRepository;
import br.com.symonn.quickly.repository.PedidoRepository;
import br.com.symonn.quickly.repository.ProdutoServicoRepository;
import br.com.symonn.quickly.utils.Paginacao;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class ProdutoServicoService {

    @Autowired
    private ProdutoServicoRepository produtoServicoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ItensPedidoRepository itensPedidoRepository;

    public void criar(ProdutoOuServico produtoOuServico) {
        produtoServicoRepository.save(produtoOuServico);
    }

    public ResponseEntity<ProdutoOuServico> buscaPeloId(UUID id) {
        Optional<ProdutoOuServico> produtoServico = produtoServicoRepository.findById(id);
        return produtoServico.isPresent() ? ResponseEntity.ok(produtoServico.get()) : ResponseEntity.notFound().build();
    }

    public String atualizar(UUID id, ProdutoOuServico produtoOuServico) {
        AtomicReference<String> retorno = new AtomicReference<>("");
        Optional<ProdutoOuServico> associacao = produtoServicoRepository.findById(produtoOuServico.getId());
        associacao.ifPresent(produto -> {
            BeanUtils.copyProperties(produtoOuServico, produto, "id");
            produtoServicoRepository.save(produto);
            retorno.set("Produto/serviço atualizado com sucesso");
        });
        return retorno.get();
    }

    public String deletar(@PathVariable UUID id) throws Exception {
        AtomicReference<String> retorno = new AtomicReference<>("");
        ProdutoOuServico produtoOuServico = new ProdutoOuServico();
        produtoOuServico.setId(id);
        List<ItensPedido> pedidos = itensPedidoRepository.findByProdutoOuServico(produtoOuServico);
        if (!pedidos.isEmpty()) {
            retorno.set("Não é possível excluir um produto ou serviço associado a algum pedido.");
        } else {
            produtoServicoRepository.deleteById(id);
            retorno.set("Produto/serviço deletado com sucesso.");
        }
        return retorno.get();
    }

    public void setarAtivo(UUID id, Boolean ativo) {
        Optional<ProdutoOuServico> produtoServicoSalvo = produtoServicoRepository.findById(id);
        produtoServicoSalvo.ifPresent(produtoOuServico -> {
            produtoOuServico.setAtivo(ativo);
            produtoServicoRepository.save(produtoOuServico);
        });
    }

    public Paginacao<ProdutoOuServico> buscaTodosprodutosServicos(Integer pageIndex, Integer pageSize) {
        Page page = produtoServicoRepository.findPageable(pageIndex, pageSize);
        return new Paginacao<ProdutoOuServico>(pageIndex, page.getTotalElements(), page.getContent());
    }
}
