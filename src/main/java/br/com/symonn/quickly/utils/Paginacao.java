package br.com.symonn.quickly.utils;

import lombok.Getter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Getter
@Component
public class Paginacao<T> implements Pageable, Serializable {

    private Integer pagina = 0;
    private Long totalItens = 0l;
    private List<T> conteudo;

    public Paginacao() {
    }

    public Paginacao(Integer pagina, Long totalItens, List<T> conteudo) {
        if (totalItens < 1) {
            throw new IllegalArgumentException("Limit must not be less than one!");
        }
        if (pagina < 0) {
            throw new IllegalArgumentException("Offset index must not be less than zero!");
        }
        this.pagina = Objects.nonNull(pagina) ? pagina : 0;
        this.totalItens = Objects.nonNull(totalItens) ? totalItens : 0;
        this.conteudo = conteudo;
    }

    @Override
    public boolean isPaged() {
        return Pageable.super.isPaged();
    }

    @Override
    public boolean isUnpaged() {
        return Pageable.super.isUnpaged();
    }

    @Override
    public int getPageNumber() {
        return pagina / totalItens.intValue();
    }

    @Override
    public int getPageSize() {
        return totalItens.intValue();
    }

    @Override
    public long getOffset() {
        return pagina;
    }

    @Override
    public Sort getSort() {
        return Sort.by(Sort.Direction.DESC, "id");
    }

    @Override
    public Sort getSortOr(Sort sort) {
        return Pageable.super.getSortOr(sort);
    }

    @Override
    public Pageable next() {
        return new Paginacao(getPageSize(), (getOffset() + getPageSize()), this.conteudo);
    }

    public Pageable previous() {
        return hasPrevious() ?
                new Paginacao(getPageSize(), (getOffset() - getPageSize()), this.conteudo) : this;
    }

    @Override
    public Pageable previousOrFirst() {
        return hasPrevious() ? previous() : first();
    }

    @Override
    public Pageable first() {
        return new Paginacao(getPageSize(), 0l, this.conteudo);
    }

    @Override
    public Pageable withPage(int pageNumber) {
        return Pageable.ofSize(pageNumber);
    }

    @Override
    public boolean hasPrevious() {
        return pagina > totalItens;
    }

    @Override
    public Optional<Pageable> toOptional() {
        return Pageable.super.toOptional();
    }
}