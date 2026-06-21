package com.torloksz.arethium.dto;

import java.util.List;

public record RoadmapResponseDTO(
        List<ModuleResponseDTO> modules
) {
}
