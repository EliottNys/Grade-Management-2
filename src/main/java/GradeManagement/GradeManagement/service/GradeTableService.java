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
import GradeManagement.GradeManagement.model.Honor;
import GradeManagement.GradeManagement.model.Mean;
import GradeManagement.GradeManagement.model.Percentage;
import GradeManagement.GradeManagement.model.Section;
import GradeManagement.GradeManagement.model.Student;
import GradeManagement.GradeManagement.repository.CourseRepository;
import GradeManagement.GradeManagement.repository.GradeTableRepository;
import GradeManagement.GradeManagement.repository.HonorRepository;
import GradeManagement.GradeManagement.repository.MeanRepository;
import GradeManagement.GradeManagement.repository.PercentageRepository;
import GradeManagement.GradeManagement.repository.StudentRepository;

@Service
public class GradeTableService {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private HonorRepository honorRepository;

    @Autowired
    private MeanRepository meanRepository;

    @Autowired
    private GradeTableRepository gradeTableRepository;

    @Autowired
    private PercentageRepository PercentageRepository;

    @Autowired
    private MeanRepository MeanRepository;

    @Autowired
    private PercentageRepository percentageRepository;

        /**
    * Get the grade of a student for a particular course
    * @param studentId The id of the student
    * @param courseId The id of the course
    * @return HTTP response (OK if successful, NO_CONTENT if not found, INTERNAL_SERVER_ERROR if error occur)
    */
    public ResponseEntity<List<GradeTable>> getAllgradeBystudentIdAndCourseId(@PathVariable(value = "studentId") Long studentId,@PathVariable(value = "courseId") Long courseId) {
        if (!studentRepository.existsById(studentId)) {
            throw new ResourceNotFoundException("Not found student with id = " + studentId);
        }

        if (!courseRepository.existsById(courseId)) {
            throw new ResourceNotFoundException("Not found Course with id = " + courseId);
        }

        List<GradeTable> grade = gradeTableRepository.findByCourseIdAndStudentId(courseId,studentId);
        return new ResponseEntity<>(grade, HttpStatus.OK);
    }

            /**
    * Get a grade by its id
    * @param id the id of the grade
    * @return HTTP response (OK if successful, NO_CONTENT if not found, INTERNAL_SERVER_ERROR if error occur)
    */
    public ResponseEntity<GradeTable> getgradeById(@PathVariable(value = "id") Long id) {
        GradeTable gradeTable = gradeTableRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found GradeTable with id = " + id));

        return new ResponseEntity<>(gradeTable, HttpStatus.OK);
    }

