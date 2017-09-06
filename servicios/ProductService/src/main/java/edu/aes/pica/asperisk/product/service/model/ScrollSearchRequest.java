/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.aes.pica.asperisk.product.service.model;

import edu.puj.aes.pica.asperisk.oms.utilities.model.BasicRequest;
import edu.puj.aes.pica.asperisk.oms.utilities.model.BasicSearchParams;
import lombok.Data;
import lombok.ToString;

/**
 *
 * @author acost
 */
@Data
@ToString
public class ScrollSearchRequest {
    
    private BasicSearchParams basicSearchParams;
    private BasicRequest basicRequest;
    private String scrollId;
    
}
