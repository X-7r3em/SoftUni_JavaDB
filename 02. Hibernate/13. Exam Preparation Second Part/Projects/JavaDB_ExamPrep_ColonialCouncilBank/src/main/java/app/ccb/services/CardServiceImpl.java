package app.ccb.services;

import app.ccb.domain.dtos.cardImport.CardDto;
import app.ccb.domain.dtos.cardImport.CardWrapperDto;
import app.ccb.domain.entities.BankAccount;
import app.ccb.domain.entities.Card;
import app.ccb.repositories.BankAccountRepository;
import app.ccb.repositories.CardRepository;
import app.ccb.repositories.ClientRepository;
import app.ccb.repositories.EmployeeRepository;
import app.ccb.util.FileUtil;
import app.ccb.util.ValidationUtil;
import app.ccb.util.XmlParser;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;

import static app.ccb.common.Constants.ERROR_MESSAGE;
import static app.ccb.common.Constants.SUCCESS_MESSAGE;

@Service
public class CardServiceImpl implements CardService {

    private static final String CARD_XML_PATH = System.getProperty("user.dir") + "\\src\\main\\resources\\files\\xml\\cards.xml";

    private CardRepository cardRepository;
    private BankAccountRepository bankAccountRepository;
    private FileUtil fileUtil;
    private XmlParser xmlParser;
    private ModelMapper modelMapper;
    private ValidationUtil validationUtil;

    @Autowired
    public CardServiceImpl(CardRepository cardRepository, BankAccountRepository
            bankAccountRepository, FileUtil fileUtil, XmlParser xmlParser, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.cardRepository = cardRepository;
        this.bankAccountRepository = bankAccountRepository;
        this.fileUtil = fileUtil;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public Boolean cardsAreImported() {
        return cardRepository.count() > 0;
    }

    @Override
    public String readCardsXmlFile() throws IOException {
        return fileUtil.readFile(CARD_XML_PATH);
    }

    @Override
    public String importCards() throws JAXBException {
        StringBuilder output = new StringBuilder();
        CardWrapperDto cardWrapperDto = xmlParser.fromXml(CardWrapperDto.class, CARD_XML_PATH);
        for (CardDto cardDto : cardWrapperDto.getCards()) {
            Card card = modelMapper.map(cardDto, Card.class);
            BankAccount bankAccount = bankAccountRepository.findByAccountNumber(cardDto.getAccountNumber());
            card.setBankAccount(bankAccount);
            if (bankAccount == null || !validationUtil.isValid(card)) {
                output.append(ERROR_MESSAGE).append(System.lineSeparator());
                continue;
            }

            card.setClient(bankAccount.getClient());

            cardRepository.saveAndFlush(card);
            output.append(String.format(SUCCESS_MESSAGE, card.getClass().getSimpleName(), card.getCardNumber()))
                    .append(System.lineSeparator());
        }

        return output.toString().trim();
    }
}
