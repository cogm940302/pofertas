package com.mit.repository;

import com.mit.domain.POFER02Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOnboardingRepository extends JpaRepository<POFER02Client, String> {
}
