package br.com.laboratory.entity;

import br.com.laboratory.util.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(
        name="tb_laboratory_exam"
)
public class LaboratoryExam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "laboratoryId")
    private Laboratory laboratory;

    @ManyToOne
    @JoinColumn(name = "examId")
    private Exam exam;



    public  boolean canMakeAssociation(){

        return laboratory.getStatus() == Status.ACTIVE && laboratory.getStatus() == Status.ACTIVE;
    }

}
