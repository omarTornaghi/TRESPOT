package com.mondorevive.TRESPOT.pagination;

import com.mondorevive.TRESPOT.cauzione.PageResponse;
import com.mondorevive.TRESPOT.requests.PaginationRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PaginationService {
    public<T, L, K extends PagingAndSortingRepository<T,L> & JpaSpecificationExecutor<T>> PageResponse<T> getPage(Specification<T>specification, PaginationRequest request, K repository){
        Sort sort = request.getOrdinamento() != null && request.getOrdinamento().presente()? Sort.by(Sort.Direction.valueOf(request.getOrdinamento().getDirezione().toUpperCase()),request.getOrdinamento().getColonna()) : Sort.unsorted();
        PageRequest page = PageRequest.of(request.getPage(), request.getRowNumber(), sort);
        Page<T> pageDb = repository.findAll(specification, page);
        List<T> content = pageDb.getContent();
        return new PageResponse<>(pageDb,content);
    }
}
