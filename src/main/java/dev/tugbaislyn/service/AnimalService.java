package dev.tugbaislyn.service;
import dev.tugbaislyn.core.exception.ConflictException;
import dev.tugbaislyn.core.exception.NotFoundException;
import dev.tugbaislyn.core.utilies.Msg;
import dev.tugbaislyn.dao.AnimalRepo;
import dev.tugbaislyn.dto.request.AnimalRequest;
import dev.tugbaislyn.dto.response.AnimalResponse;
import dev.tugbaislyn.entity.Animal;
import lombok.RequiredArgsConstructor;
import dev.tugbaislyn.core.config.modelMapper.ModelMapperService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class AnimalService {
    private final AnimalRepo animalRepo;
    private  final ModelMapperService modelMapper;


    //READ

    public AnimalResponse getById(Long id){
        Optional<Animal> animalIdDB=animalRepo.findById(id);
        if (animalIdDB.isEmpty()){
            throw new NotFoundException(id+Msg.NOT_FOUND);
        }else {
            return modelMapper.forResponse().map(animalIdDB.get(),AnimalResponse.class);
        }

    }
    //Değerlendirme Formu 16
    public List<AnimalResponse> getByAnimalNameorAll(String name){
        if (name==null || name.isEmpty()){
           return this.getAll();

        }else{
           List<AnimalResponse> animalResponseList = animalRepo.findByName(name)
                   .stream()
                   .map(animal -> modelMapper.forResponse().map(animal, AnimalResponse.class)
            ).collect(Collectors.toList());

            return animalResponseList;
        }
    }


    public List<AnimalResponse> getByCustomerName(String name){

        List<AnimalResponse> animalResponseList = animalRepo.findByCustomerName(name)
                .stream()
                .map(animal -> modelMapper.forResponse().map(animal, AnimalResponse.class)
                ).collect(Collectors.toList());

        return animalResponseList;

    }
    public List<AnimalResponse> getAll(){

        List<AnimalResponse> animalResponseList = animalRepo.findAll()
                .stream().map(
                animal -> modelMapper.forResponse().map(animal, AnimalResponse.class)
        ).collect(Collectors.toList());
        return animalResponseList;
    }

    //Değerlendirme Formu 18
    public  List<AnimalResponse> getByCustomerId(Long id){
        List<AnimalResponse> animalResponseList = animalRepo.findByCustomerId(id)
                .stream().map(
                        animal -> modelMapper.forResponse().map(animal, AnimalResponse.class)
                ).collect(Collectors.toList());
        return animalResponseList;
    }

    //CREATE
    //Değerlendirme Formu 11
    public AnimalResponse create(AnimalRequest animalRequest){
        Optional<Animal> isAnimalExist=animalRepo.findByNameAndSpeciesAndBreedAndGenderAndColourAndDateOfBirthAndCustomerId(animalRequest.getName(),animalRequest.getSpecies(),animalRequest.getBreed(),animalRequest.getGender(),animalRequest.getColour(), animalRequest.getDateOfBirth(),animalRequest.getCustomer().getId());
        if (isAnimalExist.isEmpty()){
            Animal saveAnimal=modelMapper.forRequest().map(animalRequest,Animal.class); // Girilen veriler animal nesnesine çevrildi.
            return modelMapper.forResponse().map(animalRepo.save(saveAnimal),AnimalResponse.class);// Çevrilen animal nesnesi veritabanına gönderildi, çıktı için responseye çevrildi.
        }else{
            throw new ConflictException(Msg.CONFLICT); //Aynı veri kaydedilemez
        }

    }

    //UPDATE
    public AnimalResponse update(Long id, AnimalRequest animalRequest){
        Optional<Animal> animalIdDB=animalRepo.findById(id);
        if (animalIdDB.isEmpty()){
            throw new NotFoundException(id+Msg.NOT_FOUND);
        }else{
            Optional<Animal> isAnimalExist=animalRepo.findByNameAndSpeciesAndBreedAndGenderAndColourAndDateOfBirthAndCustomerId(animalRequest.getName(),animalRequest.getSpecies(),animalRequest.getBreed(),animalRequest.getGender(),animalRequest.getColour(), animalRequest.getDateOfBirth(),animalRequest.getCustomer().getId());
            if(isAnimalExist.isPresent()){
                throw new ConflictException(Msg.CONFLICT);
            }
            Animal updatedAnimal=animalIdDB.get();
            updatedAnimal.setName(animalRequest.getName());//Veritabanındaki bilgiler dışardan gelen bilgilerle değiştirildi
            updatedAnimal.setSpecies(animalRequest.getSpecies());
            updatedAnimal.setBreed(animalRequest.getBreed());
            updatedAnimal.setGender(animalRequest.getGender());
            updatedAnimal.setColour(animalRequest.getColour());
            updatedAnimal.setDateOfBirth(animalRequest.getDateOfBirth());
            updatedAnimal.setCustomer(animalRequest.getCustomer());
            return modelMapper.forResponse().map(animalRepo.save(updatedAnimal),AnimalResponse.class);

        }
    }
    //DELETE

    public void delete(Long id){
        Optional<Animal> animalIdDB=animalRepo.findById(id);
        if (animalIdDB.isEmpty()){
            throw new NotFoundException(id+Msg.NOT_FOUND);
        }else{
            animalRepo.delete(animalIdDB.get());
        }
        //animalRepo.delete(animal);
    }
}
