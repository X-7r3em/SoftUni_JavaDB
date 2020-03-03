package bookshopsystemapp.domain.dto;

import bookshopsystemapp.domain.entities.AgeRestriction;
import bookshopsystemapp.domain.entities.EditionType;

import java.math.BigDecimal;

public interface ReducedBook {
    String getTitle();
    EditionType getEditionType();
    AgeRestriction getAgeRestriction();
    BigDecimal getPrice();
}
