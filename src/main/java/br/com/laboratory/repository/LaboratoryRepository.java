package br.com.laboratory.repository;

import br.com.laboratory.entity.Laboratory;
import br.com.laboratory.util.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LaboratoryRepository extends CrudRepository<Laboratory,Long> {

    Page<Laboratory> findByStatus(Status status, Pageable pageable);
}
