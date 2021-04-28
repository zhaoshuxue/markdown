package com.zsx.md.dao;

import com.zsx.md.entity.Mnote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MnoteDao extends JpaRepository<Mnote, Integer> {

    List<Mnote> findByStatusAndUserId(Integer status, Integer userId);


    @Modifying
    @Query("update Mnote m set m.pid=?1, m.orders=?2 where (m.id = ?3)")
    void updateOrdersById(Integer pid, Integer orders, Integer id);

    @Modifying
    @Query("update Mnote m set m.orders=m.orders+1 where (m.pid = ?1 and m.orders >= ?2)")
    void updateOrders1(Integer pid, Integer orders);

    @Modifying
    @Query("update Mnote m set m.orders=m.orders+1 where (m.pid = ?1 and m.orders > ?2)")
    void updateOrders2(Integer pid, Integer orders);


}
