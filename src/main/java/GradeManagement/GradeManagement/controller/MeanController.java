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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import GradeManagement.GradeManagement.model.Mean;
import GradeManagement.GradeManagement.model.Section;
import GradeManagement.GradeManagement.model.Student;
import GradeManagement.GradeManagement.service.MeanService;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class MeanController {

  @Autowired
  private MeanService meanService;


  @GetMapping("/means") //ok
  public ResponseEntity<List<Mean>> getAllMeans(@RequestParam(required = false) Integer mean, @RequestParam(required = false) Student student, @RequestParam(required = false) Section section) {
    return meanService.getAllMeans();
  }

  @GetMapping("/sections/{sectionId}/students/{studentId}/means")
  public ResponseEntity<List<Mean>> getAllMeansBystudentIdAndsectionId(@PathVariable(value = "studentId") Long studentId, @PathVariable(value = "sectionId") Long sectionId) {
    return meanService.getAllMeansBystudentIdAndsectionId(studentId, sectionId);
  }


  @GetMapping("/sections/{sectionId}/means")
  public ResponseEntity<List<Mean>> getAllMeansBysectionId(@PathVariable(value = "sectionId") Long sectionId) {
    return meanService.getAllMeansBysectionId(sectionId);
  }

  @GetMapping("/students/{studentId}/means")
  public ResponseEntity<List<Mean>> getAllMeansBystudentId(@PathVariable(value = "studentId") Long studentId) {
    return meanService.getAllMeansBystudentId(studentId);
  }

  @GetMapping("/means/{id}")
  public ResponseEntity<Mean> getMeansById(@PathVariable(value = "id") Long id) {
    return meanService.getMeansById(id);
  }


  @PostMapping("/sections/{sectionId}/students/{studentId}/means")
  public ResponseEntity<Mean> createMean(@PathVariable(value = "sectionId") Long sectionId, @PathVariable(value = "studentId") Long studentId, @RequestBody Mean MeanRequest) {
    return meanService.createMean(sectionId, studentId, MeanRequest);
  }

  @PutMapping("/means/{sectionId}/{studentId}")
  public ResponseEntity<Mean> updateMean(@PathVariable("sectionId") long sectionId, @PathVariable("studentId") long studentId, @RequestBody Mean MeanRequest) {
    return meanService.updateMean(sectionId, sectionId, MeanRequest);
  }

  @DeleteMapping("/means/{id}")
  public ResponseEntity<HttpStatus> deleteMean(@PathVariable("id") long id) {
    return meanService.deleteMean(id);
  }

  @DeleteMapping("/sections/{sectionsId}/students/{studentId}/means")
  public ResponseEntity<List<Mean>> deleteMeanOfstudentInSection(@PathVariable(value = "studentId") Long studentId, @PathVariable(value = "sectionId") Long sectionId) {
    return meanService.deleteMeanOfstudentInSection(studentId, sectionId);
  }
}