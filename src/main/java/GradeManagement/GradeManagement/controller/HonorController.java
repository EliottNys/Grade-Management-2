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

import GradeManagement.GradeManagement.model.Honor;
import GradeManagement.GradeManagement.repository.HonorRepository;
import GradeManagement.GradeManagement.service.HonorService;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class HonorController {
    @Autowired
    HonorRepository honorRepository;

    @Autowired
    HonorService honorService;

    @GetMapping("/honor")
    public ResponseEntity<List<Honor>> getAllHonors() { return honorService.getAllHonors(); }

    @GetMapping("/honor/{studentID}/{degree}")
    public ResponseEntity<Honor> getHonorByStudentIdAndDegree(@PathVariable("studentID") long studentID,@PathVariable("degree") String degree  ) {
        return honorService.getHonorByStudentIdAndDegree(studentID,degree);
    }

    @PostMapping("/honor")
    public ResponseEntity<Honor> createHonor(@RequestBody Honor honor) {
        return honorService.createHonor(honor);
    }

    @PutMapping("/honor/{studentID}/{degree}")
    public ResponseEntity<Honor> updateHonor(@PathVariable("studentID") long studentID, @PathVariable("degree") String degree, @RequestBody Honor honor) {
        return honorService.updateHonor(studentID,degree,honor);
    }

    @DeleteMapping("/honor/{studentID}/{degree}")
    public ResponseEntity<HttpStatus> deleteHonor(@PathVariable("studentID") long studentID, @PathVariable("degree") String degree) {
        return honorService.deleteHonor(studentID,degree);
    }

    @DeleteMapping("/honor")
    public ResponseEntity<HttpStatus> deleteAllHonors() {
        return honorService.deleteAllHonors();
    }
}
