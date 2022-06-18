package br.com.symonn.quickly.repository;

import br.com.symonn.quickly.model.ProdutoOuServico;
import br.com.symonn.quickly.utils.PaginationRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoServicoRepository extends PaginationRepository<ProdutoOuServico> {
}
