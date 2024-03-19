package dev.tugbaislyn.service;

import dev.tugbaislyn.core.config.modelMapper.ModelMapperService;

import dev.tugbaislyn.core.exception.ConflictException;
import dev.tugbaislyn.core.exception.NotFoundException;
import dev.tugbaislyn.core.utilies.Msg;
import dev.tugbaislyn.dao.AppointmentRepo;
import dev.tugbaislyn.dao.ReportRepo;
import dev.tugbaislyn.dto.request.ReportRequest;
import dev.tugbaislyn.dto.response.*;
import dev.tugbaislyn.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final ReportRepo reportRepo;
    private final ModelMapperService modelMapper;
    private final AppointmentService appointmentService;
    private final AppointmentRepo appointmentRepo;

    //READ
   public List<ReportResponse> getAll(){
        List<ReportResponse> reportResponseList=reportRepo.findAll()
                .stream()
                .map(
                        report -> modelMapper.forResponse().map(report,ReportResponse.class)
                ).collect(Collectors.toList());
        return reportResponseList;
    }

    public List<ReportResponse> getReportsByAnimalId(Long animalId) {
        List<Report> reports = new ArrayList<>();

        // İlgili hayvana ait randevuları bul
        List<Appointment> appointments = appointmentRepo.findByAnimalId(animalId);

        // Her randevunun raporunu bul ve listeye ekle
        for (Appointment appointment : appointments) {
            Optional<Report> report = reportRepo.findByAppointmentId(appointment.getId());
            report.ifPresent(reports::add);
        }

        // Raportları ReportResponse sınıfına dönüştürerek döndür
        return reports.stream()
                .map(report -> modelMapper.forResponse().map(report, ReportResponse.class))
                .collect(Collectors.toList());
    }


    //CREATE
    public ReportResponse create(ReportRequest reportRequest) {
        // Aynı değerlere sahip bir rapor var mı kontrol et
        Optional<Report> isReportExist = reportRepo.findByAppointmentId(reportRequest.getAppointment().getId());
        if (isReportExist.isEmpty()) {
            Report saveReport = modelMapper.forRequest().map(reportRequest, Report.class); // Girilen veriler report nesnesine çevrildi.
            Report savedReport = reportRepo.save(saveReport); // Çevrilen report nesnesi veritabanına gönderildi

            return modelMapper.forResponse().map(savedReport, ReportResponse.class); // Çıktı için responseye çevrildi.
        } else {
            throw new ConflictException(Msg.CONFLICT); // Aynı veri kaydedilemez
        }
    }

    //UPDATE
    public ReportResponse update(Long id, ReportRequest reportRequest){
        Optional<Report> reportIdDB=reportRepo.findById(id);//Sistemde rapor idsi olup olmadığına bakılır.
        Optional<Report> isExistReport=reportRepo.findByAppointmentIdAndDiagnosisAndTitleAndPrice(reportRequest.getAppointment().getId(), reportRequest.getDiagnosis(),reportRequest.getTitle(),reportRequest.getPrice());
        if (reportIdDB.isEmpty()){
            throw new NotFoundException(id+Msg.NOT_FOUND);
        }

        try {
            AppointmentResponse appointmentIdDB = appointmentService.getById(reportRequest.getAppointment().getId());

        }catch(Exception e){
            throw new NotFoundException("Girilen randevu için "+reportRequest.getAppointment().getId()+ Msg.NOT_FOUND);
        }

        if(isExistReport.isPresent()) {
            throw new ConflictException(Msg.CONFLICT);
        }

        Report updatedReport = reportIdDB.get();
        updatedReport.setTitle(reportRequest.getTitle());
        updatedReport.setDiagnosis(reportRequest.getDiagnosis());
        updatedReport.setAppointment(reportRequest.getAppointment());
        updatedReport.setPrice(reportRequest.getPrice());
        return modelMapper.forResponse().map(reportRepo.save(updatedReport), ReportResponse.class);


    }

    // DELETE

    public  void delete(Long id){
        Optional<Report> reportIdDB=reportRepo.findById(id);
        if (reportIdDB.isEmpty()){
            throw new NotFoundException(id+Msg.NOT_FOUND);
        }else{
            reportRepo.delete(reportIdDB.get());
        }
    }

}
