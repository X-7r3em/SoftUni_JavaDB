package app.terminal;

import app.domain.dto.PhoneNumberDto;
import app.domain.model.PhoneNumber;
import app.repository.PhoneNumberRepository;
import app.service.PersonService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.FileReader;

@Component
public class Terminal implements CommandLineRunner {

    @Autowired
    private PhoneNumberRepository phoneNumberRepository;

    @Override
    public void run(String... strings) throws Exception {

        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting()
                .create();

        PhoneNumberDto phoneNumberDto = gson.fromJson(new FileReader("D:\\SoftUni\\04. Java DB\\Hibernate\\08. JSON Processing\\Projects\\LabJSONProcessing\\src\\main\\resources\\files\\jsonResource.txt"), PhoneNumberDto.class);

        ModelMapper mapper = new ModelMapper();

        PhoneNumber phoneNumber = mapper.map(phoneNumberDto, PhoneNumber.class);

        this.phoneNumberRepository.saveAndFlush(phoneNumber);

        PhoneNumber fromDb = this.phoneNumberRepository.findAll().get(0);

        PhoneNumberDto fromDbDto = mapper.map(fromDb, PhoneNumberDto.class);

        System.out.println(gson.toJson(fromDbDto));
    }
}
