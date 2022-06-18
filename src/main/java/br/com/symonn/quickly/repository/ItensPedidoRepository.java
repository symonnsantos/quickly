package br.com.symonn.quickly.repository;

import br.com.symonn.quickly.model.ItensPedido;
import br.com.symonn.quickly.model.ProdutoOuServico;
import br.com.symonn.quickly.utils.PaginationRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItensPedidoRepository extends PaginationRepository<ItensPedido> {

    List<ItensPedido> findByProdutoOuServico(ProdutoOuServico produtoOuServico);
}
