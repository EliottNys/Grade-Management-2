package GradeManagement.GradeManagement.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import GradeManagement.GradeManagement.exception.ResourceNotFoundException;
import GradeManagement.GradeManagement.model.Course;
import GradeManagement.GradeManagement.model.Teacher;
import GradeManagement.GradeManagement.repository.CourseRepository;
import GradeManagement.GradeManagement.repository.TeacherRepository;

@Service
public class CourseService{

    @Autowired 
    CourseRepository courseRepository;

    @Autowired
    TeacherRepository teacherRepository;

  /**
   * Get all the courses
   * @return HTTP response (OK if successful, NO_CONTENT if not found, INTERNAL_SERVER_ERROR if error occur)
   */

    public ResponseEntity<List<Course>> getAllCourses() {
        List<Course> courses = new ArrayList<Course>();
    
    
          courseRepository.findAll().forEach(courses::add);
    
    
        if (courses.isEmpty()) {
          return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }

    /**
   * Get one course by its id
   * @param id The id of the desired course
   * @param semester 
   * @return HTTP response (OK if successful, NO_CONTENT if not found, INTERNAL_SERVER_ERROR if error occur)
   */

    public ResponseEntity<Course> getCourseById(long id) {
    Course course = courseRepository.findById(id)
    .orElseThrow(() -> new ResourceNotFoundException("Not found Course with id = " + id));

    return new ResponseEntity<>(course, HttpStatus.OK);
    }

    /**
     * Create a new course
    * @param course "course" is the body of the request, it contains the name and the semester of the course
    * @return HTTP response (OK if successful, NO_CONTENT if not found, INTERNAL_SERVER_ERROR if error occur)
     */

    public ResponseEntity<Course> createCourse(Course course) {
      Course _course = courseRepository.save(new Course(course.getName()));
      return new ResponseEntity<>(_course, HttpStatus.CREATED);
    }

    /**
    * Update a course
    * @param id The id of the course that you want to update
    * @param course "course" is the body of the request, it contains the name and the semester of the course
    * @return HTTP response (OK if successful, NO_CONTENT if not found, INTERNAL_SERVER_ERROR if error occur)
    */

    public ResponseEntity<Course> updateCourse(long id, Course course) {
      Course _course = courseRepository.findById(id)
          .orElseThrow(() -> new ResourceNotFoundException("Not found Course with id = " + id));
  
      _course.setName(course.getName());
  
      return new ResponseEntity<>(courseRepository.save(_course), HttpStatus.OK);
    }

    /**
    * Assign a teacher to a course
    * @param id The id of the course that you want to assign to a teacher
    * @param teacherId the id of the teacher that you want to assign to a the course
    * @return HTTP response (OK if successful, NO_CONTENT if not found, INTERNAL_SERVER_ERROR if error occur)
    */

    public ResponseEntity<Course> assignTeacherToCourse(Long id, Long teacherId) {
      Set<Teacher> teacherSet = null;
      Course course = courseRepository.findById(id).get();
      Teacher teacher = teacherRepository.findById(teacherId).get();
      teacherSet =  course.getTeachers();
      teacherSet.add(teacher);
      course.setTeachers(teacherSet);
      return new ResponseEntity<>(courseRepository.save(course), HttpStatus.OK);
    }

    /**
    * Delete a course by its id
    * @param id The id of the course that you want to delete to a teacher
    * @return HTTP response (OK if successful, NO_CONTENT if not found, INTERNAL_SERVER_ERROR if error occur)
    */

    public ResponseEntity<HttpStatus> deleteCourseById(long id) {
      courseRepository.deleteById(id);
      
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

        /**
    * Delete all the courses
    * @return HTTP response (OK if successful, NO_CONTENT if not found, INTERNAL_SERVER_ERROR if error occur)s
    */

    public ResponseEntity<HttpStatus> deleteAllCourses() {
      courseRepository.deleteAll();
      
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
