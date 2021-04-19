package br.com.laboratory.entity;


import br.com.laboratory.util.Status;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(
        name = "tb_laboratory",
        indexes = {
        @Index(name = "idx_laboratory_status_index",columnList = "status")
})
public class Laboratory {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(hidden = true)
    private long id;

    @NotNull(message = "O campo name não pode ser null")
    @ApiModelProperty(name = "name",dataType = "String",required = true)
    @Column(name ="name",nullable = false)
    private String name;

    @NotNull(message = "O campo address não pode ser null")
    @ApiModelProperty(name = "address",dataType = "String",required = true)
    @Column(name ="address",nullable = false)
    private String address;

    @NotNull(message = "O campo status não pode ser null")
    @ApiModelProperty(name = "status",dataType = "br.com.laboratory.util.Status",required = true)
    @Enumerated(EnumType.ORDINAL)
    @Column(name ="status",nullable = false)
    private Status status;



    public void changeObjectToUpdate(final Laboratory laboratoryUpdate, Laboratory laboratoryDB){

        if(!laboratoryDB.getName().equals(laboratoryUpdate.getName())){
            laboratoryDB.setName(laboratoryUpdate.getName());
        }

        if(!laboratoryUpdate.getAddress().equals(laboratoryDB.getAddress())){
            laboratoryDB.setAddress(laboratoryUpdate.getAddress());
        }

        if(laboratoryUpdate.getStatus() == Status.INATIVE ){
            throw  new IllegalArgumentException("Não é possivel desativar, use o endpoint de delete.");
        }

    }

}
