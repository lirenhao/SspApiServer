package com.yada.ssp.apiServer.dao;

import com.yada.ssp.apiServer.model.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MerchantDao extends JpaRepository<Merchant, String> {
    
}