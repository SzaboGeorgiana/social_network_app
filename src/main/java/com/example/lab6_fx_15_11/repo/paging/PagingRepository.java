package com.example.lab6_fx_15_11.repo.paging;

import com.example.lab6_fx_15_11.domain.Entity;
import com.example.lab6_fx_15_11.repo.Repo;
import com.example.lab6_fx_15_11.repo.UtilozatorDBRepo;

public interface PagingRepository<ID ,
        E extends Entity<ID>>
        extends Repo<ID, E> {

    Page<E> findAll(Pageable pageable);   // Pageable e un fel de paginator
}
