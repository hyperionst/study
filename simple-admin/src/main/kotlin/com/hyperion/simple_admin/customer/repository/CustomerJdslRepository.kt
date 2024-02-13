package com.hyperion.simple_admin.customer.repository

import com.hyperion.simple_admin.customer.model.BaseUserModel
import com.linecorp.kotlinjdsl.support.spring.data.jpa.repository.KotlinJdslJpqlExecutor
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface CustomerJdslRepository : JpaRepository<BaseUserModel, UUID>, KotlinJdslJpqlExecutor
