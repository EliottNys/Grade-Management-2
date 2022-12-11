package GradeManagement.GradeManagement.controller;

import GradeManagement.GradeManagement.model.Student;
import GradeManagement.GradeManagement.repository.StudentRepository;
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
        Student student = new Student("DUJARDIN Jean");
        studentRepository.save(student);

        // when
        ResultActions response = mockMvc.perform(get("/api/students/{id}", student.getId()));

        // then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.name", is(student.getName())));
    }

    @Test
    void getStudentByInvalidId() throws Exception {
        // given
        long studentId = 2L;
        Student employee = new Student("DUJARDIN Jean");
        studentRepository.save(employee);

        // when
        ResultActions response = mockMvc.perform(get("/api/students/{id}", studentId));

        // then
        response.andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void createStudent() throws Exception {
        // given
        Student student = new Student("DENEUVE Catherine");

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
    void updateStudent() throws Exception {
        // given
        Student savedStudent = new Student("DES GALLES Chariot");
        studentRepository.save(savedStudent);

        Student updatedStudent = new Student("DE GAULLE Charles");

        // when
        ResultActions response = mockMvc.perform(put("/api/students/{id}", savedStudent.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedStudent)));

        // then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.name", is(updatedStudent.getName())));
    }

    @Test
    void updateStudentInvalidId() throws Exception {
        // given
        long studentId = 1L;
        Student savedStudent = new Student("DES GALLES Chariot");
        studentRepository.save(savedStudent);

        Student updatedStudent = new Student("DE GAULLE Charles");

        // when
        ResultActions response = mockMvc.perform(put("/api/students/{id}", studentId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedStudent)));

        // then
        response.andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void deleteStudent() {
    }

    @Test
    void deleteAllStudents() {
    }
}