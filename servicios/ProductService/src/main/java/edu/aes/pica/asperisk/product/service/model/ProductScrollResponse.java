/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.aes.pica.asperisk.product.service.model;

import edu.puj.aes.pica.asperisk.oms.utilities.model.Product;
import edu.puj.aes.pica.asperisk.oms.utilities.model.ScrollResponse;
import java.util.List;
import lombok.Data;

/**
 *
 * @author acost
 */
@Data
public class ProductScrollResponse extends ScrollResponse{
    
    private List<Product> productos;
    
}
