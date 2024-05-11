package com.github.springsneaker.web.dto;


import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SneakerInfo {

    private Integer modelId;
    private List<String> modelTraits;
    private Double price;
    private List<SneakerInventory> sneakerInventories;
}