            /**
    * Get all grades
    * @return HTTP response (OK if successful, NO_CONTENT if not found, INTERNAL_SERVER_ERROR if error occur)
    */
    public ResponseEntity<List<GradeTable>> getAllGrades() {
        List<GradeTable> grades = new ArrayList<GradeTable>();

        gradeTableRepository.findAll().forEach(grades::add);

        if (grades.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(grades, HttpStatus.OK);
    }

            /**
    * Create the grade of a student in a course, you have to assign a null value to each variable to register a student to a course
    * @param studentId The id of the student
    * @param courseId The id of the course
    * @param gradeTableRequest it's the body of the request
    * @return HTTP response (OK if successful, NO_CONTENT if not found, INTERNAL_SERVER_ERROR if error occur)
    */
    public ResponseEntity<GradeTable> createGradeTable(@PathVariable(value = "courseId") Long courseId,@PathVariable(value = "studentId") Long studentId,
                                                       @RequestBody GradeTable gradeTableRequest) {

        Course course = courseRepository.findById(courseId).orElseThrow(() -> new ResourceNotFoundException("Not found student with id = " + courseId));
        gradeTableRequest.setCourse(course);
        gradeTableRepository.save(gradeTableRequest);

        Student student = studentRepository.findById(studentId).orElseThrow(() -> new ResourceNotFoundException("Not found student with id = " + studentId));
        gradeTableRequest.setStudent(student);
        gradeTableRepository.save(gradeTableRequest);

        System.out.println(gradeTableRepository);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

            /**
    * Uptade the grade by its id
    * @param id The id of the grade
    * @param gradeTableRequest it's the body of the request
    * @return HTTP response (OK if successful, NO_CONTENT if not found, INTERNAL_SERVER_ERROR if error occur)
    */
    public ResponseEntity<GradeTable> updateGradeTable(@PathVariable("id") long id, @RequestBody GradeTable gradeTableRequest) {
        GradeTable gradeTable = gradeTableRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("gradeTableId " + id + "not found"));

        gradeTable.setSemester(gradeTableRequest.getSemester());
        gradeTable.setSchoolYear(gradeTableRequest.getSchoolYear());
        gradeTable.setComment(gradeTableRequest.getComment());
        gradeTable.setComment_teacher(gradeTableRequest.getComment_teacher());
        gradeTable.setGrade(gradeTableRequest.getGrade());

        return new ResponseEntity<>(gradeTableRepository.save(gradeTable), HttpStatus.OK);

    }

            /**
    * Get the level of the honor in function of the grade
    * @param grade Any grade
    * @return HTTP response (OK if successful, NO_CONTENT if not found, INTERNAL_SERVER_ERROR if error occur)
    */
    public String GetHonorLevel(Double grade){
        if (12 <= grade && grade < 14) {
            return "Cum Fructu";
        }
        if (14 <= grade && grade < 16) {
            return "Cum laude";
        }
        if (16 <= grade && grade < 18) {
            return "Magna cum laude";
        }
        if (18 <= grade && grade < 20) {
            return "Summa cum laude";
        }
        else {
            return "None";
        }
    }

            /**
    * Update the grade of a studnet in a particular course, this method is used because initally every student is register in a course with all the parameters at "null", it's with this update that a student get it's grade.
    This function is also used to calculate the mean of a section depending on the grade of the course in this particular section 
    * @param studentId The id of the student
    * @param courseId The id of the course
    * @param schoolYear the schoolYear when the student obtains the grade 
    * @param semester the semester when the student obtains the grade
    * @param gradeTableRequest it's the body of the request
    * @return HTTP response (OK if successful, NO_CONTENT if not found, INTERNAL_SERVER_ERROR if error occur)
    */
    public ResponseEntity<GradeTable> updateGrade(@PathVariable("courseID") long courseId,@PathVariable("studentID") long studentId,@PathVariable("SchoolYear") Integer schoolYear,@PathVariable("semester") String semester,@RequestBody GradeTable gradeTableRequest) {
        GradeTable gradeTable = gradeTableRepository.findByCourseIdAndStudentIdAndSchoolYearAndSemester(courseId, studentId, schoolYear, semester);
        gradeTable.setComment(gradeTableRequest.getComment());
        gradeTable.setComment_teacher(gradeTableRequest.getComment_teacher());
        gradeTable.setGrade(gradeTableRequest.getGrade());

        List<Mean> means = meanRepository.findByStudentId(studentId);
        List<Section> sections_with_student = new ArrayList<Section>() ;   //sections where this student is subscribed
        means.forEach((n) -> sections_with_student.add(n.getSection()));
        List<Percentage> percentages = PercentageRepository.findByCourseId(courseId);
        List<Section> sections_with_course = new ArrayList<Section>();    //sections which contain this course
        percentages.forEach((n) -> sections_with_course.add(n.getSection()));

        Integer sumBac = 0;
        Integer sumMa = 0;
        Double meanBac = 0.0;
        Double meanMa = 0.0;

        for (Section section: sections_with_student){
            if (sections_with_course.contains(section)){  //intersection of the 2 lists
                List<Course> courses = new ArrayList<Course>();
                PercentageRepository.findBySectionId(section.getId()).forEach((n) -> courses.add(n.getCourse()));
                Integer mean = 0;
                for (Course course : courses ){
                    GradeTable grade = gradeTableRepository.findByCourseIdAndStudentIdAndSchoolYearAndSemester(course.getId(),studentId, schoolYear, course.getSemester());
                    if (grade.getGrade() == null) {
                        mean = null;
                        break;
                    }
                    Integer percent = percentageRepository.findBySectionIdAndCourseId(section.getId(),course.getId()).get(0).getPercentage();
                    mean += grade.getGrade()*percent;

                };
                Mean meanus = meanRepository.findByStudentIdAndSectionIdAndSchoolYear(studentId, section.getId(),schoolYear);

                meanus.setMean(mean);
            }

            if (section.getLevel() < 4) {
                sumBac += section.getCredits();
            }
            else if (section.getLevel() < 6 && section.getLevel() > 3){
                sumMa += section.getCredits();
            }

            if (sumBac >=180) {
                List<Mean> meanBacList = meanRepository.findBySectionLevel(1);
                meanBacList.addAll(meanRepository.findBySectionLevel(2));
                meanBacList.addAll(meanRepository.findBySectionLevel(3));
                for (Mean mean : meanBacList) {
                    if (mean.getMean() == null ) {
                        meanBac = null;
                        break;
                    }
                    meanBac += (double) mean.getMean()*mean.getSection().getCredits()/18000;

                }
                if (meanBac != null) {
                    Student student = studentRepository.findById(studentId).orElseThrow(() -> new ResourceNotFoundException("Not found student with id = " + studentId));
                    Honor honor = new Honor(student, GetHonorLevel(meanBac), meanBac, "Bac");
                    honorRepository.save(honor);
                }
            }
            if (sumMa >= 120) {
                List<Mean> meanMaList = meanRepository.findBySectionLevel(4);
                meanMaList.addAll(meanRepository.findBySectionLevel(5));
                for (Mean mean : meanMaList) {
                    if (mean.getMean() == null ) {
                        meanMa = null;
                        break;
                    }
                    meanMa += (double)  mean.getMean()*mean.getSection().getCredits()/12000;
                }
                if (meanMa != null) {
                    Student student = studentRepository.findById(studentId).orElseThrow(() -> new ResourceNotFoundException("Not found student with id = " + studentId));
                    Honor honor = new Honor(student,GetHonorLevel(meanMa),meanMa,"Ma");
                    honorRepository.save(honor);
                }

            }
        }
        return new ResponseEntity<>(gradeTableRepository.save(gradeTable), HttpStatus.OK);
    }

            /**
    * Delete a grade by it's id
    * @param id The id of the grade
    * @return HTTP response (OK if successful, NO_CONTENT if not found, INTERNAL_SERVER_ERROR if error occur)
    */
    public ResponseEntity<HttpStatus> deleteGradeTable(@PathVariable("id") long id) {
        gradeTableRepository.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

            /**
    * delete a grade by its courseId and its studentId
    * @param studentId The id of the student
    * @param courseId The id of the course
    * @return HTTP response (OK if successful, NO_CONTENT if not found, INTERNAL_SERVER_ERROR if error occur)
    */
    public ResponseEntity<List<GradeTable>> deleteGradeTableByCourseIdAndStudentId( @PathVariable(value = "courseId") Long courseId,@PathVariable(value = "studentId") Long studentId) {
        if (!studentRepository.existsById(studentId)) {
            throw new ResourceNotFoundException("Not found student with id = " + studentId);
        }

        if (!courseRepository.existsById(courseId)) {
            throw new ResourceNotFoundException("Not found Course with id = " + courseId);
        }

        gradeTableRepository.deleteByCourseIdAndStudentId(courseId, studentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
