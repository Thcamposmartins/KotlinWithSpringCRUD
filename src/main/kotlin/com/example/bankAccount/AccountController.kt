package com.example.bankAccount

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.lang.RuntimeException

@RestController
@RequestMapping("accounts")
class AccountController(val repository: IAccountRepository){
    @PostMapping
    fun create(@RequestBody account: Account) = ResponseEntity.ok(repository.save(account))

    @GetMapping
    fun read(): ResponseEntity<MutableList<Account>> = ResponseEntity.ok(repository.findAll())

    @PutMapping("{document}")
    fun update(@PathVariable document: String, @RequestBody account: Account): ResponseEntity<Account>{
        val accountDBOptional = repository.findByDocument(document) // busca o account por document no banco
        val accountDB = accountDBOptional.orElseThrow{RuntimeException("Account document: $document not found!")} // orElseThrow faz um get em accountDBOptional retornando o account ou erro se vazio
        val saved = repository.save(accountDB.copy(name = account.name,balance = account.balance)) //salva uma copia atualizada com os valores
        return ResponseEntity.ok(saved)

        /*
         val accountDBOptional = repository.findByDocument(document)
         val toSave = accountDBOptional
                    .orElseThrow{RuntimeException("Account document: $document not found!")}
                    .copy(name = account.name,balance = account.balance)
         return ResponseEntity.ok(repository.save(toSave))
         */
    }

    @DeleteMapping("{document}")
    fun delete(@PathVariable document: String) = repository
            .findByDocument(document) //busca account pelo documento
            .ifPresent{repository.delete(it)} // se existir apaga o accont achado
}