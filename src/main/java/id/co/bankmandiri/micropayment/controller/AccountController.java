package id.co.bankmandiri.micropayment.controller;

import id.co.bankmandiri.micropayment.constant.Noun;
import id.co.bankmandiri.micropayment.constant.ResponseMessage;
import id.co.bankmandiri.micropayment.constant.UrlPath;
import id.co.bankmandiri.micropayment.entity.Account;
import id.co.bankmandiri.micropayment.service.AccountService;
import id.co.bankmandiri.micropayment.utils.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(UrlPath.MST_BANK)
public class AccountController {
    AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<CustomResponse<Account>> addAccount(@RequestBody Account newAccount) {
        CustomResponse<Account> customResponse = new CustomResponse<>();
        customResponse.setData(accountService.save(newAccount));
        customResponse.setMessage(String.format(
                ResponseMessage.ADD_SINGLE_SUCCESS, Noun.BANK
        ));
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(customResponse);
    }
    @PostMapping("/bulk")
    public ResponseEntity<CustomResponse<List<Account>>> addBulkAccounts(@RequestBody List<Account> accountsToAdd) {
        CustomResponse<List<Account>> customResponse = new CustomResponse<>();
        customResponse.setData(accountService.saveBulk(accountsToAdd));
        customResponse.setMessage(String.format(
                ResponseMessage.ADD_BULK_SUCCESS, accountsToAdd.size(), Noun.BANKS
        ));
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(customResponse);
    }

    @GetMapping
    public ResponseEntity<CustomResponse<List<Account>>> getAllAccounts() {
        CustomResponse<List<Account>> customResponse = new CustomResponse<>();
        customResponse.setData(accountService.getAll());
        customResponse.setMessage(String.format(
                ResponseMessage.GET_BULK_SUCCESS, Noun.BANKS
        ));
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(customResponse);
    }

    @GetMapping("/{idOfAccount}")
    public ResponseEntity<CustomResponse<Account>> findAccountById(@PathVariable String idOfAccount) {
        CustomResponse<Account> customResponse = new CustomResponse<>();
        customResponse.setData(accountService.getById(idOfAccount));
        customResponse.setMessage(String.format(
                ResponseMessage.GET_SINGLE_SUCCESS, Noun.BANK, idOfAccount
        ));
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(customResponse);
    }

    @PutMapping
    public ResponseEntity<CustomResponse<Account>> updateAccount(@RequestBody Account accountToUpdate) {
        CustomResponse<Account> customResponse = new CustomResponse<>();
        customResponse.setData(accountService.save(accountToUpdate));
        customResponse.setMessage(String.format(
                ResponseMessage.UPDATE_SINGLE_SUCCESS, Noun.BANK
        ));
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(customResponse);
    }

    @DeleteMapping("/{idOfAccount}")
    public ResponseEntity<CustomResponse<Account>> removeAccountById(@PathVariable String idOfAccount) {
        CustomResponse<Account> customResponse = new CustomResponse<>();
        customResponse.setData(accountService.softRemoveById(idOfAccount));
        customResponse.setMessage(String.format(
                ResponseMessage.DELETE_SINGLE_SUCCESS, Noun.BANK
        ));
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(customResponse);
    }
}
