package com.users.repositories;

import com.users.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository interface for accessing Address entities.
 */
public interface AddressRepository extends JpaRepository<Address, Integer> {

  /**
   * Finds addresses by user ID.
   *
   * @param userId the ID of the user whose addresses are to be retrieved.
   * @return a list of addresses associated with the given user ID.
   */
  List<Address> findByUserId(Integer userId);
}
