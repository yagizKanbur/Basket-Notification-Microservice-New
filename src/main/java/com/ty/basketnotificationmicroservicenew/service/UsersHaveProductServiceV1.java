package com.ty.basketnotificationmicroservicenew.service;

import com.ty.basketnotificationmicroservicenew.dto.BasketCompleteOrderEvent;
import com.ty.basketnotificationmicroservicenew.dto.BasketItemEvent;
import com.ty.basketnotificationmicroservicenew.exceptions.ProductNotFoundException;
import com.ty.basketnotificationmicroservicenew.model.UsersHaveProduct;
import com.ty.basketnotificationmicroservicenew.repository.UsersHaveProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsersHaveProductServiceV1 implements UsersHaveProductService {
    private static final String NOTIFICATION_TOPIC = "product.notification";

    private final UsersHaveProductRepository usersHaveProductRepository;
    private final KafkaTemplate<String, UsersHaveProduct> kafkaTemplate;

    public void updateProductShoppersSet(BasketItemEvent readValue) {
        Optional<UsersHaveProduct> optionalProduct = usersHaveProductRepository.findById(readValue.getProductId());
        if(optionalProduct.isEmpty()){
            throw new ProductNotFoundException();
        }
        UsersHaveProduct usersHaveProduct = optionalProduct.get();
        usersHaveProduct.removeUserFromSet(readValue.getUserId());
        usersHaveProductRepository.save(usersHaveProduct);
    }

    public void saveToProductShoppersSet(BasketItemEvent readValue) {
        // Todo: codes bellow implemented just for demonstration purposes, since there is no real product service it fakes product entry
        UsersHaveProduct product = new UsersHaveProduct();
        product.setProductId(readValue.getProductId());
        product.setProductPrice(10.00);
        product.setStockQuantity(1000);
        product.setProductName("Demonstration");
        addProduct(product);
        Optional<UsersHaveProduct> optionalProduct = usersHaveProductRepository.findById(readValue.getProductId());
        if(optionalProduct.isEmpty()){
            throw new ProductNotFoundException();

        }
        Optional<UsersHaveProduct> fakeProduct = usersHaveProductRepository.findById(readValue.getProductId());
        UsersHaveProduct usersHaveProduct = fakeProduct.get();
        usersHaveProduct.addUserToSet(readValue.getUserId());
        usersHaveProductRepository.save(usersHaveProduct);

        if (usersHaveProduct.getStockQuantity()<3){
            kafkaTemplate.send(NOTIFICATION_TOPIC, usersHaveProduct);
        }
        if (usersHaveProduct.getStockQuantity().equals(0)){
            kafkaTemplate.send(NOTIFICATION_TOPIC, usersHaveProduct);
        }
    }

    public void removeFromProductsShoppersSets(BasketCompleteOrderEvent readValue) {
        for(int i = 0; i < readValue.getProductIds().size(); i++){
            Optional<UsersHaveProduct> optionalProduct =  usersHaveProductRepository.findById(readValue.getProductIds().get(i));
            if(optionalProduct.isPresent()){
                UsersHaveProduct usersHaveProduct = optionalProduct.get();
                usersHaveProduct.removeUserFromSet(readValue.getUserId());
                // Todo: Can we optimize this process?
            }
        }
    }

    public void updateProduct(UsersHaveProduct readValue) {
        Optional<UsersHaveProduct> optionalProduct = usersHaveProductRepository.findById(readValue.getProductId());
        if(optionalProduct.isEmpty()){
            throw new ProductNotFoundException();
            // Todo: Maybe save to db as a new product
        }
        UsersHaveProduct usersHaveProduct = optionalProduct.get();

        Double newProductPrice = readValue.getProductPrice();
        Integer newStockQuantity = readValue.getStockQuantity();

        if(!usersHaveProduct.getProductPrice().equals(newProductPrice)){
            if(newProductPrice< usersHaveProduct.getProductPrice()){
                // Todo: send lowered price notification
                kafkaTemplate.send(NOTIFICATION_TOPIC, usersHaveProduct);
            }
            usersHaveProduct.setProductPrice(newProductPrice);
        }

        if(!usersHaveProduct.getStockQuantity().equals(newStockQuantity)){
            if (newStockQuantity<3){
                // Todo: send low stock notification
                kafkaTemplate.send(NOTIFICATION_TOPIC, usersHaveProduct);
            }
            if (newStockQuantity.equals(0)){
                // Todo: send out of stock notification
                kafkaTemplate.send(NOTIFICATION_TOPIC, usersHaveProduct);
            }
            usersHaveProduct.setStockQuantity(newStockQuantity);
        }
        usersHaveProductRepository.save(usersHaveProduct);

    }

    public void addProduct(UsersHaveProduct readValue) {
        UsersHaveProduct usersHaveProduct = new UsersHaveProduct();
        usersHaveProduct.setProductId(readValue.getProductId());
        usersHaveProduct.setProductName(readValue.getProductName());
        usersHaveProduct.setProductPrice(readValue.getProductPrice());
        usersHaveProduct.setStockQuantity(readValue.getStockQuantity());

        usersHaveProductRepository.save(usersHaveProduct);
    }


}
