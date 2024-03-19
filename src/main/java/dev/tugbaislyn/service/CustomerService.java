package dev.tugbaislyn.service;
import dev.tugbaislyn.core.exception.ConflictException;
import dev.tugbaislyn.core.exception.NotFoundException;
import dev.tugbaislyn.core.utilies.Msg;
import dev.tugbaislyn.dao.CustomerRepo;
import dev.tugbaislyn.dto.request.CustomerRequest;
import dev.tugbaislyn.dto.response.CustomerResponse;
import dev.tugbaislyn.entity.Customer;
import dev.tugbaislyn.core.config.modelMapper.ModelMapperService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepo customerRepo;
    private final ModelMapperService modelMapper;


    //CREATE
    //Değerlendirme Formu 10
    public CustomerResponse create(CustomerRequest customerRequest){
        List<Customer> customerMailDB=customerRepo.findByMailOrPhone(customerRequest.getMail(),customerRequest.getPhone());
        if (customerMailDB.size()>0){
            throw new ConflictException(Msg.CONFLICT);//"Aynı maile veya telefona sahip kişi tekrar kaydedilemez!"
        }else{
            Customer customer=modelMapper.forRequest().map(customerRequest,Customer.class); // Girilen veri customer nesnesine çevrildi.
            return modelMapper.forResponse().map(customerRepo.save(customer),CustomerResponse.class); // Girilen veriler veritabanına gönderildi. Responseye çevrildi.
        }
    }
    //READ
    public CustomerResponse getById(Long id){
        Optional<Customer> customerIdDB=customerRepo.findById(id);
        if (customerIdDB.isEmpty()){
            throw new NotFoundException(id+Msg.NOT_FOUND);
        }else{
            return modelMapper.forResponse().map(customerIdDB.get(),CustomerResponse.class);
        }
    }

    public List<CustomerResponse> getAll(){
        List<CustomerResponse> customerResponseList=customerRepo.findAll()
                .stream()
                .map(
                        customer -> modelMapper.forResponse().map(customer,CustomerResponse.class)
        ).collect(Collectors.toList());
        return customerResponseList;
    }

    //Değerlendirme Formu 17
    public List<CustomerResponse> getAllOrName(String name) {
        if (name == null || name.isEmpty()) {
            return this.getAll();
        } else {
            List<CustomerResponse> customerResponseList = customerRepo.findByName(name).stream().map(
                    customer -> modelMapper.forResponse().map(customer, CustomerResponse.class)
            ).collect(Collectors.toList());
            return customerResponseList;
        }
    }
    //UPDATE
    public CustomerResponse update(Long id,CustomerRequest customerRequest){
        Optional<Customer> customerIdDB=customerRepo.findById(id);
        List<Customer> customerSameList=customerRepo.findByMailOrPhone(customerRequest.getMail(),customerRequest.getPhone());

        if (customerIdDB.isEmpty()){
            throw new NotFoundException(id+Msg.NOT_FOUND);
        }

        Customer updatedCustomer=customerIdDB.get(); //değişiklik yapılcak id nin bilgileri updatedCustomer nesnesine aktarıldı.
        //Eğer id de bulunan telefon ve mail bilgileri değiştirildiyse
        if (!updatedCustomer.getMail().equals(customerRequest.getMail()) || !updatedCustomer.getPhone().equals(customerRequest.getPhone())) {
            for (Customer customer : customerSameList) {
                if (!customer.getId().equals(id)) {//Aynı mail veya aynı telefon numarasına eriştiğinde kendi idsi değilse başka bir id de aynı veri kayıtlı demektir.
                    throw new ConflictException(Msg.CONFLICT); //Aynı mail veya telefon tekrar kaydedilemez!
                }
            }
        }

        //girilen id var ve güncelleme sırasında telefon ve mail bilgileri değiştirilmediyse direkt güncelleme işlemini başlatır.
        updatedCustomer.setName(customerRequest.getName());
        updatedCustomer.setMail(customerRequest.getMail());
        updatedCustomer.setCity(customerRequest.getCity());
        updatedCustomer.setAddress(customerRequest.getAddress());
        updatedCustomer.setPhone(customerRequest.getPhone());
        return modelMapper.forResponse().map(customerRepo.save(updatedCustomer),CustomerResponse.class);
    }

    //DELETE
    public void delete(Long id){
        Optional<Customer> customerIdDB=customerRepo.findById(id);
        if (customerIdDB.isEmpty()){
            throw new NotFoundException(id+Msg.NOT_FOUND);
        }else{
            customerRepo.delete(customerIdDB.get());
        }
    }

}
