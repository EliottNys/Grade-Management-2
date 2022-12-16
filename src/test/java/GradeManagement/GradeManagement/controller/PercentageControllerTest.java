package GradeManagement.GradeManagement.controller;

import GradeManagement.GradeManagement.model.Course;
import GradeManagement.GradeManagement.model.Percentage;
import GradeManagement.GradeManagement.model.Section;
import GradeManagement.GradeManagement.repository.CourseRepository;
import GradeManagement.GradeManagement.repository.PercentageRepository;
import GradeManagement.GradeManagement.repository.SectionRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class PercentageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private Section section;
    @Autowired
    private SectionRepository sectionRepository;

    private List<Course> listOfCourses = new ArrayList<>();
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private PercentageRepository percentageRepository;

    @BeforeEach
    void setup() {
        percentageRepository.deleteAll();
    }

    @BeforeAll
    void setup_repos() {
        section = new Section("Architecture and software quality", 4, 4);
        sectionRepository.save(section);

        listOfCourses.add(new Course("Software Architecture"));
        listOfCourses.add(new Course("Software Architecture and Quality Lab"));
        courseRepository.saveAll(listOfCourses);
    }

    @Test
    void getAllPercentages() throws Exception {
        // given
        List<Percentage> listOfPercentages = new ArrayList<>();
        listOfPercentages.add(new Percentage(listOfCourses.get(0), section, 70));
        listOfPercentages.add(new Percentage(listOfCourses.get(1), section, 30));
        percentageRepository.saveAll(listOfPercentages);

        // when
        ResultActions response = mockMvc.perform(get("/api/percentages"));

        // then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()",
                        is(listOfPercentages.size())));
    }

    @Test
    void getAllPercentagesByCourseIdAndsectionId() {
    }

    @Test
    void getAllPercentagesBySectionId() {
    }

    @Test
    void getAllPercentagesByCourseId() {
    }

    @Test
    void getPercentagesById() {
    }

    @Test
    void createPercentage() {
    }

    @Test
    void testCreatePercentage() {
    }

    @Test
    void updatePercentage() {
    }

    @Test
    void deletePercentage() {
    }

    @Test
    void deleteAllPercentagesOfCourseAndsections() {
    }
}