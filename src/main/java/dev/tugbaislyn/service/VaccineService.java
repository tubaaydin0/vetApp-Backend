package dev.tugbaislyn.service;

import dev.tugbaislyn.core.exception.ConflictException;
import dev.tugbaislyn.core.exception.NotFoundException;
import dev.tugbaislyn.core.utilies.Msg;
import dev.tugbaislyn.dao.AnimalRepo;
import dev.tugbaislyn.dao.AppointmentRepo;
import dev.tugbaislyn.dao.ReportRepo;
import dev.tugbaislyn.dao.VaccineRepo;
import dev.tugbaislyn.dto.request.VaccineRequest;
import dev.tugbaislyn.dto.response.VaccineResponse;
import dev.tugbaislyn.entity.Vaccine;
import dev.tugbaislyn.core.config.modelMapper.ModelMapperService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VaccineService {
    private final VaccineRepo vaccineRepo;
    private final ModelMapperService modelMapper;
    private final ReportRepo reportRepo;
    private  final AppointmentRepo appointmentRepo;
    private final AnimalRepo animalRepo;

    //CREATE

    //Eğer hastaya ait aynı tip aşının (adı ve kodu aynı olan aşı) aşı koruyuculuk bitiş tarihi daha gelmemiş ise
    // sisteme yeni aşı girilememelidir. Aşı kodlarından ve aşı bitiş tarihlerinden bunu kontrol edebilirsin.

    //Değerlendirme Formu 15,19
    public VaccineResponse create(VaccineRequest vaccineRequest) {
        Optional<Vaccine> isSameVaccineExist=vaccineRepo.findByNameAndCodeAndAnimalIdAndProtectionStartDateAndProtectionFinishDate(vaccineRequest.getName(),vaccineRequest.getCode(),vaccineRequest.getAnimal().getId(), vaccineRequest.getProtectionStartDate(),vaccineRequest.getProtectionFinishDate());
        if (isSameVaccineExist.isPresent()){
           throw new ConflictException(Msg.CONFLICT);
        }

        //Önce sistemde aynı isim,kod ve hayvan idli olanlar bir listede toplanır.
        List<Vaccine> existingVaccines = vaccineRepo.findByNameAndCodeAndAnimalId(vaccineRequest.getName(), vaccineRequest.getCode(), vaccineRequest.getAnimal().getId());

        if (vaccineRequest.getProtectionStartDate().isAfter(vaccineRequest.getProtectionFinishDate())){
            throw  new RuntimeException(Msg.WARNING_VACCINE2);
        }
        // Listenin elemanları gezilerek koruma bitiş tarihleri bugünden sonra mı bakılır.eğer hepsinin koruması bugünden sonraysa yeni kayıt işlemi yapılamaz. Hata mesajı verir.
        for (Vaccine existingVaccine : existingVaccines) {

            if (existingVaccine.getProtectionFinishDate().isAfter(LocalDate.now())) {
                throw new ConflictException(Msg.CONFLICT_VACCINE);//Bu aşının koruma süresi henüz bitmemiştir! Süresi bitmeyen aşı tekrar kayıt edilemez!
            }


            //Eğer sistemde kayıtlı aşıların bitiş tarihi bugünden önceyse kayıt edilmek istenen aşının başlangıç tarihine bakılır.
            //Başlangıç tarihi önceki aşıların bitiş tarihinden önceyse yine hata mesajı verecektir.
            if(vaccineRequest.getProtectionStartDate().isBefore(existingVaccine.getProtectionFinishDate())){
                throw new RuntimeException(Msg.WARNING_VACCINE);//"Bu aşının koruma başlangıç tarihi,önceki aşısının süresi bittikten sonra başlamalıdır!!"
            }
        }

            //Yukarıdaki hata mesajlarına takılmazsa kaydetme işlemi yapılır.
            Vaccine vaccine = modelMapper.forRequest().map(vaccineRequest, Vaccine.class);
            return modelMapper.forResponse().map(vaccineRepo.save(vaccine), VaccineResponse.class);


    }





    //READ
    //Hayvan id’sine göre belirli bir hayvana ait tüm aşı kayıtlarını listelemek için gerekli API end point'ini oluşturmak.
    //Değerlendirme Formu 20
    public List<VaccineResponse> getByAnimalId(Long id) {

            List<VaccineResponse> vaccineResponseList = vaccineRepo.findByAnimalId(id).stream().map(
                    vaccine -> modelMapper.forResponse().map(vaccine, VaccineResponse.class)
            ).collect(Collectors.toList());
            return vaccineResponseList;

    }

    public List<VaccineResponse> getByAnimalName(String name){

        List<VaccineResponse> vaccineResponseList = vaccineRepo.findByAnimalName(name)
                .stream()
                .map(vaccine -> modelMapper.forResponse().map(vaccine,VaccineResponse.class)
                ).collect(Collectors.toList());

        return vaccineResponseList;

    }

  /*
  Kullanıcının aşı koruyuculuk bitiş tarihi yaklaşan hayvanları listeleyebilmesi için kullanıcının gireceği
  başlangıç ve bitiş tarihlerine göre aşı koruyuculuk tarihi bu aralıkta olan hayvanların listesini geri döndüren
  API end  point'ini oluşturmak.
     */

    //Değerlendirme Formu 21
    public List<VaccineResponse> getByAnimalsByWillExpire(LocalDate protectionStartDate, LocalDate protectionEndDate){
        //Bitiş tarihi girilen baş ve bit tarihi arasında olanlar bir listede toplanır.
        List<Vaccine> vaccineWillExpireList = vaccineRepo.findByProtectionFinishDateBetween(protectionStartDate, protectionEndDate);

        //Liste response formatına getirilir
        List<VaccineResponse> vaccineResponseList = vaccineWillExpireList.stream()
                .map(vaccine -> modelMapper.forResponse().map(vaccine, VaccineResponse.class))
                .collect(Collectors.toList());
        return vaccineResponseList;
    }


    public VaccineResponse getById(Long id) {
        Optional<Vaccine> vaccineIdDB = vaccineRepo.findById(id);
        if (vaccineIdDB.isEmpty()) {
            throw new NotFoundException(id+Msg.NOT_FOUND);
        } else {
            return modelMapper.forResponse().map(vaccineIdDB.get(), VaccineResponse.class);
        }
    }


    public List<VaccineResponse> getAll() {
        List<VaccineResponse> vaccineResponseList = vaccineRepo.findAll()
                .stream()
                .map(vaccine -> modelMapper.forResponse().map(vaccine, VaccineResponse.class)
                ).collect(Collectors.toList());
        return vaccineResponseList;
    }

    //UPDATE
    public VaccineResponse update(Long id, VaccineRequest vaccineRequest) {

        Optional<Vaccine> vaccineIdDB=vaccineRepo.findById(id);
        Optional<Vaccine> isSameVaccineExist=vaccineRepo.findByNameAndCodeAndAnimalIdAndProtectionStartDateAndProtectionFinishDateAndReportId(vaccineRequest.getName(),vaccineRequest.getCode(),vaccineRequest.getAnimal().getId(), vaccineRequest.getProtectionStartDate(),vaccineRequest.getProtectionFinishDate(), vaccineRequest.getReport().getId());

        if(vaccineIdDB.isEmpty()){
            throw new NotFoundException(id+Msg.NOT_FOUND);
        }

        if (isSameVaccineExist.isPresent()){
            throw new ConflictException(Msg.CONFLICT);
        }

        if (vaccineRequest.getProtectionStartDate().isAfter(vaccineRequest.getProtectionFinishDate())){
            throw  new RuntimeException(Msg.WARNING_VACCINE2);
        }

        //Güncellenecek verinin isim, kod ve animal id aynı olan veriler bir listede toplanır.
        List<Vaccine> existingVaccines = vaccineRepo.findByNameAndCodeAndAnimalId(vaccineRequest.getName(), vaccineRequest.getCode(), vaccineRequest.getAnimal().getId()); // sistemde girilen idli kayıt var mı


        Vaccine updatedVaccine=vaccineIdDB.get();
        if(!updatedVaccine.getProtectionStartDate().equals(vaccineRequest.getProtectionStartDate()) || !updatedVaccine.getProtectionFinishDate().equals(vaccineRequest.getProtectionFinishDate())) {
            for (Vaccine existingVaccine : existingVaccines) {
                if (!existingVaccine.getId().equals(id)) {
                    if ((vaccineRequest.getProtectionStartDate().isBefore(existingVaccine.getProtectionFinishDate()))) {
                        //Eğer girilen bitiş tarihi kayıtlı olan verilerin arasındaysa
                        throw new RuntimeException(Msg.WARNING_VACCINE);//"Bu aşının koruma başlangıç tarihi,önceki aşısının süresi bittikten sonra başlamalıdır!!"

                    }
                }

            }
        }



        updatedVaccine.setName(vaccineRequest.getName());
        updatedVaccine.setCode(vaccineRequest.getCode());
        updatedVaccine.setProtectionStartDate(vaccineRequest.getProtectionStartDate());
        updatedVaccine.setProtectionFinishDate(vaccineRequest.getProtectionFinishDate());
        updatedVaccine.setAnimal(vaccineRequest.getAnimal());
        updatedVaccine.setReport(vaccineRequest.getReport());
        return modelMapper.forResponse().map(vaccineRepo.save(updatedVaccine),VaccineResponse.class);


    }



    //DELETE
    public  void  delete(Long id){
        Optional<Vaccine> vaccineIdDB=vaccineRepo.findById(id);
        if (vaccineIdDB.isPresent()){
            vaccineRepo.delete(vaccineIdDB.get());
        }else{
            throw new NotFoundException(id+Msg.NOT_FOUND);
        }
    }

  /*  public void updateReportId(Long vaccineId, Long reportId) {
        Optional<Vaccine> optionalVaccine = vaccineRepo.findById(vaccineId);
        if (optionalVaccine.isPresent()) {
            Vaccine vaccine = optionalVaccine.get();
            vaccine.getReport().setId(reportId);
            vaccineRepo.save(vaccine);
        } else {
            throw new NotFoundException("Vaccine not found with id: " + vaccineId);
        }
    }

   */
}
