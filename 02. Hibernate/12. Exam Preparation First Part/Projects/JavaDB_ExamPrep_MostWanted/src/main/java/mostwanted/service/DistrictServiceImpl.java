package mostwanted.service;

import com.google.gson.Gson;
import mostwanted.domain.dtos.DistrictImportDto;
import mostwanted.domain.entities.District;
import mostwanted.repository.DistrictRepository;
import mostwanted.repository.TownRepository;
import mostwanted.util.FileUtil;
import mostwanted.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static mostwanted.common.Constants.*;

@Service
public class DistrictServiceImpl implements DistrictService {

    private final static String DISTRICT_JSON_FILE_PATH = System.getProperty("user.dir") + "/src/main/resources/files/districts.json";

    private final DistrictRepository districtRepository;
    private final TownRepository townRepository;
    private final FileUtil fileUtil;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    @Autowired
    public DistrictServiceImpl(DistrictRepository districtRepository, TownRepository townRepository,
                               FileUtil fileUtil, Gson gson, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.districtRepository = districtRepository;
        this.townRepository = townRepository;
        this.fileUtil = fileUtil;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public Boolean districtsAreImported() {
        return this.districtRepository.count() > 0;
    }

    @Override
    public String readDistrictsJsonFile() throws IOException {
        return this.fileUtil.readFile(DISTRICT_JSON_FILE_PATH);
    }

    @Override
    public String importDistricts(String districtsFileContent) {
        StringBuilder output = new StringBuilder();
        DistrictImportDto[] districtImportDtos = this.gson.fromJson(districtsFileContent, DistrictImportDto[].class);
        for (DistrictImportDto districtImportDto : districtImportDtos) {
            District district = this.modelMapper.map(districtImportDto, District.class);
            if (!this.validationUtil.isValid(district)) {
                output.append(INCORRECT_DATA_MESSAGE).append(System.lineSeparator());

                continue;
            }

            if (this.districtRepository.findByName(district.getName()) != null) {
                output.append(DUPLICATE_DATA_MESSAGE).append(System.lineSeparator());

                continue;
            }

            district.setTown(this.townRepository.findByName(districtImportDto.getTownName()));
            this.districtRepository.save(district);
            output.append(String.format(SUCCESSFUL_IMPORT_MESSAGE, district.getClass().getSimpleName(), district.getName()))
                    .append(System.lineSeparator());
        }

        return output.toString().trim();
    }
}
