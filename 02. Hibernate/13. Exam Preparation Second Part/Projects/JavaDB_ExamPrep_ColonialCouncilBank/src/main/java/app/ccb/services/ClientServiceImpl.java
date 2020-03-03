package app.ccb.services;

import app.ccb.domain.dtos.clientImport.ClientDto;
import app.ccb.domain.entities.Card;
import app.ccb.domain.entities.Client;
import app.ccb.domain.entities.Employee;
import app.ccb.repositories.BranchRepository;
import app.ccb.repositories.ClientRepository;
import app.ccb.repositories.EmployeeRepository;
import app.ccb.util.FileUtil;
import app.ccb.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

import static app.ccb.common.Constants.ERROR_MESSAGE;
import static app.ccb.common.Constants.SUCCESS_MESSAGE;

@Service
public class ClientServiceImpl implements ClientService {
    private static final String CLIENT_JSON_PATH = System.getProperty("user.dir") + "\\src\\main\\resources\\files\\json\\clients.json";

    private ClientRepository clientRepository;
    private EmployeeRepository employeeRepository;
    private FileUtil fileUtil;
    private Gson gson;
    private ModelMapper modelMapper;
    private ValidationUtil validationUtil;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository, EmployeeRepository employeeRepository,
                             FileUtil fileUtil, Gson gson, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.clientRepository = clientRepository;
        this.employeeRepository = employeeRepository;
        this.fileUtil = fileUtil;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public Boolean clientsAreImported() {
        return clientRepository.count() > 0;
    }

    @Override
    public String readClientsJsonFile() throws IOException {
        return fileUtil.readFile(CLIENT_JSON_PATH);
    }

    @Override
    public String importClients(String clients) {
        StringBuilder output = new StringBuilder();
        ClientDto[] clientDtos = gson.fromJson(clients, ClientDto[].class);
        for (ClientDto clientDto : clientDtos) {
            Client client = modelMapper.map(clientDto, Client.class);

            String employeeFirstName = clientDto.getAppointedEmployee().substring(0, clientDto.getAppointedEmployee().indexOf(" "));
            String employeeLastName = clientDto.getAppointedEmployee().substring(clientDto.getAppointedEmployee().indexOf(" ") + 1);
            Employee employee = employeeRepository.findByFirstNameAndLastName(employeeFirstName, employeeLastName);

            if (employee == null) {
                output.append(ERROR_MESSAGE).append(System.lineSeparator());
                continue;
            }

            if (!validationUtil.isValid(client)) {
                output.append(ERROR_MESSAGE).append(System.lineSeparator());
                continue;
            }

            Client clientDb = clientRepository.findByFullName(client.getFullName());
            if (clientDb == null) {
                clientRepository.saveAndFlush(client);
            } else {
                client = clientDb;
            }

            employee.getClients().add(client);
            employeeRepository.saveAndFlush(employee);
            output.append(String.format(SUCCESS_MESSAGE, client.getClass().getSimpleName(), client.getFullName()))
                    .append(System.lineSeparator());
        }

        return output.toString().trim();
    }

    @Override
    public String exportFamilyGuy() {
        Client client = clientRepository.findAllClients().get(0);
        StringBuilder output = new StringBuilder();
        output.append("Full Name: ").append(client.getFullName()).append(System.lineSeparator())
                .append("Age: ").append(client.getAge()).append(System.lineSeparator())
                .append("Bank Account: ").append(client.getBankAccount().getAccountNumber()).append(System.lineSeparator());
        for (Card card : client.getCards()) {
            output.append("  Card Number: ").append(card.getCardNumber()).append(System.lineSeparator())
                    .append("  Card Status: ").append(card.getStatus()).append(System.lineSeparator());
        }

        return output.toString().trim();
    }
}
