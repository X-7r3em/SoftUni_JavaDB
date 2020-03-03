package app.services;

import app.domain.dto.query3.SupplierIdWrapperDto;
import app.domain.dto.supplierimport.SupplierDto;

import java.util.List;

public interface SupplierService {
    String seedSuppliers(List<SupplierDto> supplierDtos);

    SupplierIdWrapperDto findAllSuppliersByImport(boolean isImporter);
}
