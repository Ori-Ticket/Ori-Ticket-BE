package com.zerobase.oriticket.domain.transaction.repository;

import com.zerobase.oriticket.domain.post.entity.Post;
import com.zerobase.oriticket.domain.transaction.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    boolean existsBySalePost(Post salePost);

    @Query("SELECT CASE \n" +
            "WHEN \n" +
            "SUM(CASE WHEN status = 'Canceled' THEN 1 ELSE 0 END) > 0 AND \n" +
            "SUM(CASE WHEN status IN ('Pending', 'Received', 'Completed', 'Reported') THEN 1 ELSE 0 END) = 0 \n" +
            "THEN true \n" +
            "WHEN \n" +
            "SUM(CASE WHEN status = 'Canceled' THEN 1 ELSE 0 END) = 0 AND \n" +
            "SUM(CASE WHEN status IN ('Pending', 'Received', 'Completed', 'Reported') THEN 1 ELSE 0 END) = 0 \n" +
            "THEN true \n" +
            "WHEN \n" +
            "SUM(CASE WHEN status = 'Canceled' THEN 1 ELSE 0 END) > 0 AND \n" +
            "SUM(CASE WHEN status IN ('Pending', 'Received', 'Completed', 'Reported') THEN 1 ELSE 0 END) > 0 \n" +
            "THEN false \n" +
            "WHEN \n" +
            "SUM(CASE WHEN status = 'Canceled' THEN 1 ELSE 0 END) = 0 AND \n" +
            "SUM(CASE WHEN status IN ('Pending', 'Received', 'Completed', 'Reported') THEN 1 ELSE 0 END) > 0 \n" +
            "THEN false \n" +
            "ELSE true \n" +
            "END AS result \n" +
            "FROM Transaction WHERE salePost = %:salePost%")
    boolean existsCanRegisterByStatus(@Param("salePost") Post salePost);

}
