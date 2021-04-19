package br.com.labortory.integration;

import br.com.laboratory.entity.Laboratory;
import br.com.laboratory.util.Status;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class LaboratoryIntegration {


    private MockMvc mockMvc;


    private ObjectMapper objectMapper;

    public LaboratoryIntegration(MockMvc mockMvc, ObjectMapper objectMapper){
        this.mockMvc = mockMvc;
        this.objectMapper= objectMapper;
    }

    public Laboratory persist() throws Exception {

        String laboratorySave = this.mockMvc.perform(post("/laboratory/")
                .content(
                        objectMapper.writeValueAsString(
                                Laboratory.builder()
                                        .name("Iniciando cadastro")
                                        .status(Status.ACTIVE)
                                        .address("Endereço")
                                        .build()))
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Iniciando cadastro"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(Status.ACTIVE.name()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address").value("Endereço"))
                .andReturn().getResponse().getContentAsString();

        return objectMapper.readValue(laboratorySave,Laboratory.class);
    }


    public  void find() throws Exception {
        this.mockMvc.perform(get("/laboratory/")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.empty").value(Boolean.toString(false)));

    }



    public  void delete(Laboratory laboratory) throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/laboratory/{id}/",laboratory.getId())
                .contentType("application/json"))
                .andExpect(status().isOk());

    }
}
