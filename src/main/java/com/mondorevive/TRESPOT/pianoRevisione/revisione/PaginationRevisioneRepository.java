package com.mondorevive.TRESPOT.pianoRevisione.revisione;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PaginationRevisioneRepository extends PagingAndSortingRepository<Revisione, Long>,
        JpaSpecificationExecutor<Revisione> {
}