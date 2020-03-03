package app.ccb.services;

import app.ccb.domain.dtos.bankAccountImport.BankAccountDto;
import app.ccb.domain.dtos.bankAccountImport.BankAccountWrapperDto;
import app.ccb.domain.entities.BankAccount;
import app.ccb.domain.entities.Client;
import app.ccb.repositories.BankAccountRepository;
import app.ccb.repositories.ClientRepository;
import app.ccb.util.FileUtil;
import app.ccb.util.ValidationUtil;
import app.ccb.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;

import static app.ccb.common.Constants.ERROR_MESSAGE;
import static app.ccb.common.Constants.SUCCESS_MESSAGE;

@Service
public class BankAccountServiceImpl implements BankAccountService {
    private static final String BANK_ACCOUNT_XML_PATH = System.getProperty("user.dir") + "\\src\\main\\resources\\files\\xml\\bank-accounts.xml";

    private BankAccountRepository bankAccountRepository;
    private ClientRepository clientRepository;
    private FileUtil fileUtil;
    private XmlParser xmlParser;
    private ModelMapper modelMapper;
    private ValidationUtil validationUtil;

    @Autowired
    public BankAccountServiceImpl(BankAccountRepository bankAccountRepository, ClientRepository clientRepository, FileUtil fileUtil,
                                  XmlParser xmlParser, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.bankAccountRepository = bankAccountRepository;
        this.clientRepository = clientRepository;
        this.fileUtil = fileUtil;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public Boolean bankAccountsAreImported() {
        return bankAccountRepository.count() > 0;
    }

    @Override
    public String readBankAccountsXmlFile() throws IOException {
        return fileUtil.readFile(BANK_ACCOUNT_XML_PATH);
    }

    @Override
    public String importBankAccounts() throws JAXBException {
        StringBuilder output = new StringBuilder();
        BankAccountWrapperDto bankAccountWrapperDto = xmlParser.fromXml(BankAccountWrapperDto.class, BANK_ACCOUNT_XML_PATH);
        for (BankAccountDto bankAccountDto : bankAccountWrapperDto.getBankAccounts()) {
            BankAccount bankAccount = modelMapper.map(bankAccountDto, BankAccount.class);

            Client client = clientRepository.findByFullName(bankAccountDto.getClientName());
            if (client == null) {
                output.append(ERROR_MESSAGE).append(System.lineSeparator());
                continue;
            }

            if (!validationUtil.isValid(bankAccount)) {
                output.append(ERROR_MESSAGE).append(System.lineSeparator());
                continue;
            }

            bankAccount.setClient(client);
            bankAccountRepository.saveAndFlush(bankAccount);
            client.setBankAccount(bankAccount);
            clientRepository.saveAndFlush(client);
            output.append(String.format(SUCCESS_MESSAGE,
                    bankAccount.getClass().getSimpleName(), bankAccount.getAccountNumber()))
                    .append(System.lineSeparator());
        }

        return output.toString().trim();
    }
}
