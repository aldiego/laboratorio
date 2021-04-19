package br.com.laboratory.controller;

import br.com.laboratory.entity.Exam;
import br.com.laboratory.service.ExamService;
import br.com.laboratory.service.ExameLaboratoryAssociateService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Slf4j
@RestController
@RequestMapping(value = "/exam")
public class ExamController {


    @Autowired
    private ExamService examService;

    @Autowired
    private ExameLaboratoryAssociateService exameLaboratoryAssociateService;

    @PostMapping(value = "/",produces = {MediaType.APPLICATION_JSON_VALUE},consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Salva um exame no banco.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Item criado"),
            @ApiResponse(code = 404, message = "Parametros incorretos."),
            @ApiResponse(code = 500, message = "Sistema indisponível")
    })
    public ResponseEntity<Exam> persist(@Valid @RequestBody Exam exam){

        log.info("Inicio save  no exame exam={}",exam);
        var examPersist = examService.persist(exam);
        log.info("Fim save  no exame  exam={}",examPersist);
        return  ResponseEntity.ok().body(examPersist);
    }

    @PutMapping(value = "/{id}",produces = {MediaType.APPLICATION_JSON_VALUE},consumes = {MediaType.APPLICATION_JSON_VALUE} )
    @ApiOperation(value = "Atualiza o  exame no banco.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Item atualizado"),
            @ApiResponse(code = 404, message = "Parametros incorretos."),
            @ApiResponse(code = 500, message = "Sistema indisponível")
    })
    public ResponseEntity<Exam> update(@RequestBody Exam exam, @NotNull  @PathVariable Long id){

        Assert.notNull(id,"Não é possivel atualuzar ");
        log.info("Inicio update  do exame exam={}",exam);
        Exam examUpdated = examService.update(exam,id);
        log.info("Fim update  do exame exam={}",exam);
        return  ResponseEntity.ok().body(examUpdated);
    }

    @GetMapping(value = "/",produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Pega todos  os  exames do banco com paginação.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Item criado"),
            @ApiResponse(code = 404, message = "Parametros incorretos."),
            @ApiResponse(code = 500, message = "Sistema indisponível")
    })
    public Page<Exam> getPageable(@RequestParam(value = "page",required = false,defaultValue = "0") Integer page){
        log.info("Inicio busca todos exames por pagina page={}",page);
        var  pageExame = examService.findByStatusPageable(page);
        log.info("Fim busca todos exames por pagina page={}",page);
        return  pageExame;
    }

    @DeleteMapping(value = "/{id}",produces = {MediaType.APPLICATION_JSON_VALUE})
    @GetMapping(value = "/",produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Pega todos  os  exames do banco com paginação.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Item criado"),
            @ApiResponse(code = 404, message = "Parametros incorretos."),
            @ApiResponse(code = 500, message = "Sistema indisponível")
    })
    public ResponseEntity delete(@PathVariable("id") Long id){

        log.info("Inicio delete  exam por id id={}",id);
        examService.inactivate(id);
        log.info("Fim delete  exam por id id={}",id);
        return  ResponseEntity.ok().build();
    }

    @PostMapping(value = "/{id}/associateLaboratory/{idLaboratory}",produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Associa um exame a um laboratorio, o exame e laboratorio precisar estar ativo.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Item criado"),
            @ApiResponse(code = 404, message = "Parametros incorretos."),
            @ApiResponse(code = 500, message = "Sistema indisponível")
    })
    public ResponseEntity associateExamWithLaboratory(@NotNull @PathVariable("id") Long idExam, @NotNull @PathVariable("idLaboratory")Long idLaboratory){

        log.info("Inicio da associação do exame com laboratorio  idExam={}  idLaboratory={}",idExam,idLaboratory);
        exameLaboratoryAssociateService.associateExamWithLaboratory(idExam,idLaboratory);
        log.info("Fim da associação do exame com laboratorio  idExam={}  idLaboratory={}",idExam,idLaboratory);
        return  ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/disassociateLaboratory/{idLaboratory}")
    @ApiOperation(value = "Exclui um exame do laboratorio, o exame e laboratorio precisar estar ativo.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Item criado"),
            @ApiResponse(code = 404, message = "Parametros incorretos."),
            @ApiResponse(code = 500, message = "Sistema indisponível")
    })
    public ResponseEntity dissociateExamWithLaboratory(@PathVariable("id") Long idExam,@PathVariable("idLaboratory")Long idLaboratory){

        log.info("Inicio da associação do exame com laboratorio  idExam={}  idLaboratory={}",idExam,idLaboratory);
        exameLaboratoryAssociateService.disassociateExamWithLaboratory(idLaboratory,idExam);
        log.info("Fim da associação do exame com laboratorio  idExam={}  idLaboratory={}",idExam,idLaboratory);
        return  ResponseEntity.ok().build();
    }

}

