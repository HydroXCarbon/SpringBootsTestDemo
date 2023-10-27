package com.backend.repository;

import com.backend.entity.Address;
import com.backend.entity.User;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface AddressRepository extends CrudRepository<Address, String> {

    List<Address> findByUser(User user);

}
