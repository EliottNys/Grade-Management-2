package GradeManagement.GradeManagement.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import GradeManagement.GradeManagement.exception.ResourceNotFoundException;
import GradeManagement.GradeManagement.model.Course;
import GradeManagement.GradeManagement.model.Teacher;
import GradeManagement.GradeManagement.repository.CourseRepository;
import GradeManagement.GradeManagement.repository.TeacherRepository;

@Service
public class TeacherService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    TeacherRepository teacherRepository;

                            /**
    * Get all teachers
    * @return HTTP response (OK if successful, NO_CONTENT if not found, INTERNAL_SERVER_ERROR if error occur)
    */
    public ResponseEntity<List<Teacher>> getAllTeachers() {
        List<Teacher> teachers = new ArrayList<Teacher>();
        teacherRepository.findAll().forEach(teachers::add);

        if (teachers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(teachers, HttpStatus.OK);
    }

                            /**
    * Get a teacher by id
    * @param id the id of the teacher
    * @return HTTP response (OK if successful, NO_CONTENT if not found, INTERNAL_SERVER_ERROR if error occur)
    */
    public ResponseEntity<Teacher> getTeacherById(@PathVariable("id") long id) {
        Teacher Teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Teacher with id = " + id));
        return new ResponseEntity<>(Teacher, HttpStatus.OK);
    }

                                /**
    * Add a teacher by course
    * @param courseId the id of the course
    * @param teacherRequest the body of the request
    * @return HTTP response (OK if successful, NO_CONTENT if not found, INTERNAL_SERVER_ERROR if error occur)
    */
    public ResponseEntity<Teacher> addTeacherByCourse(@PathVariable(value = "courseId") Long courseId, @RequestBody Teacher teacherRequest) {
        Teacher teacher = courseRepository.findById(courseId).map(course -> {
            long teacherId = teacherRequest.getId();

            // tag is existed
            if (teacherId != 0L) {
                Teacher _teacher = teacherRepository.findById(teacherId)
                        .orElseThrow(() -> new ResourceNotFoundException("Not found Teacher with id = " + teacherId));
                course.addTeacher(_teacher);
                courseRepository.save(course);
                return _teacher;
            }

            // add and create new Tag
            course.addTeacher(teacherRequest);
            return teacherRepository.save(teacherRequest);
        }).orElseThrow(() -> new ResourceNotFoundException("Not found Course with id = " + courseId));

        return new ResponseEntity<>(teacher, HttpStatus.CREATED);
    }

                                /**
    * create a teacher
    * @param teacher the body of the request
    * @return HTTP response (OK if successful, NO_CONTENT if not found, INTERNAL_SERVER_ERROR if error occur)
    */
    public ResponseEntity<Teacher> createTeachers(@RequestBody Teacher teacher) {
        Teacher _teacher = teacherRepository.save(new Teacher(teacher.getName()));
        return new ResponseEntity<>(_teacher,HttpStatus.CREATED);
    }

                                /**
    * Update a teacher by id
    * @param id the id of the teacher
    * @param teacher the body of the request
    * @return HTTP response (OK if successful, NO_CONTENT if not found, INTERNAL_SERVER_ERROR if error occur)
    */
    public ResponseEntity<Teacher> updateTeacher(@PathVariable("id") long id, @RequestBody Teacher teacher) {
        Teacher _teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Teacher with id = " + id));

        _teacher.setName(teacher.getName());

        return new ResponseEntity<>(teacherRepository.save(_teacher), HttpStatus.OK);
    }

                                /**
    * Assign a course to a teacher
    * @param teacherId the id of the teacher    
    * @param courseId the id of the course
    * @return HTTP response (OK if successful, NO_CONTENT if not found, INTERNAL_SERVER_ERROR if error occur)
    */
    public ResponseEntity<Teacher> assignCourseToTeacher( @PathVariable Long teacherId,@PathVariable Long courseId) {
        Set<Course> courseSet = null;
        Teacher teacher = teacherRepository.findById(teacherId).get();
        Course course = courseRepository.findById(courseId).get();
        courseSet =  teacher.getCourses();
        courseSet.add(course);
        teacher.setCourses(courseSet);
        return new ResponseEntity<>(teacherRepository.save(teacher), HttpStatus.OK);
    }

                                /**
    * Delete a teacher by id
    * @param id the id of the teacher
    * @return HTTP response (OK if successful, NO_CONTENT if not found, INTERNAL_SERVER_ERROR if error occur)
    */
    public ResponseEntity<HttpStatus> deleteTeacher(@PathVariable("id") long id) {
        teacherRepository.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

                                /**
    * Delete a teacher from a course
    * @param courseId the id of the course
    * @param teacherId the id of the teacher    
    * @return HTTP response (OK if successful, NO_CONTENT if not found, INTERNAL_SERVER_ERROR if error occur)
    */
    public ResponseEntity<HttpStatus> deleteTeacherFromCourse(@PathVariable(value = "courseId") Long courseId, @PathVariable(value = "teacherId") Long teacherId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Tutorial with id = " + courseId));

        course.removeTeacher(teacherId);
        courseRepository.save(course);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    
}
