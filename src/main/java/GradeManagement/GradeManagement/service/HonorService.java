package GradeManagement.GradeManagement.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import GradeManagement.GradeManagement.model.Honor;

import GradeManagement.GradeManagement.repository.HonorRepository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class HonorService {
    @Autowired
    HonorRepository honorRepository;

    public ResponseEntity<List<Honor>> getAllHonors() {
        List<Honor> honors = new ArrayList<>();

        honorRepository.findAll().forEach(honors::add);

        if (honors.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(honors, HttpStatus.OK);
    }

    public ResponseEntity<Honor> getHonorByStudentIdAndDegree(@PathVariable("studentID") long studentID, @PathVariable("degree") String degree  ) {

        Honor honor = honorRepository.findByStudentIdAndDegree(studentID, degree);

        return new ResponseEntity<>(honor, HttpStatus.OK);
    }

    public ResponseEntity<Honor> createHonor(@RequestBody Honor honor) {
        Honor _honor = honorRepository
                .save(new Honor(honor.getStudent(),honor.getHonor(),honor.getGrade(),honor.getDegree()));
        return new ResponseEntity<>(_honor, HttpStatus.CREATED);
    }
    public ResponseEntity<Honor> updateHonor(@PathVariable("studentID") long studentID, @PathVariable("degree") String degree, @RequestBody Honor honor) {
        Honor _honor = honorRepository.findByStudentIdAndDegree(studentID,degree);

        _honor.setGrade(honor.getGrade());
        _honor.setHonor(honor.getHonor());

        return new ResponseEntity<>(honorRepository.save(_honor), HttpStatus.OK);
    }
    @DeleteMapping("/honor/{studentID}/{degree}")
    public ResponseEntity<HttpStatus> deleteHonor(@PathVariable("studentID") long studentID, @PathVariable("degree") String degree) {
        honorRepository.deleteByStudentIdAndDegree(studentID,degree);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    public ResponseEntity<HttpStatus> deleteAllHonors() {
        honorRepository.deleteAll();

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
