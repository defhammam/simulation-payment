package id.co.bankmandiri.micropayment.service.implementation;

import id.co.bankmandiri.micropayment.dto.AccountResponseDto;
import id.co.bankmandiri.micropayment.entity.Account;
import id.co.bankmandiri.micropayment.repository.AccountRepository;
import id.co.bankmandiri.micropayment.utils.exception.AccountExistsException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountServiceImplTest {
    @Mock
    private AccountRepository accountRepository;
    @InjectMocks
    private AccountServiceImpl accountService;
    @Mock
    private static Account mockedAccount;
    @Mock
    private AccountResponseDto accountResponseDtoMock;
    @Mock
    private List<Account> accountsMock;

    @BeforeAll
    static void beforeAll() {
        mockedAccount = new Account();
        mockedAccount.setId("fake-uuid");
        mockedAccount.setCustomerPhone("+62896969696");
        mockedAccount.setBalance(96969696);
        mockedAccount.setIsDeleted(false);
    }

    @Test
    void itShouldSaveAndReturnAccount() {
        when(accountRepository.save(any())).thenReturn(mockedAccount);
        try {
            Account actualAccount = accountService.save(mockedAccount);
            assertEquals(mockedAccount.getId(), actualAccount.getId());
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @Test
    void itShouldSaveAndReturnBulkOfAccounts() {
        accountsMock = new ArrayList<>();
        accountsMock.add(new Account());
        accountsMock.add(new Account());
        when(accountRepository.saveAll(any())).thenReturn(accountsMock);
        try {
            List<Account> actualAccounts = accountService.saveBulk(accountsMock);
            assertEquals(accountsMock.get(0), actualAccounts.get(0));
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    /*@Test
    void itShouldGetAllAccounts() {
        accountsMock = new ArrayList<>();
        accountsMock.add(new Account());
        accountsMock.add(new Account());
        when(accountRepository.findAll()).thenReturn(accountsMock);
        try {
            List<Account> actualAccounts = accountService.getAll();
            assertEquals(accountsMock.get(0), actualAccounts.get(0));
            verify(accountRepository, times(1)).findAll();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }*/

    @Test
    void itShouldGetAccountById() {
        when(accountRepository.findById(anyString())).thenReturn(Optional.of(mockedAccount));
        try {
            Account actualAccount = accountService.getById(anyString());
            assertEquals(mockedAccount.getBalance(), actualAccount.getBalance());
            verifyNoMoreInteractions(accountRepository);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @Test
    void itShouldThrowNoElementWhenGetAccountByIdReturnsEmptyResult() {
        when(accountRepository.findById(anyString())).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                accountService.getById(anyString());
            }
        });
    }

    @Test
    void itShouldUpdateAccountWhenRemoveByIdIsExecuted() {
        when(accountRepository.findById(anyString())).thenReturn(Optional.of(mockedAccount));
        when(accountRepository.save(any())).thenReturn(mockedAccount);
        try {
            Account actualAccount = accountService.softRemoveById(anyString());
            assertEquals(mockedAccount.getIsDeleted(), actualAccount.getIsDeleted());
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @Test
    void itShouldSaveAccountByPhoneAndReturnAccountResponseDto() {
        accountResponseDtoMock = new AccountResponseDto("+62896969696", 96969696);
        when(accountRepository.findAccountByCustomerPhone(anyString())).thenReturn(null);
        when(accountRepository.save(any())).thenReturn(mockedAccount);
        try {
            AccountResponseDto actualResponse = accountService.saveByPhone(accountResponseDtoMock.getPhone());
            assertEquals(accountResponseDtoMock.getPhone(), actualResponse.getPhone());
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @Test
    void itShouldThrowAccountExistsExceptionWhenFindAccountByPhoneDoesNotReturnNull() {
        when(accountRepository.findAccountByCustomerPhone(anyString())).thenReturn(mockedAccount);
        assertThrows(AccountExistsException.class, () -> accountService.saveByPhone(anyString()));
    }
}
