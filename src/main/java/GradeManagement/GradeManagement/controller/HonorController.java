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
import GradeManagement.GradeManagement.service.HonorService;
import io.swagger.v3.oas.annotations.Operation;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class HonorController {


    @Autowired
    HonorService honorService;

    @Operation(summary = "Get all honors")
    @GetMapping("/honor")
    public ResponseEntity<List<Honor>> getAllHonors() { return honorService.getAllHonors(); }

    @Operation(summary = "Get a honor by studentId")
    @GetMapping("/honor/{studentID}/{degree}")
    public ResponseEntity<Honor> getHonorByStudentIdAndDegree(@PathVariable("studentID") long studentID,@PathVariable("degree") String degree  ) {
        return honorService.getHonorByStudentIdAndDegree(studentID,degree);
    }

    @Operation(summary = "Create a honor")
    @PostMapping("/honor")
    public ResponseEntity<Honor> createHonor(@RequestBody Honor honor) {
        return honorService.createHonor(honor);
    }

    @Operation(summary = "Update a honors by studentId and the name of the honor")
    @PutMapping("/honor/{studentID}/{degree}")
    public ResponseEntity<Honor> updateHonor(@PathVariable("studentID") long studentID, @PathVariable("degree") String degree, @RequestBody Honor honor) {
        return honorService.updateHonor(studentID,degree,honor);
    }

    @Operation(summary = "Delete a honors by studentId and the name of the honor")
    @DeleteMapping("/honor/{studentID}/{degree}")
    public ResponseEntity<HttpStatus> deleteHonor(@PathVariable("studentID") long studentID, @PathVariable("degree") String degree) {
        return honorService.deleteHonor(studentID,degree);
    }

    @Operation(summary = "Delete all honors")
    @DeleteMapping("/honor")
    public ResponseEntity<HttpStatus> deleteAllHonors() {
        return honorService.deleteAllHonors();
    }
}
