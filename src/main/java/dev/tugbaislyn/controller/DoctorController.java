package dev.tugbaislyn.controller;

import dev.tugbaislyn.dto.request.DoctorRequest;
import dev.tugbaislyn.dto.response.DoctorResponse;
import dev.tugbaislyn.service.DoctorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/doctor")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;


    //CREATE
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public DoctorResponse create(@Valid @RequestBody DoctorRequest doctorRequest){
        return doctorService.create(doctorRequest);
    }

    //READ
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public DoctorResponse getById(@PathVariable("id") Long id){
        return doctorService.getById(id);

    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<DoctorResponse> getAll(){
        return doctorService.getAll();
    }

    //UPDATE
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public DoctorResponse update(@Valid @PathVariable("id") Long id, @RequestBody DoctorRequest doctorRequest){
        return doctorService.update(id,doctorRequest);
    }

    //DELETE
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") Long id){
        doctorService.delete(id);
    }
}
