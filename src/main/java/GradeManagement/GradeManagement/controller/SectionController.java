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


import GradeManagement.GradeManagement.model.Section;
import GradeManagement.GradeManagement.repository.SectionRepository;
import GradeManagement.GradeManagement.service.SectionService;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class SectionController {

    @Autowired
    SectionRepository sectionRepository;

    @Autowired
    SectionService sectionService;

    @GetMapping("/sections")
    public ResponseEntity<List<Section>> getAllSections(@RequestParam(required = false) String name) {
        return sectionService.getAllSections();
    }

    @GetMapping("/sections/{id}")
    public ResponseEntity<Section> getSectionById(@PathVariable("id") long id) {
        return sectionService.getSectionById(id);
    }

    @PostMapping("/sections")
    public ResponseEntity<Section> createSection(@RequestBody Section section) {
        return sectionService.createSection(section);
    }

    @PutMapping("/sections/{id}")
    public ResponseEntity<Section> updateSection(@PathVariable("id") long id, @RequestBody Section section) {
        return sectionService.updateSection(id,section);
    }

     @GetMapping("/courses/{courseId}/sections")
     public ResponseEntity<List<Section>> getAllSectionsByCourseId(@PathVariable(value = "courseId") Long courseId) {
       return sectionService.getAllSectionsByCourseId(courseId);
    }

    @DeleteMapping("/sections/{id}") //ok
    public ResponseEntity<HttpStatus> deleteSection(@PathVariable("id") long id) {
        return sectionService.deleteSection(id);
    }

    @DeleteMapping("/sections")
    public ResponseEntity<HttpStatus> deleteAllSections() {
        return sectionService.deleteAllSections();
    }


}
