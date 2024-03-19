package dev.tugbaislyn.core.config.modelMapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ModelMapperService {
    private final  ModelMapper modelMapper;


    public ModelMapper forRequest() {
        //Requestler dışardan girileceği için esnek değil standard olarak ayarla.Daha kontrollü matchleme işlemi yapılsın.
        modelMapper.getConfiguration().setAmbiguityIgnored(true).setMatchingStrategy(MatchingStrategies.STANDARD);
        return modelMapper;
    }

    public ModelMapper forResponse() {
        //Responseler bizim tarafımızdan yani veritabanından geleceği için hata oranı daha düşük olacağından daha esnek olunabilir.
        modelMapper.getConfiguration().setAmbiguityIgnored(true).setMatchingStrategy(MatchingStrategies.LOOSE);
        return modelMapper;
    }
}

