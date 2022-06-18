package br.com.symonn.quickly.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "pedido")
public class Pedido {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Column(name = "nome", length = 100, nullable = false)
    private String nome;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private DefinicaoStatusEnum definicaoStatusEnum;

    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn(name = "pedido_fk")
    private List<ItensPedido> itensPedido;
}
