package GradeManagement.GradeManagement.controller;

import GradeManagement.GradeManagement.model.Section;
import GradeManagement.GradeManagement.model.Teacher;
import GradeManagement.GradeManagement.repository.SectionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    void getSectionById() {
    }

    @Test
    void createSection() {
    }

    @Test
    void updateSection() {
    }

    @Test
    void deleteSection() {
    }

    @Test
    void deleteAllSections() {
    }
}