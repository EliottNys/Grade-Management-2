package GradeManagement.GradeManagement.controller;

import GradeManagement.GradeManagement.model.Student;
import GradeManagement.GradeManagement.repository.StudentRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
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
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup(){
        studentRepository.deleteAll();
    }

    @Test
    void GetAllStudents() throws Exception {
        // given
        List<Student> listOfStudents = new ArrayList<>();
        listOfStudents.add(new Student("BRASSENS George"));
        listOfStudents.add(new Student("DELON Alain"));
        listOfStudents.add(new Student("PIAF Edith"));
        studentRepository.saveAll(listOfStudents);

        // when
        ResultActions response = mockMvc.perform(get("/api/students"));

        // then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()",
                        is(listOfStudents.size())));
    }

    @Test
    void getStudentById() throws Exception {
        // given
        Student employee = new Student("DUJARDIN Jean");
        studentRepository.save(employee);

        // when
        ResultActions response = mockMvc.perform(get("/api/students/{id}", employee.getId()));

        // then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.name", is(employee.getName())));
    }

    @Test
    void createStudent() throws Exception {
        // given
        Student student = new Student("NYS Eliott");

        // when
        ResultActions response = mockMvc.perform(post("/api/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(student)));

        // then
        response.andDo(print()).
                andExpect(status().isCreated())
                .andExpect(jsonPath("$.name",
                        is(student.getName())));
    }

    @Test
    void updateStudent() {
    }

    @Test
    void deleteStudent() {
    }

    @Test
    void deleteAllStudents() {
    }
}