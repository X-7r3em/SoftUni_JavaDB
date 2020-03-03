package app.services;

import app.domain.dto.partimport.PartDto;
import app.domain.entities.Part;
import app.domain.entities.Supplier;
import app.repositories.PartRepository;
import app.repositories.SupplierRepository;
import app.utilities.ValidatorUtility;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.List;
import java.util.Random;

import static app.utilities.SystemMessages.ALREADY_SEEDED;
import static app.utilities.SystemMessages.SUCCESSFUL_SEED;

@Service
public class PartServiceImpl implements PartService {
    private final PartRepository partRepository;
    private final SupplierRepository supplierRepository;
    private final ValidatorUtility validatorUtility;
    private final ModelMapper mapper;
    private final Random random;

    @Autowired
    public PartServiceImpl(PartRepository partRepository, SupplierRepository supplierRepository,
                           ValidatorUtility validatorUtility, ModelMapper mapper, Random random) {
        this.partRepository = partRepository;
        this.supplierRepository = supplierRepository;
        this.validatorUtility = validatorUtility;
        this.mapper = mapper;
        this.random = random;
    }

    @Override
    @Transactional
    public String seedParts(List<PartDto> partDtos) {
        if (this.partRepository.count() != 0) {
            return String.format(ALREADY_SEEDED, "Parts");
        }

        List<Supplier> suppliers = this.supplierRepository.findAll();

        StringBuilder output = new StringBuilder();
        for (PartDto partDto : partDtos) {
            if (!this.validatorUtility.isValid(partDto)) {
                output.append(this.validatorUtility.validationErrors(partDto))
                        .append(System.lineSeparator());
                continue;
            }


            Part part = this.mapper.map(partDto, Part.class);
            Supplier supplier = suppliers.get(this.random.nextInt(suppliers.size()));

            part.setSupplier(supplier);

            this.partRepository.saveAndFlush(part);
        }

        return output.toString() + String.format(SUCCESSFUL_SEED, "Parts");
    }
}
