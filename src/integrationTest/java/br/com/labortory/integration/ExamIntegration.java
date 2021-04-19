package br.com.labortory.integration;

import br.com.laboratory.entity.Exam;
import br.com.laboratory.util.ExamType;
import br.com.laboratory.util.Status;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ExamIntegration {


    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    public ExamIntegration(MockMvc mockMvc, ObjectMapper objectMapper){
        this.mockMvc = mockMvc;
        this.objectMapper= objectMapper;
    }

    public Exam persist() throws Exception {

        String examSave = this.mockMvc.perform(post("/exam/")
                .content(
                        objectMapper.writeValueAsString(
                                Exam.builder()
                                        .name("Iniciando cadastro")
                                        .status(Status.ACTIVE)
                                        .type(ExamType.CLINIC)
                                        .build()))
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Iniciando cadastro"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(Status.ACTIVE.name()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.type").value(ExamType.CLINIC.name()))
                .andReturn().getResponse().getContentAsString();

        return objectMapper.readValue(examSave,Exam.class);
    }


    public  void find() throws Exception {
        this.mockMvc.perform(get("/exam/")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.empty").value(Boolean.toString(false)));

    }



    public  void delete(Exam exam) throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/exam/{id}/",exam.getId())
                .contentType("application/json"))
                .andExpect(status().isOk());

    }
}
