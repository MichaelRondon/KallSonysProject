package edu.puj.aes.pica.asperisk.product.service.model;

import edu.puj.aes.pica.asperisk.oms.utilities.model.Campanign;
import edu.puj.aes.pica.asperisk.oms.utilities.model.Response;
import lombok.Data;

import java.util.List;

@Data
public class CampaignResponse extends Response{

    private List<Campanign> campanias;
}
