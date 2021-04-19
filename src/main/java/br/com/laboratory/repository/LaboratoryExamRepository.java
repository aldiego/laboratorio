package br.com.laboratory.repository;

import br.com.laboratory.entity.LaboratoryExam;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LaboratoryExamRepository extends CrudRepository<LaboratoryExam,Long> {

    @Query(value = "select * from  tb_laboratory_exam  where laboratory_id = :idtLaboratory and exam_id = :idExam ",nativeQuery = true)
    LaboratoryExam findByLaboratoryAndExam(@Param("idtLaboratory") Long  idtLaboratory, @Param("idExam") Long  examId);
}
