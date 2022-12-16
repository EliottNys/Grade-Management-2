package GradeManagement.GradeManagement.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import GradeManagement.GradeManagement.exception.ResourceNotFoundException;
import GradeManagement.GradeManagement.model.Student;
import GradeManagement.GradeManagement.repository.StudentRepository;

@Service
public class StudentService {

    @Autowired
    StudentRepository studentRepository;

                        /**
    * Get all students
    * @return HTTP response (OK if successful, NO_CONTENT if not found, INTERNAL_SERVER_ERROR if error occur)
    */
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> students = new ArrayList<Student>();
        studentRepository.findAll().forEach(students::add);

        if (students.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

                    /**
    * Delete a student by id
    * @param id the id of the student
    * @return HTTP response (OK if successful, NO_CONTENT if not found, INTERNAL_SERVER_ERROR if error occur)
    */
    public ResponseEntity<Student> getStudentById(@PathVariable("id") long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Student with id = " + id));

        return new ResponseEntity<>(student, HttpStatus.OK);
    }

                            /**
    * Create a student
    * @param student the body of the request
    * @return HTTP response (OK if successful, NO_CONTENT if not found, INTERNAL_SERVER_ERROR if error occur)
    */
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        Student _student = studentRepository
                .save(new Student(student.getName()));

        return new ResponseEntity<>(_student, HttpStatus.CREATED);
    }

                            /**
    * Update a student by id
    * @param id the id of the student
    * @param student the body of the request
    * @return HTTP response (OK if successful, NO_CONTENT if not found, INTERNAL_SERVER_ERROR if error occur)
    */
    public ResponseEntity<Student> updateStudent(@PathVariable("id") long id, @RequestBody Student student) {
        Student _student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found student with id = " + id));
        
        _student.setName(student.getName());

        return new ResponseEntity<>(studentRepository.save(_student), HttpStatus.OK);
    }

                            /**
    * Delete a student by id
    * @param id the id of the student
    * @return HTTP response (OK if successful, NO_CONTENT if not found, INTERNAL_SERVER_ERROR if error occur)
    */
    public ResponseEntity<HttpStatus> deleteStudent(@PathVariable("id") long id) {
        studentRepository.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

                            /**
    * Delete all sections
    * @return HTTP response (OK if successful, NO_CONTENT if not found, INTERNAL_SERVER_ERROR if error occur)
    */
    public ResponseEntity<HttpStatus> deleteAllStudents() {
        studentRepository.deleteAll();

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    
}
