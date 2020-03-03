package app.services;

import app.domain.dto.partimport.PartDto;

import java.util.List;

public interface PartService {
    String seedParts(List<PartDto> partDtos);
}
