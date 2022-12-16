package GradeManagement.GradeManagement.controller;

import GradeManagement.GradeManagement.model.Course;
import GradeManagement.GradeManagement.repository.CourseRepository;
import org.junit.jupiter.api.BeforeEach;
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
class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CourseRepository courseRepository;

    @BeforeEach
    void setup() { courseRepository.deleteAll(); }

    @Test
    void getAllCourses() throws Exception {
        // given
        List<Course> listOfCourses = new ArrayList<>();
        listOfCourses.add(new Course("Software Architecture"));
        listOfCourses.add(new Course("Software Architecture and Quality Lab"));
        courseRepository.saveAll(listOfCourses);

        // when
        ResultActions response = mockMvc.perform(get("/api/courses"));

        // then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()",
                        is(listOfCourses.size())));
    }

    @Test
    void getCourseById() throws Exception {
        // given
        Course course = new Course("Software Architecture");
        courseRepository.save(course);

        // when
        ResultActions response = mockMvc.perform(get("/api/courses/{id}", course.getId()));

        // then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.name", is(course.getName())));
    }

    @Test
    void createCourse() {
    }

    @Test
    void updateCourse() {
    }

    @Test
    void assignTeacherToCourse() {
    }

    @Test
    void deleteCourse() {
    }

    @Test
    void deleteAllCourses() {
    }
}