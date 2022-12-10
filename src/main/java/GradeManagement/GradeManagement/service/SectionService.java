package GradeManagement.GradeManagement.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.stereotype.Service;

import GradeManagement.GradeManagement.exception.ResourceNotFoundException;
import GradeManagement.GradeManagement.model.Section;
import GradeManagement.GradeManagement.repository.SectionRepository;
import GradeManagement.GradeManagement.repository.CourseRepository;

@Service
public class SectionService {
    @Autowired
    SectionRepository sectionRepository;

    @Autowired
    CourseRepository courseRepository;


    public ResponseEntity<List<Section>> getAllSections() {
        List<Section> sections = new ArrayList<Section>();
        sectionRepository.findAll().forEach(sections::add);

        if (sections.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(sections, HttpStatus.OK);
    }

    public ResponseEntity<Section> getSectionById(@PathVariable("id") long id) {
        Section section = sectionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Section with id = " + id));

        return new ResponseEntity<>(section, HttpStatus.OK);
    }

    public ResponseEntity<Section> createSection(@RequestBody Section section) {
        Section _section = sectionRepository
                .save(new Section(section.getName(), section.getCredits(),section.getLevel()));
        return new ResponseEntity<>(_section, HttpStatus.CREATED);
    }

    public ResponseEntity<Section> updateSection(@PathVariable("id") long id, @RequestBody Section section) {
        Section _section = sectionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Section with id = " + id));

        _section.setName(section.getName());
        _section.setCredits(section.getCredits());

        return new ResponseEntity<>(sectionRepository.save(_section), HttpStatus.OK);
    }

    public ResponseEntity<HttpStatus> deleteSection(@PathVariable("id") long id) {
        sectionRepository.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<HttpStatus> deleteAllSections() {
        sectionRepository.deleteAll();

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
