package com.mondorevive.TRESPOT.cauzione;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PaginationCauzioneRepository extends PagingAndSortingRepository<Cauzione, Long> , JpaSpecificationExecutor<Cauzione> {
}