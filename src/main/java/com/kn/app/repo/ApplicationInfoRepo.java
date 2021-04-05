/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kn.app.repo;

import com.kn.app.entity.ApplicationInfo;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author oghomwen.aigbedion
 */
@Repository
public interface ApplicationInfoRepo extends JpaRepository<ApplicationInfo, Integer> {

    Optional<ApplicationInfo> findByInfoName(String infoName);
}
