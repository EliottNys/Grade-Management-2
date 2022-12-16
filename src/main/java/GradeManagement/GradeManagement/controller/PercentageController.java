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

import GradeManagement.GradeManagement.model.Percentage;
import GradeManagement.GradeManagement.service.PercentageService;
import io.swagger.v3.oas.annotations.Operation;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class PercentageController {
  @Autowired
  private PercentageService percentageService;


  @Operation(summary = "Get all percentages")
  @GetMapping("/percentages") //ok
  public ResponseEntity<List<Percentage>> getAllPercentages() {
      return percentageService.getAllPercentages();
  }

  @Operation(summary = "Get a percentage by secctionId and courseId")
  @GetMapping("/sections/{sectionId}/courses/{courseId}/percentages")
  public ResponseEntity<List<Percentage>> getAllPercentagesByCourseIdAndsectionId(@PathVariable(value = "courseId") Long courseId,@PathVariable(value = "sectionId") Long sectionId) {
    return percentageService.getAllPercentagesByCourseIdAndsectionId(courseId,sectionId);
  }

  @Operation(summary = "Get percentages by a sectionId")
  @GetMapping("/sections/{sectionId}/percentages")
  public ResponseEntity<List<Percentage>> getAllPercentagesBySectionId(@PathVariable(value = "sectionId") Long sectionId) {
    return percentageService.getAllPercentagesBySectionId(sectionId);
  }

  @Operation(summary = "Get percentages by courseId")
  @GetMapping("/courses/{courseId}/percentages")
  public ResponseEntity<List<Percentage>> getAllPercentagesByCourseId(@PathVariable(value = "CourseId") Long courseId) {
    return percentageService.getAllPercentagesByCourseId(courseId);
  }

  @Operation(summary = "Get a percentage by id")
  @GetMapping("/percentages/{id}")
  public ResponseEntity<Percentage> getPercentagesById(@PathVariable(value = "id") Long id) {
    return percentageService.getPercentagesById(id);
  }

  @Operation(summary = "Create a percentage related to a sectionId and a courseId")
  @PostMapping("/sections/{sectionId}/courses/{courseId}/percentages")
  public ResponseEntity<Percentage> createPercentage(@PathVariable(value = "sectionId") Long sectionId,@PathVariable(value = "courseId") Long courseId,
      @RequestBody Percentage percentageRequest) {
      return percentageService.createPercentage(sectionId,courseId,percentageRequest);
  }

  @Operation(summary = "Update a percentage by id")
  @PutMapping("/percentages/{id}")
  public ResponseEntity<Percentage> updatePercentage(@PathVariable("id") long id, @RequestBody Percentage percentageRequest) {
    return percentageService.updatePercentage(id,percentageRequest);
  }

  @Operation(summary = "Delete a percentage by id")
  @DeleteMapping("/percentages/{id}")
  public ResponseEntity<HttpStatus> deletePercentage(@PathVariable("id") long id) {
    return percentageService.deletePercentage(id);
  }

  @Operation(summary = "Get a percentage by sectionId and courseId")
  @DeleteMapping("/sections/{sectionsId}/courses/{courseId}/percentages")
  public ResponseEntity<List<Percentage>> deletePercentagesByCourseAndSection(@PathVariable(value = "courseId") Long courseId, @PathVariable(value = "sectionId") Long sectionId) {
    return percentageService.deletePercentagesByCourseAndSection(courseId,sectionId);
  }
}
