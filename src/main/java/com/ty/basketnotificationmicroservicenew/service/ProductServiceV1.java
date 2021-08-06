package com.ty.basketnotificationmicroservicenew.service;

import com.ty.basketnotificationmicroservicenew.dto.BasketCompleteOrderEvent;
import com.ty.basketnotificationmicroservicenew.dto.BasketItemEvent;
import com.ty.basketnotificationmicroservicenew.exceptions.ProductNotFoundException;
import com.ty.basketnotificationmicroservicenew.model.Product;
import com.ty.basketnotificationmicroservicenew.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceV1 implements ProductService {
    private static ProductRepository productRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private static final String NOTIFICATION_TOPIC = "product.notification";

    public void updateProductShoppersSet(BasketItemEvent readValue) {
        Optional<Product> optionalProduct = productRepository.findById(readValue.getProductId());
        if(optionalProduct.isEmpty()){
            throw new ProductNotFoundException();
        }
        Product product = optionalProduct.get();
        product.removeUserFromSet(readValue.getUserId());
        productRepository.save(product);
    }

    public void saveToProductShoppersSet(BasketItemEvent readValue) {
        Optional<Product> optionalProduct = productRepository.findById(readValue.getProductId());
        if(optionalProduct.isEmpty()){
            throw new ProductNotFoundException();
        }

        Product product = optionalProduct.get();
        product.addUserToSet(readValue.getUserId());
        productRepository.save(product);

        if (product.getStockQuantity()<3){
            // Todo: send low stock notification
            //kafkaTemplate.send(NOTIFICATION_TOPIC,)
        }
        if (product.getStockQuantity().equals(0)){
            // Todo: send out of stock notification
            //kafkaTemplate.send(NOTIFICATION_TOPIC,)
        }
    }

    public void removeFromProductsShoppersSets(BasketCompleteOrderEvent readValue) {
        for(int i = 0; i < readValue.getProductIds().size(); i++){
            Optional<Product> optionalProduct =  productRepository.findById(readValue.getProductIds().get(i));
            if(optionalProduct.isPresent()){
                Product product = optionalProduct.get();
                product.removeUserFromSet(readValue.getUserId());
                // Todo: Can we optimize this process?
            }
        }
    }

    public void updateProduct(Product readValue) {
        Optional<Product> optionalProduct = productRepository.findById(readValue.getProductId());
        if(optionalProduct.isEmpty()){
            throw new ProductNotFoundException();
            // Todo: Maybe save to db as a new product
        }
        Product product = optionalProduct.get();

        Double newProductPrice = readValue.getProductPrice();
        Integer newStockQuantity = readValue.getStockQuantity();

        if(!product.getProductPrice().equals(newProductPrice)){
            if(newProductPrice<product.getProductPrice()){
                // Todo: send lowered price notification
                //kafkaTemplate.send(NOTIFICATION_TOPIC,)
            }
            product.setProductPrice(newProductPrice);
        }

        if(!product.getStockQuantity().equals(newStockQuantity)){
            if (newStockQuantity<3){
                // Todo: send low stock notification
                //kafkaTemplate.send(NOTIFICATION_TOPIC,)
            }
            if (newStockQuantity.equals(0)){
                // Todo: send out of stock notification
                //kafkaTemplate.send(NOTIFICATION_TOPIC,)
            }
            product.setStockQuantity(newStockQuantity);
        }
        productRepository.save(product);

    }

    public void addProduct(Product readValue) {
        Product product = new Product();
        product.setProductId(readValue.getProductId());
        product.setProductName(readValue.getProductName());
        product.setProductPrice(readValue.getProductPrice());
        product.setStockQuantity(readValue.getStockQuantity());

        productRepository.save(product);
    }


}
