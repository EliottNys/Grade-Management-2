package GradeManagement.GradeManagement.repository;

import java.util.List;

import javax.transaction.Transactional;

import GradeManagement.GradeManagement.model.Honor;
import org.springframework.data.jpa.repository.JpaRepository;


public interface HonorRepository extends JpaRepository<Honor, Long> {

    Honor findByStudentIdAndDegree(long studentID, String degree);


    @Transactional
    void deleteByStudentIdAndDegree(long studentID, String degree);
}