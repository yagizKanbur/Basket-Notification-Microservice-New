package com.ty.basketnotificationmicroservicenew.repository;

import com.ty.basketnotificationmicroservicenew.model.UsersHaveProduct;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersHaveProductRepository extends CrudRepository<UsersHaveProduct, Long> {
}
