package com.atfarm.challenge.web.rest;

import com.atfarm.challenge.service.FieldService;
import com.atfarm.challenge.service.dto.FieldConditionDTO;
import com.atfarm.challenge.web.rest.response.FieldStatisticsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.sql.Date;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
public class FieldController {

    @Autowired
    @Qualifier("InMemoryFieldServiceImpl")
    private FieldService inMemoryfieldService;

    @Autowired
    @Qualifier("FieldServiceImpl")
    private FieldService fieldService;

    /**
     * {@code POST  /v1/field-conditions} : Create a new fieldCondition using
     * inMemoryService.
     *
     * @param fieldConditionDTO the fieldConditionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
     * body the new fieldConditionDTO, or with status
     * {@code 400 (Bad Request)} if the fieldCondition has already expired.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping(value = "/v1/field-conditions", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> addFieldConditionV1(@RequestBody FieldConditionDTO fieldCondition) {
        if (inMemoryfieldService.isFieldConditionExpired(fieldCondition)) {
            return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
        } else {
            inMemoryfieldService.addFieldCondition(fieldCondition);
            return new ResponseEntity<Object>(HttpStatus.CREATED);
        }
    }

    /**
     * {@code POST  /v2/field-conditions} : Create a new fieldCondition using
     * DbService.
     *
     * @param fieldConditionDTO the fieldConditionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
     * body the new fieldConditionDTO
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping(value = "/v2/field-conditions", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> addFieldConditionV2(@RequestBody FieldConditionDTO fieldCondition) {
        fieldService.addFieldCondition(fieldCondition);
        return new ResponseEntity<Object>(HttpStatus.CREATED);
    }

    /**
     * {@code GET  /v1/field-statistics} : get statistics for all inMemoryDtos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
     * of fieldConditions in body.
     */
    @GetMapping("/v1/field-statistics")
    public ResponseEntity<FieldStatisticsResponse> getStatistics() {
        FieldStatisticsResponse response = new FieldStatisticsResponse();
        response.setVegetation(inMemoryfieldService.getStatistics());
        return new ResponseEntity<FieldStatisticsResponse>(response, HttpStatus.OK);
    }

    /**
     * {@code GET  /v2/field-statistics} : get statistics for all Db Entities
     * between defined dates.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
     * of fieldConditions in body.
     */
    @GetMapping("/v2/field-statistics")
    public ResponseEntity<FieldStatisticsResponse> getStatisticsV2(
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime  startDate,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime  endDate) {

        FieldStatisticsResponse response = new FieldStatisticsResponse();
        response.setVegetation(fieldService.findStatisticsBetweenDates(Date.valueOf(endDate.toLocalDate()), Date.valueOf(startDate.toLocalDate())));
        return new ResponseEntity<FieldStatisticsResponse>(response, HttpStatus.OK);

    }
}
