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

import GradeManagement.GradeManagement.model.Teacher;
import GradeManagement.GradeManagement.service.TeacherService;
import io.swagger.v3.oas.annotations.Operation;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class TeacherController {


    @Autowired
    TeacherService teacherService;

    @Operation(summary = "Get all teacher")
    @GetMapping("/teachers") //ok
    public ResponseEntity<List<Teacher>> getAllTeachers() {
        return teacherService.getAllTeachers();
    }
    @Operation(summary = "Get a teacher by id")
    @GetMapping("/teachers/{id}") //ok
    public ResponseEntity<Teacher> getTeacherById(@PathVariable("id") long id) {
      return teacherService.getTeacherById(id);
    }

    @Operation(summary = "Create a teacher linked to a course")
    @PostMapping("/courses/{courseId}/teachers") //ok
    public ResponseEntity<Teacher> addTeacherByCourse(@PathVariable(value = "courseId") Long courseId, @RequestBody Teacher teacherRequest) {
      return teacherService.addTeacherByCourse(courseId,teacherRequest);
    }
    @Operation(summary = "Create a new teacher")
    @PostMapping("/teachers")
    public ResponseEntity<Teacher> createTeachers(@RequestBody Teacher teacher) {
        return teacherService.createTeachers(teacher);
    }
    @Operation(summary = "Update a teacher")
    @PutMapping("/teachers/{id}") //ok
    public ResponseEntity<Teacher> updateTeacher(@PathVariable("id") long id, @RequestBody Teacher teacher) {
        return teacherService.updateTeacher(id,teacher);
    }


    @Operation(summary = "Assign a course to a teacher")
    @PutMapping("/{teacherId}/course/{courseId}")
    public ResponseEntity<Teacher> assignCourseToTeacher( @PathVariable Long teacherId,@PathVariable Long courseId) {
        return teacherService.assignCourseToTeacher(teacherId,courseId);
  }
  @Operation(summary = "Delete a teacher")
    @DeleteMapping("/teachers/{id}") //ok
    public ResponseEntity<HttpStatus> deleteTeacher(@PathVariable("id") long id) {
       return teacherService.deleteTeacher(id);
    }


    @Operation(summary = "delete a teacher from a course")
    @DeleteMapping("/courses/{courseId}/teachers/{teacherId}")
    public ResponseEntity<HttpStatus> deleteTeacherFromCourse(@PathVariable(value = "courseId") Long courseId, @PathVariable(value = "teacherId") Long teacherId) {
        return teacherService.deleteTeacherFromCourse(courseId,teacherId);
    }
}