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
import io.swagger.v3.oas.annotations.Operation;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class SectionController {

    @Autowired
    SectionRepository sectionRepository;

    @Autowired
    SectionService sectionService;

    @Operation(summary = "Get all sections")
    @GetMapping("/sections")
    public ResponseEntity<List<Section>> getAllSections(@RequestParam(required = false) String name) {
        return sectionService.getAllSections();
    }

    @Operation(summary = "Get a section by id")
    @GetMapping("/sections/{id}")
    public ResponseEntity<Section> getSectionById(@PathVariable("id") long id) {
        return sectionService.getSectionById(id);
    }

    @Operation(summary = "Create a new section")
    @PostMapping("/sections")
    public ResponseEntity<Section> createSection(@RequestBody Section section) {
        return sectionService.createSection(section);
    }

    @Operation(summary = "Update a section by id")
    @PutMapping("/sections/{id}")
    public ResponseEntity<Section> updateSection(@PathVariable("id") long id, @RequestBody Section section) {
        return sectionService.updateSection(id,section);
    }

    @Operation(summary = "Delete a section by id")
    @DeleteMapping("/sections/{id}") //ok
    public ResponseEntity<HttpStatus> deleteSection(@PathVariable("id") long id) {
        return sectionService.deleteSection(id);
    }

    @Operation(summary = "Delete all sections")
    @DeleteMapping("/sections")
    public ResponseEntity<HttpStatus> deleteAllSections() {
        return sectionService.deleteAllSections();
    }


}
