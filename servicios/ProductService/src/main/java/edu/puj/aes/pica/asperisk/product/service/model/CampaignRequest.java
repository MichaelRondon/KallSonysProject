package edu.puj.aes.pica.asperisk.product.service.model;

import edu.puj.aes.pica.asperisk.oms.utilities.model.BasicRequest;
import edu.puj.aes.pica.asperisk.oms.utilities.model.BasicSearchParams;
import edu.puj.aes.pica.asperisk.oms.utilities.model.Categoria;
import edu.puj.aes.pica.asperisk.oms.utilities.model.State;
import lombok.Data;
import lombok.ToString;


@Data
@ToString
public class CampaignRequest {
    private State state;
    private Categoria categoria;
    private BasicSearchParams basicSearchParams;
    private BasicRequest basicRequest;
}
