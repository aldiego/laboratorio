package br.com.laboratory.controller;

import br.com.laboratory.entity.Laboratory;
import br.com.laboratory.service.LaboratoryService;
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
@RequestMapping("/laboratory")
@RestController
public class LaboratoryController {

    @Autowired
    private LaboratoryService laboratoryService;

    @ApiOperation(value = "Salva um laboratorio no banco.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Item criado"),
            @ApiResponse(code = 404, message = "Parametros incorretos."),
            @ApiResponse(code = 500, message = "Sistema indisponível")
    })
    @PostMapping(value = "/",consumes = {MediaType.APPLICATION_JSON_VALUE},produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Laboratory> persist(@Valid @RequestBody Laboratory laboratory){

        log.info("Inicio salvando laboratorio no banco laboratory={}",laboratory);
        Laboratory laboratoryDB = laboratoryService.persist(laboratory);
        log.info("Fim laboratorio salvo no banco  laboratory={}",laboratoryDB);

        return  ResponseEntity.ok().body(laboratoryDB);
    }

    @GetMapping("/")
    @ApiOperation(value = "Busca no banco os laboratorios com paginação.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Item criado"),
            @ApiResponse(code = 404, message = "Parametros incorretos."),
            @ApiResponse(code = 500, message = "Sistema indisponível")
    })
    public Page<Laboratory> getPageable(@RequestParam(value = "page",required = false,defaultValue = "0") Integer page){
        log.info("Inicio busca todos laboratorio por pagina page={}",page);
        Page<Laboratory>  pageExame = laboratoryService.findByStatusPageable(page);
        log.info("Fim busca todos laboratorio por pagina page={}",page);
        return  pageExame;
    }


    @PutMapping(value = "/{id}",produces = {MediaType.APPLICATION_JSON_VALUE},consumes = {MediaType.APPLICATION_JSON_VALUE} )
    @ApiOperation(value = "Atualiza um laboratorio no banco.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Item criado"),
            @ApiResponse(code = 404, message = "Parametros incorretos."),
            @ApiResponse(code = 500, message = "Sistema indisponível")
    })
    public ResponseEntity<Laboratory> update(@RequestBody Laboratory laboratoryDTO, @NotNull @PathVariable Long id){

        Assert.notNull(id,"Não é possivel atualuzar ");
        log.info("Inicio update  do laboratory laboratory={} id={}",laboratoryDTO,id);
        Laboratory laboratoryUpdate = laboratoryService.update(laboratoryDTO,id);
        log.info("Fim update  do laboratory exam={}",laboratoryDTO);
        return  ResponseEntity.ok().body(laboratoryUpdate);

    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Deleta um laboratorio no banco.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Item criado"),
            @ApiResponse(code = 404, message = "Parametros incorretos."),
            @ApiResponse(code = 500, message = "Sistema indisponível")
    })
    public ResponseEntity delete(@PathVariable("id") Long id){

        log.info("Inicio excluir  laboratorio por id={}",id);
        laboratoryService.inactivate(id);
        log.info("Fim excluir  laboratorio por id={}",id);
        return  ResponseEntity.ok().build();
    }


}
