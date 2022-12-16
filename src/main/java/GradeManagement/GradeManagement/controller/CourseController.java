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
import io.swagger.v3.oas.annotations.Operation;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class CourseController {

  @Autowired
  CourseService courseService;

  @Operation(summary = "Get all courses")
  @GetMapping("/courses")
  public ResponseEntity<List<Course>> getAllCourses() {
    return courseService.getAllCourses();
  }

  @Operation(summary = "Get a course by its id")
  @GetMapping("/courses/{id}")
  public ResponseEntity<Course> getCourseById(@PathVariable("id") long id) {
    return courseService.getCourseById(id);
  }

  @Operation(summary = "Create a new course")
  @PostMapping("/courses")
  public ResponseEntity<Course> createCourse(@RequestBody Course course) {
    return courseService.createCourse(course);
  }

  @Operation(summary = "Update a course by its id")
  @PutMapping("/courses/{id}")
  public ResponseEntity<Course> updateCourse(@PathVariable("id") long id, @RequestBody Course course) {
  return courseService.updateCourse(id, course);
  }

  @Operation(summary = "Update a course by its courseId and teacherId")
  @PutMapping("/{courseId}/teacher/{teacherId}")
  public ResponseEntity<Course> assignTeacherToCourse(@PathVariable("id") long id,@PathVariable Long teacherId) {
  return courseService.assignTeacherToCourse(id, teacherId);
}

  @Operation(summary = "Delete a course by its id")
  @DeleteMapping("/courses/{id}")
  public ResponseEntity<HttpStatus> deleteCourseById(@PathVariable("id") long id) {
  return courseService.deleteCourseById(id);
  }

  @Operation(summary = "Delete all courses")
  @DeleteMapping("/courses")
  public ResponseEntity<HttpStatus> deleteAllCourses() {
  return courseService.deleteAllCourses();
  }
}
