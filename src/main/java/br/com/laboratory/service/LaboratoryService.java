package br.com.laboratory.service;

import br.com.laboratory.entity.Laboratory;
import br.com.laboratory.repository.LaboratoryRepository;
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
public class LaboratoryService {

    @Value("${laboratory.request.max-item-per-page}")
    private int maxItemsPerPage;

    private String messageErrorExamNotNull = "O laboratorio não pode ser null para salvar";
    
    @Autowired
    private LaboratoryRepository laboratoryRepository;


    public Laboratory persist(Laboratory laboratory){

        Assert.notNull(laboratory,messageErrorExamNotNull);

        log.info("Salvando um laboratorio no banco laboratory={}",laboratory);

        laboratory.setStatus(Status.ACTIVE);

        var entityPersisted = laboratoryRepository.save(laboratory);

        log.info("Laboratorio salvo laboratory={} id ={}",laboratory,entityPersisted.getId());

        return entityPersisted;
    }

    public Laboratory update(Laboratory laboratoryUpdate, long id){

        Assert.notNull(laboratoryUpdate,messageErrorExamNotNull);

        log.info("Fazendo o update do laboratory  laboratory={} id={}",laboratoryUpdate,id);
        var laboratoryBD = laboratoryRepository.findById(id).orElseThrow(()->{ throw  new EmptyResultDataAccessException("Não existe nenhum item salvo",1){};});

        laboratoryBD.changeObjectToUpdate(laboratoryUpdate,laboratoryBD);

        Laboratory laboratory = laboratoryRepository.save(laboratoryBD);

        log.info("Update realizado com sucesso  laboratory={} id={}",laboratory,id);

        return laboratory;

    }

    public void inactivate(long id){
        log.info("Fazendo o delete logico do laboratorio id={}",id);
        var  laboratory = laboratoryRepository.findById(id).orElseThrow(()->{ throw  new EmptyResultDataAccessException("Não existe nenhum item salvo com esse id",1){};});
        laboratory.setStatus(Status.INATIVE);

        laboratoryRepository.save(laboratory);

        log.info("Delete logico realizado do laboratorio id={}",id);
    }

    public Page<Laboratory> findByStatusPageable(Integer page){

        log.info("buscando todos os laboratorios por pagina  com limite de page={} limit={}",page,maxItemsPerPage);

        var pageRequest = PageRequest.of(page,maxItemsPerPage);

        var laboratoryPage = laboratoryRepository.findByStatus(Status.ACTIVE,pageRequest);

        if(!laboratoryPage.hasContent()){
            throw  new EmptyResultDataAccessException("Não existe nenhum item salvo",1);
        }

        log.info("Busca realizada com sucesso dos laboratorios por pagina  com limite de page={} limit={} hasNextPage={}",page,maxItemsPerPage,laboratoryPage.hasNext());

        return  laboratoryPage;
    }
}
