package br.com.symonn.quickly.service;

import br.com.symonn.quickly.model.ItensPedido;
import br.com.symonn.quickly.model.DefinicaoStatusEnum;
import br.com.symonn.quickly.model.Pedido;
import br.com.symonn.quickly.repository.PedidoRepository;
import br.com.symonn.quickly.repository.ProdutoServicoRepository;
import br.com.symonn.quickly.utils.Paginacao;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

import static br.com.symonn.quickly.model.DefinicaoProdutoServicoEnum.PRODUTO;


@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ProdutoServicoRepository produtoServicoRepository;

    public String criarPedido(Pedido pedido, HttpServletResponse response) throws Exception {
        List<ItensPedido> itens = pedido.getItensPedido();
        for (ItensPedido item : itens) {
            if(produtoServicoRepository.findById(item.getProdutoOuServico().getId()).get().getAtivo().equals(false)){
                return "Não é possível fechar pedido com o produto inativo. ID:".concat(item.getProdutoOuServico().getId().toString());
            }
            produtoServicoRepository.findById(item.getProdutoOuServico().getId()).ifPresent(produtoOuServico -> {
                if(Objects.nonNull(item.getDesconto()) && produtoOuServico.getDefinicaoProdutoServicoEnum().equals(PRODUTO) && pedido.getDefinicaoStatusEnum().equals(DefinicaoStatusEnum.ABERTO)){
                    BigDecimal desconto = produtoOuServico.getValor().multiply(new BigDecimal(item.getDesconto()).divide(BigDecimal.valueOf(100l)));
                    item.setValor(produtoOuServico.getValor().subtract(desconto));
                } else {
                    item.setValor(produtoOuServico.getValor());
                }
            });
        }
        pedidoRepository.save(pedido);
        return "Pedido cadastrado com sucesso!";
    }

    public ResponseEntity<Pedido> buscaPedidoPeloId(UUID id){
        Optional<Pedido> pedido = pedidoRepository.findById(id);
        return pedido.isPresent() ? ResponseEntity.ok(pedido.get()) : ResponseEntity.notFound().build();
    }

    public String atualizar(Pedido pedido) throws Exception {
        AtomicReference<String> retorno = new AtomicReference<>("");
        if(pedido.getDefinicaoStatusEnum().equals(DefinicaoStatusEnum.ABERTO)){
            Optional<Pedido> pedidoSalvo = pedidoRepository.findById(pedido.getId());
            pedidoSalvo.ifPresentOrElse(demanda -> {
                BeanUtils.copyProperties(pedido, demanda, "id");
                pedidoRepository.save(demanda);
                retorno.set("Pedido atualizado com sucesso");
            }, () -> {
                retorno.set("Não foi possível atualizar o pedido");
            });
        } else {
            retorno.set("Não é possível editar um pedido com status 'aberto'");
        }
        return retorno.get();
    }

    public String setStatus(UUID id, DefinicaoStatusEnum definicaoStatusEnum) {
        AtomicReference<String> retorno = new AtomicReference<>("");
        Optional<Pedido> pedidoSalvo = pedidoRepository.findById(id);
        pedidoSalvo.ifPresentOrElse(demanda -> {
            demanda.setDefinicaoStatusEnum(definicaoStatusEnum);
            pedidoRepository.save(demanda);
            retorno.set("Status atualizado com sucesso.");
        }, () -> {
            retorno.set("Não foi possível atualizar a definição do status.");
        });
        return retorno.get();
    }

    public void deletar(UUID id){
        pedidoRepository.deleteById(id);
    }

    public Paginacao<Pedido> buscaTodosPedidos(Integer pageIndex, Integer pageSize) {
        Page page = pedidoRepository.findPageable(pageIndex, pageSize);
        return new Paginacao<Pedido>(pageIndex, page.getTotalElements(), page.getContent());
    }
}
