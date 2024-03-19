package dev.tugbaislyn.controller;

import dev.tugbaislyn.dto.request.AppointmentRequest;
import dev.tugbaislyn.dto.response.AppointmentResponse;
import dev.tugbaislyn.service.AppointmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/v1/appointment")
@RequiredArgsConstructor
public class AppointmentController {
    private final AppointmentService appointmentService;


    //Create
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public AppointmentResponse createAppointment(@Valid @RequestBody AppointmentRequest appointmentRequest){
        return appointmentService.createAppointment(appointmentRequest);
    }

    //Read
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AppointmentResponse getById(@PathVariable("id") Long id){
        return appointmentService.getById(id);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<AppointmentResponse> getAll(){
        return appointmentService.getAll();
    }


    //Randevular kullanıcı tarafından girilen tarih aralığına ve doktora göre filtrelenmesi
    @GetMapping("/filter/doctor")
    @ResponseStatus(HttpStatus.OK)
    public List<AppointmentResponse> getForDateAndDoctor(@RequestParam Long doctorId,
                                                  @RequestParam LocalDate startDate,
                                                  @RequestParam LocalDate endDate){
        return  appointmentService.getForDateAndDoctor(doctorId,startDate,endDate);
    }
    //Randevular kullanıcı tarafından girilen tarih aralığına ve hayvana göre filtrelenmesi

    @GetMapping("/filter/animal")
    @ResponseStatus(HttpStatus.OK)
    public List<AppointmentResponse> getForDateAndAnimal(@RequestParam Long animalId,
                                                         @RequestParam LocalDate startDate,
                                                         @RequestParam LocalDate endDate){
        return  appointmentService.getForDateAndAnimal(animalId,startDate,endDate);
    }


    //Update
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AppointmentResponse update(@Valid @PathVariable("id") Long id , @RequestBody AppointmentRequest appointmentRequest){
        return appointmentService.update(id,appointmentRequest);
    }


    //Delete
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public  void delete(@PathVariable("id") Long id){
        appointmentService.delete(id);
    }
}
