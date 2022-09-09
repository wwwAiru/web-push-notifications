package com.webpush.application.repository;

import com.webpush.application.entity.SubscriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriptionEntityRepository extends JpaRepository<SubscriptionEntity, String> {

    @Modifying
    @Query("delete from SubscriptionEntity where endpoint = :endpoint")
    void deleteByEndpoint(@Param("endpoint") String endpoint);

    List<SubscriptionEntity> findAll();
}
