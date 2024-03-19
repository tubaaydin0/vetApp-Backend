package dev.tugbaislyn.controller;

import dev.tugbaislyn.dto.request.CustomerRequest;
import dev.tugbaislyn.dto.response.CustomerResponse;
import dev.tugbaislyn.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;


    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerResponse getById(@PathVariable("id") Long id){
        return customerService.getById(id);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<CustomerResponse> getAllOrName(@RequestParam(required = false) String name){
        return customerService.getAllOrName(name);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerResponse create(@Valid @RequestBody CustomerRequest customerRequest){
        return customerService.create(customerRequest);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public  CustomerResponse update(@Valid @PathVariable("id") Long id, @RequestBody CustomerRequest customerRequest){
        return customerService.update(id,customerRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") Long id){
        customerService.delete(id);
    }
}
