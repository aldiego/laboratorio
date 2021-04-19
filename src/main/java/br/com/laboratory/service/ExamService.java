package br.com.laboratory.service;

import br.com.laboratory.entity.Exam;
import br.com.laboratory.repository.ExamRepository;
import br.com.laboratory.util.Status;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Slf4j
@Service
public class ExamService  {

    @Autowired
    private ExamRepository examRepository;

    @Value("${laboratory.request.max-item-per-page}")
    private int maxItemsPerPage;

    private String messageErrorExamNotNull = "O exame não pode ser null para salvar";


    public Exam persist(Exam exam){

        Assert.notNull(exam,messageErrorExamNotNull);

        log.info("Salvando um axame no banco exam={}",exam);

        exam.setStatus(Status.ACTIVE);

        Exam examPersist = examRepository.save(exam);

        log.info("Exame salvo exam={} id ={}",exam,examPersist.getId());
        return examPersist;
    }

    public Exam update(Exam exam, long id){

        Assert.notNull(exam,messageErrorExamNotNull);

        log.info("Fazendo o update de um axame  exam={} id={}",exam,id);
        Exam examDB = examRepository.findById(id).orElseThrow(()->{ throw  new EmptyResultDataAccessException("Não existe nenhum item salvo",1){};});

        examDB.changeObjectToUpdate(exam,examDB);

        Exam examUpdate =  examRepository.save(examDB);

        log.info("Update realizado com sucesso  exam={} id={}",examDB,id);

        return examUpdate;

    }

    public void inactivate(long id){
        log.info("Fazendo o delete logico do axame id={}",id);
        Exam examDB = examRepository.findById(id).orElseThrow(()->{ throw  new EmptyResultDataAccessException("Não existe nenhum item salvo com esse id",1){};});
        examDB.setStatus(Status.INATIVE);

        examRepository.save(examDB);

        log.info("Delete logico realizado do axame id={}",id);
    }

    public Page<Exam> findByStatusPageable(Integer page){

        log.info("buscando todos os exames por pagina  com limite de page={} limit={}",page,maxItemsPerPage);

        PageRequest pageRequest = PageRequest.of(page,maxItemsPerPage);

        Page<Exam> examPage = examRepository.findByStatus(Status.ACTIVE,pageRequest);

        if(!examPage.hasContent()){
            throw  new EmptyResultDataAccessException("Não existe nenhum item salvo",1);
        }

        log.info("Busca realizada com sucesso dos exames por pagina  com limite de page={} limit={} hasNextPage={}",page,maxItemsPerPage,examPage.hasNext());

        return  examPage;
    }



}
