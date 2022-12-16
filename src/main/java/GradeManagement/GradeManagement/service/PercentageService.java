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
import GradeManagement.GradeManagement.model.Course;
import GradeManagement.GradeManagement.model.Percentage;
import GradeManagement.GradeManagement.model.Section;
import GradeManagement.GradeManagement.repository.CourseRepository;
import GradeManagement.GradeManagement.repository.PercentageRepository;
import GradeManagement.GradeManagement.repository.SectionRepository;

@Service
public class PercentageService {
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private PercentageRepository percentageRepository;

    /**
     * Get all contents of the table "percentage"
     * @return HTTP response (OK if successful, NO_CONTENT if not found, INTERNAL_SERVER_ERROR if error occur)
     */
    public ResponseEntity<List<Percentage>> getAllPercentages() {
        List<Percentage> percentages = new ArrayList<Percentage>();

        percentageRepository.findAll().forEach(percentages::add);

        if (percentages.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(percentages, HttpStatus.OK);
    }

    /**
     * Get all contents of the "percentage" table by courseId and sectionId
     * @param courseId the id of the course
     * @param sectionId the id of the section
     * @return HTTP response (OK if successful, NO_CONTENT if not found, INTERNAL_SERVER_ERROR if error occur)
     */
    public ResponseEntity<List<Percentage>> getAllPercentagesByCourseIdAndsectionId(@PathVariable(value = "courseId") Long courseId,@PathVariable(value = "sectionId") Long sectionId) {
        if (!courseRepository.existsById(courseId)) {
            throw new ResourceNotFoundException("Not found course with id = " + courseId);
        }

        if (!sectionRepository.existsById(sectionId)) {
            throw new ResourceNotFoundException("Not found section with id = " + sectionId);
        }

        List<Percentage> percentages = percentageRepository.findBySectionIdAndCourseId(sectionId,courseId);
        return new ResponseEntity<>(percentages, HttpStatus.OK);
    }


    /**
     * Get all contents of the "percentage" table sectionId
     * @param sectionId the id of the section
     * @return HTTP response (OK if successful, NO_CONTENT if not found, INTERNAL_SERVER_ERROR if error occur)
     */
    public ResponseEntity<List<Percentage>> getAllPercentagesBySectionId(@PathVariable(value = "sectionId") Long sectionId) {

        if (!sectionRepository.existsById(sectionId)) {
            throw new ResourceNotFoundException("Not found section with id = " + sectionId);
        }

        List<Percentage> percentages = percentageRepository.findBySectionId(sectionId);
        return new ResponseEntity<>(percentages, HttpStatus.OK);
    }



    /**
     * Get all contents of the "percentage" table by courseId and sectionId
     * @param courseId the id of the course
     * @return HTTP response (OK if successful, NO_CONTENT if not found, INTERNAL_SERVER_ERROR if error occur)
     */
    public ResponseEntity<List<Percentage>> getAllPercentagesByCourseId(@PathVariable(value = "CourseId") Long courseId) {

        if (!sectionRepository.existsById(courseId)) {
            throw new ResourceNotFoundException("Not found section with id = " + courseId);
        }

        List<Percentage> percentages = percentageRepository.findByCourseId(courseId);
        return new ResponseEntity<>(percentages, HttpStatus.OK);
    }


    /**
     * Get all contents of the "percentage" table by courseId and sectionId
     * @param id the id of the percentage
     * @return HTTP response (OK if successful, NO_CONTENT if not found, INTERNAL_SERVER_ERROR if error occur)
     */
    public ResponseEntity<Percentage> getPercentagesById(@PathVariable(value = "id") Long id) {
        Percentage percentage = percentageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found percentage with id = " + id));

        return new ResponseEntity<>(percentage, HttpStatus.OK);
    }


    /**
     * Get all contents of the "percentage" table by courseId and sectionId
     * @param courseId the id of the course
     * @param sectionId the id of the section
     * @param percentageRequest the body of the request
     * @return HTTP response (OK if successful, NO_CONTENT if not found, INTERNAL_SERVER_ERROR if error occur)
     */
    public ResponseEntity<Percentage> createPercentage(@PathVariable(value = "sectionId") Long sectionId,@PathVariable(value = "courseId") Long courseId,
                                                       @RequestBody Percentage percentageRequest) {


        Section section = sectionRepository.findById(sectionId).orElseThrow(() -> new ResourceNotFoundException("Not found Course with id = " + sectionId));
        percentageRequest.setSection(section);
        percentageRepository.save(percentageRequest);

        Course Course = courseRepository.findById(courseId).orElseThrow(() -> new ResourceNotFoundException("Not found Course with id = " + courseId));
        percentageRequest.setCourse(Course);
        percentageRepository.save(percentageRequest);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * Update all contents of the percentage "table"
     * @param id the id of the "percentage" table
     * @param percentageRequest the body of the request
     * @return HTTP response (OK if successful, NO_CONTENT if not found, INTERNAL_SERVER_ERROR if error occur)
     */
    public ResponseEntity<Percentage> updatePercentage(@PathVariable("id") long id, @RequestBody Percentage percentageRequest) {
        Percentage percentage = percentageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PercentageId " + id + "not found"));

        percentage.setPercentage(percentageRequest.getPercentage());

        return new ResponseEntity<>(percentageRepository.save(percentage), HttpStatus.OK);
    }

    /**
     * Get all contents of the "percentage" table by courseId and sectionId
     * @param id the id of the "percentage" table
     * @return HTTP response (OK if successful, NO_CONTENT if not found, INTERNAL_SERVER_ERROR if error occur)
     */
    public ResponseEntity<HttpStatus> deletePercentage(@PathVariable("id") long id) {
        percentageRepository.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Delete all contents of the "percentage" table by ID
     * @param courseId the id of the course
     * @param sectionId the id of the section
     * @return HTTP response (OK if successful, NO_CONTENT if not found, INTERNAL_SERVER_ERROR if error occur)
     */
    public ResponseEntity<List<Percentage>> deletePercentagesByCourseAndSection(@PathVariable(value = "courseId") Long courseId, @PathVariable(value = "sectionId") Long sectionId) {
        if (!courseRepository.existsById(courseId)) {
            throw new ResourceNotFoundException("Not found course with id = " + courseId);
        }

        if (!sectionRepository.existsById(sectionId)) {
            throw new ResourceNotFoundException("Not found section with id = " + sectionId);
        }

        percentageRepository.deleteBySectionIdAndCourseId(sectionId, courseId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
