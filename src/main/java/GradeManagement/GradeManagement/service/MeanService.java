package GradeManagement.GradeManagement.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import GradeManagement.GradeManagement.exception.ResourceNotFoundException;
import GradeManagement.GradeManagement.model.Course;
import GradeManagement.GradeManagement.model.GradeTable;
import GradeManagement.GradeManagement.model.Mean;
import GradeManagement.GradeManagement.model.Percentage;
import GradeManagement.GradeManagement.model.Section;
import GradeManagement.GradeManagement.model.Student;
import GradeManagement.GradeManagement.repository.CourseRepository;
import GradeManagement.GradeManagement.repository.GradeTableRepository;
import GradeManagement.GradeManagement.repository.MeanRepository;
import GradeManagement.GradeManagement.repository.PercentageRepository;
import GradeManagement.GradeManagement.repository.SectionRepository;
import GradeManagement.GradeManagement.repository.StudentRepository;

@Service
public class MeanService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private MeanRepository meanRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private GradeTableRepository gradeTableRepository;

    @Autowired
    private PercentageRepository percentageRepository;
    public ResponseEntity<List<Mean>> getAllMeans() {
        List<Mean> means = new ArrayList<Mean>();

        meanRepository.findAll().forEach(means::add);

        return new ResponseEntity<>(means, HttpStatus.OK);
    }
                /**
    * Get all means by studentid and sectionId
    * @param studentId The id of the student
    * @param sectionId The id of the section
    * @return HTTP response (OK if successful, NO_CONTENT if not found, INTERNAL_SERVER_ERROR if error occur)
    */
    public ResponseEntity<List<Mean>> getAllMeansBystudentIdAndsectionId(@PathVariable(value = "studentId") Long studentId,@PathVariable(value = "sectionId") Long sectionId) {
        if (!studentRepository.existsById(studentId)) {
            throw new ResourceNotFoundException("Not found student with id = " + studentId);
        }

        if (!sectionRepository.existsById(sectionId)) {
            throw new ResourceNotFoundException("Not found section with id = " + sectionId);
        }

        List<Mean> Means = meanRepository.findBySectionIdAndStudentId(sectionId,studentId);
        return new ResponseEntity<>(Means, HttpStatus.OK);
    }

                    /**
    * Get all means by section id
    * @param sectionId The id of the section
    * @return HTTP response (OK if successful, NO_CONTENT if not found, INTERNAL_SERVER_ERROR if error occur)
    */
    public ResponseEntity<List<Mean>> getAllMeansBysectionId(@PathVariable(value = "sectionId") Long sectionId) {

        if (!sectionRepository.existsById(sectionId)) {
            throw new ResourceNotFoundException("Not found section with id = " + sectionId);
        }

        List<Mean> Means = meanRepository.findBySectionId(sectionId);
        return new ResponseEntity<>(Means, HttpStatus.OK);
    }

                    /**
    * Get all means by studentId
    * @param studentId The id of the student
    * @return HTTP response (OK if successful, NO_CONTENT if not found, INTERNAL_SERVER_ERROR if error occur)
    */
    public ResponseEntity<List<Mean>> getAllMeansBystudentId(@PathVariable(value = "studentId") Long studentId) {

        if (!sectionRepository.existsById(studentId)) {
            throw new ResourceNotFoundException("Not found section with id = " + studentId);
        }

        List<Mean> Means = meanRepository.findByStudentId(studentId);
        return new ResponseEntity<>(Means, HttpStatus.OK);
    }

                    /**
    * Get a mean by id
    * @param id The id of the mean
    * @return HTTP response (OK if successful, NO_CONTENT if not found, INTERNAL_SERVER_ERROR if error occur)
    */
    public ResponseEntity<Mean> getMeansById(@PathVariable(value = "id") Long id) {
        Mean mean = meanRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Mean with id = " + id));

        return new ResponseEntity<>(mean, HttpStatus.OK);
    }

                    /**
    * Register a student to a course
    * @param studentId The id of the student
    * @param courseId The id of the course
    * @param schoolYear The schoolYear of the particular mean
    * @return HTTP response (OK if successful, NO_CONTENT if not found, INTERNAL_SERVER_ERROR if error occur)
    */
    public void InscriptionCours(Long courseId, Long studentId, Integer schoolYear){
            GradeTable gradeTableRequest = new GradeTable();
            Course course = courseRepository.findById(courseId).orElseThrow(() -> new ResourceNotFoundException("Not found student with id = " + courseId));
            gradeTableRequest.setCourse(course);
            Student student = studentRepository.findById(studentId).orElseThrow(() -> new ResourceNotFoundException("Not found student with id = " + studentId));
            gradeTableRequest.setStudent(student);
            gradeTableRequest.setSchoolYear(schoolYear);
            gradeTableRequest.setSemester(course.getSemester());
            gradeTableRepository.save(gradeTableRequest);
    }

                    /**
    * Register a student a student to a course with API
    * @param studentId The id of the student
    * @param sectionId The id of the section
    * @param MeanRequest The body of the request
    * @return HTTP response (OK if successful, NO_CONTENT if not found, INTERNAL_SERVER_ERROR if error occur)
    */
    public ResponseEntity<Mean> createMean(@PathVariable(value = "sectionId") Long sectionId,@PathVariable(value = "studentId") Long studentId, @RequestBody Mean MeanRequest) {
        Section section = sectionRepository.findById(sectionId).orElseThrow(() -> new ResourceNotFoundException("Not found student with id = " + sectionId));
        MeanRequest.setSection(section);
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new ResourceNotFoundException("Not found student with id = " + studentId));
        MeanRequest.setStudent(student);

        meanRepository.save(MeanRequest);

        List<Percentage> percentages = percentageRepository.findBySectionId(sectionId);

        for ( Percentage percent : percentages) {
            Long courseId = percent.getCourse().getId();
            InscriptionCours(courseId,studentId,2022);
        };

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

                    /**
    * Update a means by studentId and sectionId
    * @param studentId The id of the student
    * @param sectionId The id of the section
    * @param MeanRequest The body of the request
    * @return HTTP response (OK if successful, NO_CONTENT if not found, INTERNAL_SERVER_ERROR if error occur)
    */
    public ResponseEntity<Mean> updateMean(@PathVariable("sectionId") long sectionId,@PathVariable("studentId") long studentId, @RequestBody Mean MeanRequest) {
        Mean Mean = meanRepository.findByStudentIdAndSectionIdAndSchoolYear(studentId,sectionId,MeanRequest.getSchoolYear());

        Mean.setMean(MeanRequest.getMean());
        Mean.setSchoolYear(MeanRequest.getSchoolYear());
        return new ResponseEntity<>(meanRepository.save(Mean), HttpStatus.OK);
    }

                        /**
    * Delete a mean by id
    * @param id The id of the mean
    * @return HTTP response (OK if successful, NO_CONTENT if not found, INTERNAL_SERVER_ERROR if error occur)
    */
    public ResponseEntity<HttpStatus> deleteMean(@PathVariable("id") long id) {
        meanRepository.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

                        /**
    * Delete a means by studentid and sectionId
    * @param studentId The id of the student
    * @param sectionId The id of the section
    * @return HTTP response (OK if successful, NO_CONTENT if not found, INTERNAL_SERVER_ERROR if error occur)
    */
    public ResponseEntity<List<Mean>> deleteMeanOfstudentInSection(@PathVariable(value = "studentId") Long studentId, @PathVariable(value = "sectionId") Long sectionId) {
        if (!studentRepository.existsById(studentId)) {
            throw new ResourceNotFoundException("Not found student with id = " + studentId);
        }

        if (!sectionRepository.existsById(sectionId)) {
            throw new ResourceNotFoundException("Not found section with id = " + sectionId);
        }

        meanRepository.deleteBySectionIdAndStudentId(sectionId, studentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}




    

