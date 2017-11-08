package edu.puj.aes.pica.asperisk.product.service.model;

import edu.puj.aes.pica.asperisk.oms.utilities.model.Campanign;
import edu.puj.aes.pica.asperisk.oms.utilities.model.Response;
import java.util.LinkedList;
import lombok.Data;

import java.util.List;

public class CampaignResponse extends Response<Campanign>{

    private List<Campanign> campanias;

    public List<Campanign> getCampanias() {
        if (getObjects() == null) {
            setObjects(new LinkedList<>());
        }
        return super.getObjects();
    }

    public void setCampanias(List<Campanign> campanias) {
        super.setObjects(campanias);
    }
    
    
}
