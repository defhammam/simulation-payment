package id.co.bankmandiri.micropayment.constant;

public class ResponseMessage {
    public static final String ACCOUNT_EXISTS_ERROR = "Account already exists.";
    public static final String NOT_ENOUGH_BALANCE_ERROR = "Payment can't be processed because "
            + "either balance is empty or amount of debit exceeds current balance.";
    public static final String GET_SINGLE_SUCCESS = "Data %s found with ID %s.";
    public static final String GET_BULK_SUCCESS = "All %s found.";
    public static final String ADD_SINGLE_SUCCESS = "A new %s has been added.";
    public static final String ADD_BULK_SUCCESS = "%d new %s have been added.";
    public static final String UPDATE_SINGLE_SUCCESS = "An existing %s has been updated.";
    public static final String DELETE_SINGLE_SUCCESS = "A previously existed %s has been removed.";
    public static final String PAYMENT_CREATED = "Payment %s successful.";
}
