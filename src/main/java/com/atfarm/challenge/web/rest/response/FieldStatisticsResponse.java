package com.atfarm.challenge.web.rest.response;

import com.atfarm.challenge.service.dto.VegetationDTO;

public class FieldStatisticsResponse {

    private VegetationDTO vegetation;

    public VegetationDTO getVegetation() {
        return vegetation;
    }

    public void setVegetation(VegetationDTO vegetation) {
        this.vegetation = vegetation;
    }
}
