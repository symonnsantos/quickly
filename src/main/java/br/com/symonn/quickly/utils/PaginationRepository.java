package br.com.symonn.quickly.utils;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Objects;
import java.util.UUID;

@NoRepositoryBean
public interface PaginationRepository<T> extends JpaRepository<T, UUID> {
    default Page<T> findPageable(Integer pageIndex, Integer totalItens){
        pageIndex = Objects.nonNull(pageIndex) ? pageIndex : 0;
        totalItens = Objects.nonNull(totalItens) ? totalItens : 15;
        return findAll(PageRequest.of(pageIndex, totalItens));
    }
}
