package app.ccb.services;

import app.ccb.common.Constants;
import app.ccb.domain.dtos.branchImport.BranchDto;
import app.ccb.domain.entities.Branch;
import app.ccb.repositories.BranchRepository;
import app.ccb.util.FileUtil;
import app.ccb.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Validation;
import java.io.IOException;

import static app.ccb.common.Constants.ERROR_MESSAGE;
import static app.ccb.common.Constants.SUCCESS_MESSAGE;

@Service
public class BranchServiceImpl implements BranchService {
    private static final String BRANCH_JSON_PATH = System.getProperty("user.dir") + "\\src\\main\\resources\\files\\json\\branches.json";

    private BranchRepository branchRepository;
    private FileUtil fileUtil;
    private Gson gson;
    private ModelMapper modelMapper;
    private ValidationUtil validationUtil;

    @Autowired
    public BranchServiceImpl(BranchRepository branchRepository, FileUtil fileUtil, Gson gson,
                             ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.branchRepository = branchRepository;
        this.fileUtil = fileUtil;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public Boolean branchesAreImported() {
        return branchRepository.count() > 0;
    }

    @Override
    public String readBranchesJsonFile() throws IOException {
        return fileUtil.readFile(BRANCH_JSON_PATH);
    }

    @Override
    public String importBranches(String branchesJson) {
        StringBuilder output = new StringBuilder();
        BranchDto[] branchDtos = gson.fromJson(branchesJson, BranchDto[].class);
        for (BranchDto branchDto : branchDtos) {
            Branch branch = modelMapper.map(branchDto, Branch.class);
            if (!validationUtil.isValid(branch)) {
                output.append(ERROR_MESSAGE).append(System.lineSeparator());
                continue;
            }

            branchRepository.saveAndFlush(branch);
            output.append(String.format(SUCCESS_MESSAGE, branch.getClass().getSimpleName(), branch.getName()))
                    .append(System.lineSeparator());
        }

        return output.toString().trim();
    }
}
