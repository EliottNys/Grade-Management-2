package GradeManagement.GradeManagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import GradeManagement.GradeManagement.model.Course;
import GradeManagement.GradeManagement.service.CourseService;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class CourseController {

  @Autowired
  CourseService courseService;

  @GetMapping("/courses")
  public ResponseEntity<List<Course>> getAllCourses() {
    return courseService.getAllCourses();
  }

  @GetMapping("/courses/{id}")
  public ResponseEntity<Course> getCourseById(@PathVariable("id") long id) {
    return courseService.getCourseById(id);
  }

  @PostMapping("/courses")
  public ResponseEntity<Course> createCourse(@RequestBody Course course) {
    return courseService.createCourse(course);
  }

  @PutMapping("/courses/{id}")
  public ResponseEntity<Course> updateCourse(@PathVariable("id") long id, @RequestBody Course course) {
  return courseService.updateCourse(id, course);
  }

  @PutMapping("/{courseId}/teacher/{teacherId}")
  public ResponseEntity<Course> assignTeacherToCourse(@PathVariable("id") long id,@PathVariable Long teacherId) {
  return courseService.assignTeacherToCourse(id, teacherId);
}

  @DeleteMapping("/courses/{id}")
  public ResponseEntity<HttpStatus> deleteCourseById(@PathVariable("id") long id) {
  return courseService.deleteCourseById(id);
  }

  @DeleteMapping("/courses")
  public ResponseEntity<HttpStatus> deleteAllCourses() {
  return courseService.deleteAllCourses();
  }
}
