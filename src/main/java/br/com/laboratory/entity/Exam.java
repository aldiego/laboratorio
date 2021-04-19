package br.com.laboratory.entity;


import br.com.laboratory.util.ExamType;
import br.com.laboratory.util.Status;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Builder
@Table(
        name = "tb_exam",
        indexes = {
                @Index(name = "idx_exam_status_index",columnList = "status")
        })
public class Exam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(hidden = true)
    private long id;

    @NotNull(message = "O campo name não pode ser null")
    @ApiModelProperty(name = "name",dataType = "String",required = true)
    private String name;

    @NotNull(message = "O campo status não pode ser null")
    @ApiModelProperty(name = "status",dataType = "br.com.laboratory.util.Status",required = true)
    private Status status;

    @NotNull(message = "O campo examType não pode ser null")
    @ApiModelProperty(name = "type",dataType = "br.com.laboratory.util.ExamType",required = true)
    @Enumerated(EnumType.ORDINAL)
    private ExamType type;


    public void changeObjectToUpdate(final Exam examUpdate, Exam examDB){

        if(!examDB.getName().equals(examUpdate.getName())){
            examDB.setName(examUpdate.getName());
        }

        if(examUpdate.getType() !=  examDB.getType() ){
            examDB.setName(examUpdate.getName());
        }

        if(examUpdate.getStatus() == Status.INATIVE){
            throw  new IllegalArgumentException("Não é possivel desativar, use o endpoint de delete.");
        }

    }

}
