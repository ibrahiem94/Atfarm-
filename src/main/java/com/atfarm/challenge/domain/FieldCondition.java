package com.atfarm.challenge.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Date;
import java.time.Instant;

/**
 * A FieldCondition.
 */
@Entity
@Table(name = "field_condition")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FieldCondition implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "vegetation")
	@NotNull
	private Double vegetation;

	@NotNull
	@Column(name = "occurrence_at",nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Instant occurrenceAt;

	// jhipster-needle-entity-add-field - JHipster will add fields here, do not
	// remove
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getVegetation() {
		return vegetation;
	}

	public FieldCondition vegetation(Double vegetation) {
		this.vegetation = vegetation;
		return this;
	}

	public void setVegetation(Double vegetation) {
		this.vegetation = vegetation;
	}

	public Date getOccurrenceAt() {
		return new Date(occurrenceAt.getEpochSecond());
	}

	public FieldCondition occurrenceAt(Date occurrenceAt) {
		this.occurrenceAt = Instant.ofEpochMilli(occurrenceAt.getTime());
		return this;
	}

	public void setOccurrenceAt(Date occurrenceAt) {
		this.occurrenceAt = Instant.ofEpochMilli(occurrenceAt.getTime());
	}
	// jhipster-needle-entity-add-getters-setters - JHipster will add getters and
	// setters here, do not remove

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof FieldCondition)) {
			return false;
		}
		return id != null && id.equals(((FieldCondition) o).id);
	}

	@Override
	public int hashCode() {
		return Math.toIntExact(this.getId());
	}

	@Override
	public String toString() {
		return "FieldCondition{" + "id=" + getId() + ", vegetation=" + getVegetation() + ", occurrenceAt='"
				+ getOccurrenceAt() + "'" + "}";
	}
}
