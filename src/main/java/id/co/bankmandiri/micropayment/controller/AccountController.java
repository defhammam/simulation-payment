package id.co.bankmandiri.micropayment.controller;

import id.co.bankmandiri.micropayment.constant.DefaultParameter;
import id.co.bankmandiri.micropayment.constant.Noun;
import id.co.bankmandiri.micropayment.constant.ResponseMessage;
import id.co.bankmandiri.micropayment.constant.UrlPath;
import id.co.bankmandiri.micropayment.dto.AccountResponseDto;
import id.co.bankmandiri.micropayment.entity.Account;
import id.co.bankmandiri.micropayment.service.AccountService;
import id.co.bankmandiri.micropayment.utils.CustomResponse;
import id.co.bankmandiri.micropayment.utils.PageResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    @PostMapping("/phone")
    public ResponseEntity<CustomResponse<AccountResponseDto>> addAccountByPhone(
            @RequestParam(name="number") String customerPhone
    ) {
        CustomResponse<AccountResponseDto> customResponse = new CustomResponse<>();
        customResponse.setData(accountService.saveByPhone(customerPhone));
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
    public PageResponseWrapper<Account> getAllAccountsWithPagination(
            @RequestParam(name="index", defaultValue=DefaultParameter.PAGE_INDEX) Integer pageIndex,
            @RequestParam(name="size", defaultValue=DefaultParameter.PAGE_SIZE) Integer pageSize,
            @RequestParam(name="sortBy", defaultValue=Noun.BALANCE) String sortCriteria,
            @RequestParam(name="direction", defaultValue=DefaultParameter.SORT_DIRECTION) String directionOfSort
    ) {
        Sort sortingTechnique = Sort.by(Sort.Direction.fromString(directionOfSort), sortCriteria);
        Pageable pageRequest = PageRequest.of(pageIndex, pageSize, sortingTechnique);
        return new PageResponseWrapper<>(accountService.getAllPerPage(pageRequest));
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
