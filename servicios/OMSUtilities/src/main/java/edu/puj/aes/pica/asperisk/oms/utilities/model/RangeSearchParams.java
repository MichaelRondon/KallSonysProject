package edu.puj.aes.pica.asperisk.oms.utilities.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * Created by mfrondon on 03/08/2017.
 */
@Data
@ToString
public class RangeSearchParams {

    @JsonProperty("fecha-min")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date fechaMin;

    @JsonProperty("fecha-max")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date fechaMax;
}
