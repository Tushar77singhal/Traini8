package com.assignment.demo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/training-centers")
public class TrainingCenterController {
    private final TrainingCenterRepository trainingCenterRepository;

    public TrainingCenterController(TrainingCenterRepository trainingCenterRepository) {
        this.trainingCenterRepository = trainingCenterRepository;
    }

    @PostMapping
    public ResponseEntity<TrainingCenter> createTrainingCenter(
            @RequestBody TrainingCenter trainingCenter) {
        // Set the 'createdOn' field with the current epoch time
        trainingCenter.setCreatedOn(Instant.now().getEpochSecond());

        // Save the training center
        TrainingCenter savedCenter = trainingCenterRepository.save(trainingCenter);

        // Return the newly created training center with HTTP 201 status
        return new ResponseEntity<>(savedCenter, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<TrainingCenter>> getAllTrainingCenters() {
        List<TrainingCenter> trainingCenters = trainingCenterRepository.findAll();
        return ResponseEntity.ok(trainingCenters);
    }

    // Get a training center by ID
    @GetMapping("/{id}")
    public ResponseEntity<TrainingCenter> getTrainingCenterById(@PathVariable("id") Long id) {
        Optional<TrainingCenter> trainingCenter = trainingCenterRepository.findById(id);
        return trainingCenter.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}