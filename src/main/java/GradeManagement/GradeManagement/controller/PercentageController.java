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

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class PercentageController {
  @Autowired
  private PercentageService percentageService;



  @GetMapping("/percentages") //ok
  public ResponseEntity<List<Percentage>> getAllPercentages() {
      return percentageService.getAllPercentages();
  }

  @GetMapping("/sections/{sectionId}/courses/{courseId}/percentages")
  public ResponseEntity<List<Percentage>> getAllPercentagesByCourseIdAndsectionId(@PathVariable(value = "courseId") Long courseId,@PathVariable(value = "sectionId") Long sectionId) {
    return percentageService.getAllPercentagesByCourseIdAndsectionId(courseId,sectionId);
  }


  @GetMapping("/sections/{sectionId}/percentages")
  public ResponseEntity<List<Percentage>> getAllPercentagesBySectionId(@PathVariable(value = "sectionId") Long sectionId) {
    return percentageService.getAllPercentagesBySectionId(sectionId);
  }

  @GetMapping("/courses/{courseId}/percentages")
  public ResponseEntity<List<Percentage>> getAllPercentagesByCourseId(@PathVariable(value = "CourseId") Long courseId) {
    return percentageService.getAllPercentagesByCourseId(courseId);
  }

  @GetMapping("/percentages/{id}")
  public ResponseEntity<Percentage> getPercentagesById(@PathVariable(value = "id") Long id) {
    return percentageService.getPercentagesById(id);
  }


  @PostMapping("/sections/{sectionId}/courses/{courseId}/percentages")
  public ResponseEntity<Percentage> createPercentage(@PathVariable(value = "sectionId") Long sectionId,@PathVariable(value = "courseId") Long courseId,
      @RequestBody Percentage percentageRequest) {
      return percentageService.createPercentage(sectionId,courseId,percentageRequest);
  }

  @PutMapping("/percentages/{id}")
  public ResponseEntity<Percentage> updatePercentage(@PathVariable("id") long id, @RequestBody Percentage percentageRequest) {
    return percentageService.updatePercentage(id,percentageRequest);
  }

  @DeleteMapping("/percentages/{id}")
  public ResponseEntity<HttpStatus> deletePercentage(@PathVariable("id") long id) {
    return percentageService.deletePercentage(id);
  }
  
  @DeleteMapping("/sections/{sectionsId}/courses/{courseId}/percentages")
  public ResponseEntity<List<Percentage>> deletePercentagesByCourseAndSection(@PathVariable(value = "courseId") Long courseId, @PathVariable(value = "sectionId") Long sectionId) {
    return percentageService.deletePercentagesByCourseAndSection(courseId,sectionId);
  }
}
