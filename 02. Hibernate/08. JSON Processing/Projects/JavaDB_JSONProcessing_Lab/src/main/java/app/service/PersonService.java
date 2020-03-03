package app.service;

import app.domain.model.PhoneNumber;
import app.repository.PhoneNumberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {
    @Autowired
    private PhoneNumberRepository phoneNumberRepository;

    public List<PhoneNumber> getPhoneNumbers() {
        return this.phoneNumberRepository.findAll();
    }
}
