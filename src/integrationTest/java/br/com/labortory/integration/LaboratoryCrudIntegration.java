package br.com.labortory.integration;

import br.com.laboratory.LaboratoryApplication;
import br.com.laboratory.entity.Exam;
import br.com.laboratory.entity.Laboratory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("integrationTest")
@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = LaboratoryApplication.class)
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LaboratoryCrudIntegration {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private ExamIntegration examIntegration;
    private LaboratoryIntegration laboratoryIntegration;

    @Before
    public void setup(){
        examIntegration = new ExamIntegration(mockMvc,objectMapper);
        laboratoryIntegration = new LaboratoryIntegration(mockMvc,objectMapper);
    }

    @Test
    public void crudExam() throws Exception {
        Exam examSaved =  examIntegration.persist();
        examIntegration.find();
        examIntegration.delete(examSaved);
    }

    @Test
    public void crudLaboratory() throws Exception {
        Laboratory laboratory =  laboratoryIntegration.persist();
        laboratoryIntegration.find();
        laboratoryIntegration.delete(laboratory);
    }




    @Test
    public void associateAndDisassociateLaboratory() throws Exception {
        Laboratory laboratory =  laboratoryIntegration.persist();
        Exam exam =  examIntegration.persist();


        this.mockMvc.perform(post("/exam/{id}/associateLaboratory/{id}",exam.getId(),laboratory.getId())
                .contentType("application/json"))
                .andExpect(status().isOk());

        this.mockMvc.perform(delete("/exam/{id}/disassociateLaboratory/{id}",exam.getId(),laboratory.getId())
                .contentType("application/json"))
                .andExpect(status().isOk());

        examIntegration.delete(exam);
        laboratoryIntegration.delete(laboratory);
    }




}
