package com.epam.esm.controller;

import com.epam.esm.service.SkillService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = SkillController.class)
@SqlGroup({
        @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = {"classpath:scheme.sql", "classpath:init.sql"}),
        @Sql(executionPhase = AFTER_TEST_METHOD, scripts = "classpath:drop.sql")
})
@AutoConfigureMockMvc(addFilters = false)
class SkillControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SkillService service;

    @Test
    public void responseShouldHaveOkStatus() throws Exception {
        mockMvc.perform(get("/skills/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void responseShouldHaveBadRequestStatus() throws Exception {
        mockMvc.perform(get("/skills/badRequest")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldAddEntityToDB() throws Exception {
        int before = service.findAll().size();
        mockMvc.perform(post("/skills/")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"TestName\"}"))
                .andExpect(status().isCreated());
        int after = service.findAll().size();
        assertTrue(before < after);
    }

    @Test
    public void shouldDeleteEntityFromDB() throws Exception {
        int before = service.findAll().size();
        mockMvc.perform(delete("/skills/{id}", 3L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());
        int after = service.findAll().size();
        assertTrue(before > after);
    }
}