package GradeManagement.GradeManagement.controller;

import GradeManagement.GradeManagement.model.Student;
import GradeManagement.GradeManagement.model.Teacher;
import GradeManagement.GradeManagement.repository.TeacherRepository;
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
class TeacherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup(){
        teacherRepository.deleteAll();
    }

    @Test
    void getAllTeachers() throws Exception {
        // given
        List<Teacher> listOfTeachers = new ArrayList<>();
        listOfTeachers.add(new Teacher("BRASSENS George"));
        listOfTeachers.add(new Teacher("DELON Alain"));
        listOfTeachers.add(new Teacher("PIAF Edith"));
        teacherRepository.saveAll(listOfTeachers);

        // when
        ResultActions response = mockMvc.perform(get("/api/teachers"));

        // then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()",
                        is(listOfTeachers.size())));
    }

    @Test
    void getTeacherById() throws Exception {
        // given
        Teacher teacher = new Teacher("DUJARDIN Jean");
        teacherRepository.save(teacher);

        // when
        ResultActions response = mockMvc.perform(get("/api/teachers/{id}", teacher.getId()));

        // then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.name", is(teacher.getName())));
    }

    @Test
    void addTeacherByCourse() {
    }

    @Test
    void createTeachers() {
    }

    @Test
    void updateTeacher() {
    }

    @Test
    void assignCourseToTeacher() {
    }

    @Test
    void deleteTeacher() {
    }

    @Test
    void deleteTeacherFromCourse() {
    }
}