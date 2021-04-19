package br.com.laboratory.service;

import br.com.laboratory.entity.Exam;
import br.com.laboratory.entity.Laboratory;
import br.com.laboratory.entity.LaboratoryExam;
import br.com.laboratory.repository.ExamRepository;
import br.com.laboratory.repository.LaboratoryExamRepository;
import br.com.laboratory.repository.LaboratoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class ExameLaboratoryAssociateService {

    @Autowired
    private ExamRepository examRepository;

    @Autowired
    private LaboratoryRepository laboratoryRepository;

    @Autowired
    private LaboratoryExamRepository laboratoryExamRepository;

    public void associateExamWithLaboratory(long idExam,long idLaboratory) {

        log.info("Fazendo a associação do exame com o laboratorio idExam={} idLaboratory={}", idExam, idLaboratory);

        Exam examDB = examRepository.findById(idExam).orElseThrow(() -> {
            throw new EmptyResultDataAccessException("Não existe nenhum item salvo com esse id", 1) {
            };
        });

        Laboratory laboratoryDb = laboratoryRepository.findById(idLaboratory).orElseThrow(() -> {
            throw new EmptyResultDataAccessException("Não existe nenhum item salvo com esse id", 1) {
            };
        });

        LaboratoryExam laboratoryExam =
                LaboratoryExam
                        .builder()
                        .exam(examDB)
                        .laboratory(laboratoryDb)
                        .build();

        if(!laboratoryExam.canMakeAssociation()){
            throw  new IllegalArgumentException("O laboratorio ou exame não esta ativo exam={"+examDB.getStatus()+"}"+ " laboratory={"+laboratoryDb.getStatus()+"}");
        }

        laboratoryExamRepository.save(laboratoryExam);

        log.info("Associação do exame com o laboratorio feita com sucesso idExam={} idLaboratory={}", idExam, idLaboratory);


    }

    public void disassociateExamWithLaboratory(Long idLaboratory,Long idExam) {

        log.info("Fazendo a associação do exame com o laboratorio idExam={} idLaboratory={}", idExam, idLaboratory);

        LaboratoryExam laboratoryExam = Optional.of(laboratoryExamRepository.findByLaboratoryAndExam(idLaboratory,idExam)).orElseThrow( () -> {throw new EmptyResultDataAccessException("Não existe nenhum item salvo com esse id", 1) {
        };});


        if(!laboratoryExam.canMakeAssociation()){
            throw  new IllegalArgumentException("O laboratorio ou exame não esta ativo examId={"+idExam+"}"+ " laboratoryId={"+idLaboratory+"}");
        }

        laboratoryExamRepository.delete(laboratoryExam);

        log.info("Associação do exame com o laboratorio feita com sucesso idExam={} idLaboratory={}", idExam, idLaboratory);

    }


}
