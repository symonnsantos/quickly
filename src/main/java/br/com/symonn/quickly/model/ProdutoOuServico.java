package br.com.symonn.quickly.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "associacao_produto_servico")
public class ProdutoOuServico {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Column(name = "nome", length = 100, nullable = false)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(name = "definicao_produto_servico", nullable = false)
    private DefinicaoProdutoServicoEnum definicaoProdutoServicoEnum;

    @Column(name = "ativo")
    private Boolean ativo;

    @Column(name = "valor")
    private BigDecimal valor;
}
