package dev.tugbaislyn.service;

import dev.tugbaislyn.core.exception.ConflictException;
import dev.tugbaislyn.core.exception.NotFoundException;
import dev.tugbaislyn.core.utilies.Msg;
import dev.tugbaislyn.dao.DoctorRepo;
import dev.tugbaislyn.dto.request.DoctorRequest;
import dev.tugbaislyn.dto.response.DoctorResponse;
import dev.tugbaislyn.entity.Doctor;
import dev.tugbaislyn.core.config.modelMapper.ModelMapperService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRepo doctorRepo;
    private  final ModelMapperService modelMapper;

    //CREATE
    //Değerlendirme Formu 12
    public DoctorResponse create(DoctorRequest doctorRequest){
        List<Doctor> isDoctorExist= doctorRepo.findByMailOrPhone(doctorRequest.getMail(),doctorRequest.getPhone());
        if (isDoctorExist.size()>0){
            throw new ConflictException(Msg.CONFLICT);//Aynı maile veya aynı telefona sahip kişi tekrar kaydedilemez!
        }else{
            Doctor doctor=modelMapper.forRequest().map(doctorRequest,Doctor.class);
            return modelMapper.forResponse().map(doctorRepo.save(doctor),DoctorResponse.class);
        }
    }

    //READ
    public DoctorResponse getById(Long id){
        Optional<Doctor> doctorIdDB=doctorRepo.findById(id);
        if (doctorIdDB.isPresent()){
            return modelMapper.forResponse().map(doctorIdDB.get(),DoctorResponse.class);
        }else{
            throw new NotFoundException(id+Msg.NOT_FOUND);
        }
    }

    public List<DoctorResponse> getAll(){
        List<DoctorResponse> doctorResponseList=doctorRepo.findAll()
                .stream()
                .map(
                        doctor -> modelMapper.forResponse().map(doctor,DoctorResponse.class)
        ).collect(Collectors.toList());
        return doctorResponseList;
    }

    //UPDATE
    public DoctorResponse update(Long id, DoctorRequest doctorRequest){
        Optional<Doctor> doctorIdDB=doctorRepo.findById(id);
        if (doctorIdDB.isEmpty()){
            throw new NotFoundException(id+Msg.NOT_FOUND);
        }

        List<Doctor> doctorSameList=doctorRepo.findByMailOrPhone(doctorRequest.getMail(),doctorRequest.getPhone());
        Doctor updatedDoctor=doctorIdDB.get(); // iddeki veriler aktarıldı
        //Güncelleme öncesi sistemdeki mail veya telefon bilgisi ile güncelleme esnasındaki girilen telefon veya mail bilgileri aynı değilse
        //Sistemde güncellenecek veri ile  aynı mail ve telefon kayıtlı mı kontrol eder
        if(!updatedDoctor.getMail().equals(doctorRequest.getMail()) || !updatedDoctor.getPhone().equals(doctorRequest.getPhone())){
            for (Doctor doctor : doctorSameList) {
                if (!doctor.getId().equals(id)) {//Aynı mail veya aynı telefon numarasına eriştiğinde kendi idsi değilse başka bir id de aynı veri kayıtlı demektir.
                    throw new ConflictException(Msg.CONFLICT);//Aynı mail veya telefon tekrar kaydedilemez!
                }
            }

        }

        updatedDoctor.setName(doctorRequest.getName());
        updatedDoctor.setMail(doctorRequest.getMail());
        updatedDoctor.setCity(doctorRequest.getCity());
        updatedDoctor.setAddress(doctorRequest.getAddress());
        updatedDoctor.setPhone(doctorRequest.getPhone());
        return  modelMapper.forResponse().map(doctorRepo.save(updatedDoctor),DoctorResponse.class);


    }


    //DELETE
    public void delete(Long id){
        Optional<Doctor> doctorIdDB=doctorRepo.findById(id);
        if (doctorIdDB.isEmpty()){
            throw new NotFoundException(id+Msg.NOT_FOUND);
        }else{
            doctorRepo.delete(doctorIdDB.get());
        }

    }

}
