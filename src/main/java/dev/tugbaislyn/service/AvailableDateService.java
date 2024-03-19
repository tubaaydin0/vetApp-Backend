package dev.tugbaislyn.service;

import dev.tugbaislyn.core.exception.ConflictException;
import dev.tugbaislyn.core.exception.NotFoundException;
import dev.tugbaislyn.core.utilies.Msg;
import dev.tugbaislyn.dao.AvailableDateRepo;
import dev.tugbaislyn.dto.request.AvailableDateRequest;
import dev.tugbaislyn.dto.response.AvailableDateResponse;
import dev.tugbaislyn.entity.AvailableDate;
import dev.tugbaislyn.core.config.modelMapper.ModelMapperService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AvailableDateService {
    private final AvailableDateRepo availableDateRepo;
    private final ModelMapperService modelMapper;

    //CREATE
    //Değerlendirme Formu 13
    public AvailableDateResponse create(AvailableDateRequest availableDateRequest){
        Optional<AvailableDate> isAvailableDateExist=availableDateRepo.findByAvailableDateAndDoctorId(availableDateRequest.getAvailableDate(),availableDateRequest.getDoctor().getId());
        if (isAvailableDateExist.isPresent()){
            throw new ConflictException(Msg.CONFLICT);
            //throw new RuntimeException("Bu doktora zaten bu tarih eklenmiştir, tekrar aynı tarih eklenemez!");
        }else{
            AvailableDate availableDate=modelMapper.forRequest().map(availableDateRequest,AvailableDate.class);
            return modelMapper.forResponse().map(availableDateRepo.save(availableDate), AvailableDateResponse.class);
        }

    }

    //READ
    public AvailableDateResponse getById(Long id){
        Optional<AvailableDate> availableDateIdDB=availableDateRepo.findById(id);
        if (availableDateIdDB.isEmpty()){
            throw new NotFoundException(id+ Msg.NOT_FOUND);
        }
        else{
            return modelMapper.forResponse().map(availableDateIdDB.get(),AvailableDateResponse.class);
        }
    }
    public List<AvailableDateResponse> getAll(){
        List<AvailableDateResponse> availableDateResponseList=availableDateRepo.findAll()
                .stream()
                .map(
                        availableDate -> modelMapper.forResponse().map(availableDate,AvailableDateResponse.class)
                ).collect(Collectors.toList());
        return  availableDateResponseList;
    }
    public List<AvailableDateResponse> getByDoctorId(Long doctorId) {
        List<AvailableDate> availableDateIdDB=availableDateRepo.findByDoctorId(doctorId);
        if (availableDateIdDB.isEmpty()){
            throw new NotFoundException(doctorId+ Msg.NOT_FOUND);
        }
        else{
            List<AvailableDateResponse> availableDateResponses = new ArrayList<>();
            for (AvailableDate availableDate : availableDateIdDB) {
                availableDateResponses.add(modelMapper.forResponse().map(availableDate, AvailableDateResponse.class));
            }

            return availableDateResponses;
        }

    }



    public List<AvailableDate> getAvailableDatesByDoctorId(Long doctorId) {
        return availableDateRepo.findByDoctorId(doctorId);
    }




    //UPDATE
    public AvailableDateResponse update(Long id, AvailableDateRequest availableDateRequest){
        Optional<AvailableDate> availableDateIdDB=availableDateRepo.findById(id);
        Optional<AvailableDate> isAvailableDateExist=availableDateRepo.findByAvailableDateAndDoctorId(availableDateRequest.getAvailableDate(),availableDateRequest.getDoctor().getId());
        if (availableDateIdDB.isEmpty()){
            throw new NotFoundException(id+Msg.NOT_FOUND);
        }
        if(isAvailableDateExist.isPresent()){
            throw new ConflictException(Msg.CONFLICT);
        }

        AvailableDate updatedAvailableDate=availableDateIdDB.get();
        updatedAvailableDate.setAvailableDate(availableDateRequest.getAvailableDate());
        updatedAvailableDate.setDoctor(availableDateRequest.getDoctor());
        return modelMapper.forResponse().map(availableDateRepo.save(updatedAvailableDate),AvailableDateResponse.class);
    }

    //DELETE
    public void delete(Long id){
        Optional<AvailableDate> availableDateIdDB=availableDateRepo.findById(id);
        if (availableDateIdDB.isEmpty()){
            throw new NotFoundException(id+Msg.NOT_FOUND);
        }else{
            availableDateRepo.delete(availableDateIdDB.get());
        }
    }
}
