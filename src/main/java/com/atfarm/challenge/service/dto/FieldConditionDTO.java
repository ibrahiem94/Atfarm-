package com.atfarm.challenge.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.Objects;

/**
 * A DTO for the {@link com.atfarm.challenge.domain.FieldCondition} entity.
 */
public class FieldConditionDTO implements Comparable<FieldConditionDTO>{

    
	@JsonIgnore
	private Long id;
    
    @NotNull
    private Double vegetation;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date occurrenceAt;

    public FieldConditionDTO() {
    }

    public FieldConditionDTO(@NotNull Double vegetation, @NotNull Date occurrenceAt) {
        this.vegetation = vegetation;
        this.occurrenceAt = occurrenceAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getVegetation() {
        return vegetation;
    }

    public void setVegetation(Double vegetation) {
        this.vegetation = vegetation;
    }

    public Date getOccurrenceAt() {
        return occurrenceAt;
    }

    public void setOccurrenceAt(Date occurrenceAt) {
        this.occurrenceAt = occurrenceAt;
    }

    
    
    @Override
    public int compareTo(FieldConditionDTO o) {
        if (o.getOccurrenceAt().equals(getOccurrenceAt()))
            return 0;
        else if(o.getOccurrenceAt().after(getOccurrenceAt()))
            return -1;
        else
            return 1;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FieldConditionDTO fieldConditionDTO = (FieldConditionDTO) o;
        if (fieldConditionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), fieldConditionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FieldConditionDTO{" +
            "id=" + getId() +
            ", vegetation=" + getVegetation() +
            ", occurrenceAt='" + getOccurrenceAt() + "'" +
            "}";
    }
}
