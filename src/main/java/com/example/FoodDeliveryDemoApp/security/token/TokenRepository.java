package com.example.FoodDeliveryDemoApp.security.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

    @Query(value = """
      select t from Token t inner join Admin a
      on t.admin.id = a.id
      where a.id = :id and (t.expired = false or t.revoked = false)
      """)
    List<Token> findAllValidTokenByAdmin(Long id);

    @Query(value = """
      select t from Token t inner join Owner a
      on t.owner.id = a.id
      where a.id = :id and (t.expired = false or t.revoked = false)
      """)
    List<Token> findAllValidTokenByOwner(Long id);

    @Query(value = """
      select t from Token t inner join Customer a
      on t.customer.id = a.id
      where a.id = :id and (t.expired = false or t.revoked = false)
      """)
    List<Token> findAllValidTokenByCustomer(Long id);

    Optional<Token> findByToken(String token);

}
