package br.com.symonn.quickly.repository;

import br.com.symonn.quickly.model.ItensPedido;
import br.com.symonn.quickly.model.Pedido;
import br.com.symonn.quickly.utils.PaginationRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends PaginationRepository<Pedido> {
    @Query("select p from Pedido p where p.itensPedido in(:itensPedido)")
    List<Pedido> findByItensPedido(ItensPedido itensPedido);
}
