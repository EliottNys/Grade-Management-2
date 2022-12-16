package GradeManagement.GradeManagement.controller;

import GradeManagement.GradeManagement.model.Section;
import GradeManagement.GradeManagement.repository.SectionRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class SectionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {sectionRepository.deleteAll();}
    @Test
    void getAllSections() throws Exception {
        // given
        List<Section> listOfSections = new ArrayList<>();
        listOfSections.add(new Section("Architecture and software quality", 4, 4));
        listOfSections.add(new Section("Mobile development", 3, 4));
        listOfSections.add(new Section("Software engineering 1", 7, 3));
        sectionRepository.saveAll(listOfSections);

        // when
        ResultActions response = mockMvc.perform(get("/api/sections"));

        // then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()",
                        is(listOfSections.size())));
    }

    @Test
    void getSectionById() throws Exception {
        // given
        Section section = new Section("Architecture and software quality", 4, 4);
        sectionRepository.save(section);

        // when
        ResultActions response = mockMvc.perform(get("/api/sections/{id}", section.getId()));

        // then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.name", is(section.getName())))
                .andExpect((jsonPath("$.credits", is(section.getCredits()))))
                .andExpect(jsonPath("$.level", is(section.getLevel())));
    }

    @Test
    void getSectionByInvalidId() throws Exception {
        // given
        Section section = new Section("Architecture and software quality", 4, 4);
        sectionRepository.save(section);

        // when
        ResultActions response = mockMvc.perform(get("/api/sections/{id}", section.getId()+1));

        // then
        response.andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void createSection() throws Exception {
        //given
        Section section = new Section("Architecture and software quality", 4, 4);

        //when
        ResultActions response = mockMvc.perform(post("/api/sections")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(section)));

        //then
        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(section.getName())))
                .andExpect(jsonPath("$.credits", is(section.getCredits())))
                .andExpect(jsonPath("$.level", is(section.getLevel())));
    }

    @Test
    void updateSection() throws Exception {
        // given
        Section savedSection = new Section("Architecture quality", 2, 3);
        sectionRepository.save(savedSection);

        Section updatedSection = new Section("Architecture and software quality", 4, 4);

        // when
        ResultActions response = mockMvc.perform(put("/api/sections/{id}", savedSection.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedSection)));

        // then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.name", is(updatedSection.getName())))
                .andExpect(jsonPath("$.credits", is(updatedSection.getCredits())))
                .andExpect(jsonPath("$.level", is(updatedSection.getLevel())));
    }

    @Test
    void deleteSection() throws Exception {
        // given
        Section savedSection = new Section("Architecture and software quality", 4, 4);
        sectionRepository.save(savedSection);
        List<Section> listOfSections = new ArrayList<>();
        listOfSections.add(new Section("Mobile development", 3, 4));
        listOfSections.add(new Section("Software engineering 1", 7, 3));
        sectionRepository.saveAll(listOfSections);

        // when
        ResultActions response = mockMvc.perform(delete("/api/sections/{id}", savedSection.getId()));
        ResultActions get_response = mockMvc.perform(get("/api/sections"));

        // then
        response.andExpect(status().isNoContent())
                .andDo(print());
        get_response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()",
                        is(listOfSections.size())));
    }

    @Test
    void deleteAllSections() throws Exception {
        // given
        List<Section> listOfSections = new ArrayList<>();
        listOfSections.add(new Section("Architecture and software quality", 4, 4));
        listOfSections.add(new Section("Mobile development", 3, 4));
        listOfSections.add(new Section("Software engineering 1", 7, 3));
        sectionRepository.saveAll(listOfSections);

        // when
        ResultActions response = mockMvc.perform(delete("/api/sections"));
        ResultActions get_response = mockMvc.perform(get("/api/sections"));

        // then
        response.andExpect(status().isNoContent())
                .andDo(print());
    }
}