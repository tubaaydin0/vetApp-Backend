package dev.tugbaislyn.controller;

import dev.tugbaislyn.dto.request.AvailableDateRequest;
import dev.tugbaislyn.dto.response.AvailableDateResponse;

import dev.tugbaislyn.service.AvailableDateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/availableDate")
@RequiredArgsConstructor
public class AvailableDateContoller {
    private  final AvailableDateService availableDateService;


    //CREATE
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public AvailableDateResponse create(@Valid @RequestBody AvailableDateRequest availableDateRequest){
        return availableDateService.create(availableDateRequest);
    }

    //READ
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AvailableDateResponse getById(@PathVariable("id") Long id){
        return availableDateService.getById(id);
    }

    @GetMapping("/doctor/{doctorId}")
    @ResponseStatus(HttpStatus.OK)
    public List<AvailableDateResponse> getByDoctorId(@PathVariable Long doctorId) {
        return availableDateService.getByDoctorId(doctorId);
    }
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<AvailableDateResponse> getAll(){
        return availableDateService.getAll();
    }

    //UPDATE

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AvailableDateResponse update(@Valid @PathVariable("id") Long id, @RequestBody AvailableDateRequest availableDateRequest){
        return availableDateService.update(id,availableDateRequest);
    }

    //DELETE
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public  void delete(@PathVariable("id") Long id){
        availableDateService.delete(id);
    }
}
