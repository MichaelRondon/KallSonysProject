package edu.aes.pica.asperisk.product.service.rest;

import edu.aes.pica.asperisk.product.service.model.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/producto")
public class SearchController {

    @RequestMapping(value = "/historico", method = RequestMethod.POST)
    public ResponseEntity<List<Product>> greeting(@RequestParam(value="ip", defaultValue="") String ip,
                                                  @RequestParam(value="cliente-id", defaultValue="-1") Long clienteId) {
        return new ResponseEntity(new Product(), HttpStatus.OK);
    }
}
