package softuni.workshop.service;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.workshop.domain.dtos.exportDtos.jsonExport.CompanyJsonExportDto;
import softuni.workshop.domain.dtos.importDtos.companyDtos.CompanyDto;
import softuni.workshop.domain.dtos.importDtos.companyDtos.CompanyRootDto;
import softuni.workshop.domain.entities.Company;
import softuni.workshop.repository.CompanyRepository;
import softuni.workshop.util.FileUtil;
import softuni.workshop.util.ValidatorUtil;
import softuni.workshop.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {
    private static final String EXPORT_COMPANIES_XML_PATH = "src\\main\\resources\\files\\xmls\\companies.xml";
    private static final String EXPORT_COMPANIES_JSON_PATH = "src\\main\\resources\\files\\json\\companies.json";

    private final CompanyRepository companyRepository;
    private final ModelMapper mapper;
    private final XmlParser xmlParser;
    private final Gson gson;
    private final ValidatorUtil validatorUtil;
    private final FileUtil fileUtil;

    @Autowired
    public CompanyServiceImpl(CompanyRepository companyRepository, ModelMapper mapper, XmlParser xmlParser, Gson gson, ValidatorUtil validatorUtil, FileUtil fileUtil) {
        this.companyRepository = companyRepository;
        this.mapper = mapper;
        this.xmlParser = xmlParser;
        this.gson = gson;
        this.validatorUtil = validatorUtil;
        this.fileUtil = fileUtil;
    }

    @Override
    public void importCompanies() throws JAXBException {
        CompanyRootDto companyRootDto = this.xmlParser.fromXml(CompanyRootDto.class, EXPORT_COMPANIES_XML_PATH);

        for (CompanyDto companyDto : companyRootDto.getCompanies()) {
            Company company = this.mapper.map(companyDto, Company.class);

            if (!this.validatorUtil.isValid(company)){
                System.out.println(this.validatorUtil.validationErrors(company));
                continue;
            }

            this.companyRepository.saveAndFlush(company);
        }
    }

    @Override
    public boolean areImported() {
        return this.companyRepository.count() > 0;
    }

    @Override
    public String readCompaniesXmlFile() throws IOException {
        return this.fileUtil.readFile(EXPORT_COMPANIES_XML_PATH);
    }

    @Override
    public void exportJsonCompanies() throws IOException {
        if (this.fileUtil.isFileEmpty(EXPORT_COMPANIES_JSON_PATH)){
            return;
        }

        List<Company> companies = this.companyRepository.findAll();
        List<CompanyJsonExportDto> companiesExport = new ArrayList<>();

        for (Company c : companies) {
            companiesExport.add(this.mapper.map(c, CompanyJsonExportDto.class));
        }
        FileWriter fileWriter = new FileWriter(EXPORT_COMPANIES_JSON_PATH);
        this.gson.toJson(companiesExport, fileWriter);

        fileWriter.close();
    }
}
