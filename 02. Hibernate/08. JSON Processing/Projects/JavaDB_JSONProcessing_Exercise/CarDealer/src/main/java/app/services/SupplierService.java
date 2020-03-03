package app.services;

import app.domain.dto.SupplierDto;
import app.domain.dto.query3.SupplierIdDto;

import java.util.List;

public interface SupplierService {
    String seedSuppliers(SupplierDto[] supplierDtos);

    List<SupplierIdDto> findAllSuppliersByImport(boolean isImporter);
}
