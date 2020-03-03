package app.services;

import app.domain.dto.query3.SupplierIdWrapperDto;
import app.domain.dto.supplierimport.SupplierDto;
import app.domain.entities.Supplier;
import app.repositories.SupplierRepository;
import app.utilities.ValidatorUtility;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static app.utilities.SystemMessages.ALREADY_SEEDED;
import static app.utilities.SystemMessages.SUCCESSFUL_SEED;

@Service
public class SupplierServiceImpl implements SupplierService {
    private final SupplierRepository supplierRepository;
    private final ValidatorUtility validatorUtility;
    private final ModelMapper mapper;

    @Autowired
    public SupplierServiceImpl(SupplierRepository supplierRepository, ValidatorUtility validatorUtility, ModelMapper mapper) {
        this.supplierRepository = supplierRepository;
        this.validatorUtility = validatorUtility;
        this.mapper = mapper;
    }

    @Override
    public String seedSuppliers(List<SupplierDto> supplierDtos) {
        if (this.supplierRepository.count() != 0) {
            return String.format(ALREADY_SEEDED, "Suppliers");
        }

        StringBuilder output = new StringBuilder();
        for (SupplierDto supplierDto : supplierDtos) {
            if (!this.validatorUtility.isValid(supplierDto)) {
                output.append(this.validatorUtility.validationErrors(supplierDto))
                        .append(System.lineSeparator());
                continue;
            }


            Supplier supplier = this.mapper.map(supplierDto, Supplier.class);

            this.supplierRepository.saveAndFlush(supplier);
        }

        return output.toString() + String.format(SUCCESSFUL_SEED, "Suppliers");
    }

    @Override
    public SupplierIdWrapperDto findAllSuppliersByImport(boolean isImporter) {
        return new SupplierIdWrapperDto(
                this.supplierRepository.findAllByImporterIs(isImporter));
    }
}
