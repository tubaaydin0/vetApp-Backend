package dev.tugbaislyn.controller;

import dev.tugbaislyn.dto.request.AnimalRequest;
import dev.tugbaislyn.dto.response.AnimalResponse;
import dev.tugbaislyn.service.AnimalService;
import dev.tugbaislyn.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//Değerlendirme Formu 26,27
@RestController
@RequestMapping("/v1/animal")
@RequiredArgsConstructor
public class AnimalController {

    private final AnimalService animalService;

    //	Hayvanları kaydetme, bilgilerin güncelleme, görüntüleme ve silme  ,
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AnimalResponse getById(@Valid @PathVariable("id") Long id){
        return animalService.getById(id);

    }

    //Animal ismine göre filtreleme
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<AnimalResponse> getByAnimalNameorAll(@Valid @RequestParam(required = false) String name){
        return animalService.getByAnimalNameorAll(name);
    }

    @GetMapping("/customer")
    @ResponseStatus(HttpStatus.OK)
    public List<AnimalResponse> getByCustomerName(@Valid @RequestParam String name){
        return animalService.getByCustomerName(name);
    }

    //Hayvan sahibinin sistemde kayıtlı olan hayvanlarını listeleme
    @GetMapping("/customer/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<AnimalResponse> getByCustomerId(@Valid @PathVariable("id") Long id){
        //CustomerResponse customerResponse=customerService.getById(id);
        return animalService.getByCustomerId(id);
    }



   /* @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<AnimalResponse> getAll(){
        return animalService.getAll();
    }

    */

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public AnimalResponse create(@RequestBody AnimalRequest animalRequest){
        return animalService.create(animalRequest);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AnimalResponse update(@Valid @PathVariable("id") Long id , @RequestBody AnimalRequest animalRequest){
        return animalService.update(id, animalRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") Long id){
        animalService.delete(id);
    }
}
