package app.services;

import app.domain.dto.PartDto;

public interface PartService {
    String seedParts(PartDto[] partDtos);
}
