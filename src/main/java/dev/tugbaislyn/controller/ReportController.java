package dev.tugbaislyn.controller;

import dev.tugbaislyn.dto.request.AnimalRequest;
import dev.tugbaislyn.dto.request.CustomerRequest;
import dev.tugbaislyn.dto.request.ReportRequest;
import dev.tugbaislyn.dto.response.AnimalResponse;
import dev.tugbaislyn.dto.response.CustomerResponse;
import dev.tugbaislyn.dto.response.ReportResponse;
import dev.tugbaislyn.entity.Report;
import dev.tugbaislyn.service.ReportService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/report")
@RequiredArgsConstructor
public class ReportController {

    private  final ReportService reportService;

    //READ
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<ReportResponse> getAll(){
        return reportService.getAll();
    }

    @GetMapping("/animal")
    @ResponseStatus(HttpStatus.OK)
    public List<ReportResponse> getReportsById(@RequestParam Long animalId){
        return reportService.getReportsByAnimalId(animalId);
    }



    //CREATE
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ReportResponse create(@RequestBody ReportRequest reportRequest){
        return reportService.create(reportRequest);
    }

    //UPDATE
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ReportResponse update(@Valid @PathVariable("id") Long id, @RequestBody ReportRequest reportRequest){
        return reportService.update(id,reportRequest);
    }

    //DELETE

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public  void delete(@PathVariable("id") Long id){
        reportService.delete(id);
    }
}
