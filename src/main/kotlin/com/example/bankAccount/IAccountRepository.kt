package com.example.bankAccount

import org.springframework.data.mongodb.repository.MongoRepository
import java.util.*

interface IAccountRepository : MongoRepository<Account, String> {
    fun findByDocument(document : String): Optional<Account>
}