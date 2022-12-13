package id.co.bankmandiri.micropayment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.co.bankmandiri.micropayment.constant.Noun;
import id.co.bankmandiri.micropayment.constant.ResponseMessage;
import id.co.bankmandiri.micropayment.constant.UrlPath;
import id.co.bankmandiri.micropayment.dto.AccountResponseDto;
import id.co.bankmandiri.micropayment.entity.Account;
import id.co.bankmandiri.micropayment.service.AccountService;
import id.co.bankmandiri.micropayment.utils.CustomResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AccountController.class)
public class AccountControllerTest {
    @MockBean
    private AccountService accountService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Mock
    private Account mockedAccount;
    @Mock
    private AccountResponseDto accountResponseDtoMock;
    @Mock
    private List<Account> accountsMock;

    @BeforeEach
    void setUp() {
        mockedAccount = new Account();
        mockedAccount.setId("fake-uuid");
        mockedAccount.setCustomerPhone("+62896969696");
        mockedAccount.setBalance(96969696);
        mockedAccount.setIsDeleted(false);
    }

    @Test
    void itShouldAddAccountAndReturnResponseOfAccountAndStatusCreated() throws Exception {
        when(accountService.save(any())).thenReturn(mockedAccount);
        CustomResponse<Account> customResponse = new CustomResponse<>(
                String.format(ResponseMessage.ADD_SINGLE_SUCCESS, Noun.BANK),
                mockedAccount
        );
        mockMvc.perform(post(UrlPath.MST_BANK)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mockedAccount))
        ).andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(customResponse)));
    }

    @Test
    void itShouldAddAccountByPhoneAndReturnResponseDtoOfAccountAndStatusCreated() throws Exception {
        accountResponseDtoMock = new AccountResponseDto("+62896969696", 96969696);
        when(accountService.saveByPhone(any())).thenReturn(accountResponseDtoMock);
        CustomResponse<AccountResponseDto> customResponse = new CustomResponse<>(
                String.format(ResponseMessage.ADD_SINGLE_SUCCESS, Noun.BANK),
                accountResponseDtoMock
        );
        mockMvc.perform(post(UrlPath.MST_BANK + "/phone?number=" + accountResponseDtoMock.getPhone()))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(customResponse)));
    }

    @Test
    void itShouldGetAccountAndReturnResponseOfAccountIncludingStatusOk() throws Exception {
        when(accountService.getById(anyString())).thenReturn(mockedAccount);
        CustomResponse<Account> customResponse = new CustomResponse<>(
                String.format(ResponseMessage.GET_SINGLE_SUCCESS, Noun.BANK, mockedAccount.getId()),
                mockedAccount
        );
        mockMvc.perform(get(UrlPath.MST_BANK + "/" + mockedAccount.getId()))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(customResponse)));
    }

    @Test
    void itShouldAddBulkAccountsAndReturnResponseOfAccountsIncludingStatusCreated() throws Exception {
        accountsMock = new ArrayList<>();
        accountsMock.add(new Account());
        accountsMock.add(new Account());
        when(accountService.saveBulk(any())).thenReturn(accountsMock);
        CustomResponse<List<Account>> customResponse = new CustomResponse<>(
                String.format(ResponseMessage.ADD_BULK_SUCCESS, accountsMock.size(), Noun.BANKS),
                accountsMock
        );
        mockMvc.perform(post(UrlPath.MST_BANK + "/bulk")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accountsMock))
                ).andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(customResponse)));
    }

    @Test
    void itShouldGetAllAccountsAndReturnResponseOfAccountsIncludingStatusOk() throws Exception {
        accountsMock = new ArrayList<>();
        accountsMock.add(new Account());
        accountsMock.add(new Account());
        when(accountService.getAll()).thenReturn(accountsMock);
        CustomResponse<List<Account>> customResponse = new CustomResponse<>(
                String.format(ResponseMessage.GET_BULK_SUCCESS, Noun.BANKS),
                accountsMock
        );
        mockMvc.perform(get(UrlPath.MST_BANK))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(customResponse)));
    }

    @Test
    void itShouldUpdateAccountAndReturnResponseOfAccountAndStatusOk() throws Exception {
        when(accountService.save(any())).thenReturn(mockedAccount);
        CustomResponse<Account> customResponse = new CustomResponse<>(
                String.format(ResponseMessage.UPDATE_SINGLE_SUCCESS, Noun.BANK),
                mockedAccount
        );
        mockMvc.perform(put(UrlPath.MST_BANK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockedAccount))
                ).andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(customResponse)));
    }

    @Test
    void itShouldSoftDeleteAccountAndReturnResponseOfAccountIncludingStatusOk() throws Exception {
        when(accountService.softRemoveById(anyString())).thenReturn(mockedAccount);
        CustomResponse<Account> customResponse = new CustomResponse<>(
                String.format(ResponseMessage.DELETE_SINGLE_SUCCESS, Noun.BANK),
                mockedAccount
        );
        mockMvc.perform(delete(UrlPath.MST_BANK + "/" + mockedAccount.getId()))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(customResponse)));
    }
}
