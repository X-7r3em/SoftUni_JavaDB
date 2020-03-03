package app.repositories;

import app.domain.dto.query3.SupplierIdDto;
import app.domain.entities.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Integer> {

    @Query("SELECT NEW app.domain.dto.query3.SupplierIdDto(s.id, s.name, SIZE(s.parts)) FROM Supplier s " +
            "WHERE s.importer = :isImporter GROUP BY s.id")
    List<SupplierIdDto> findAllByImporterIs(@Param(value = "isImporter") boolean isImporter);
}
