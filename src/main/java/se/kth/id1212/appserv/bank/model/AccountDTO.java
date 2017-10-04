package se.kth.id1212.appserv.bank.model;

/**
 * The views read-only view of an Account.
 */
public interface AccountDTO {
    /**                                     
     * Gets the number of this Account.
     *
     * @return the account number.
     */
    int getAcctNo();

    /**
     * Gets the balance of this Account.
     *
     * @return this accounts balance.
     */
    int getBalance();

    /**
     * Gets the first name of the holder of this Account.
     *
     * @return the first name of the holder of this Account.
     */
    String getFirstName();

    /**
     * Gets the last name of the holder of this Account.
     *
     * @return the last name of the holder of this Account.
     */
    String getLastName();

}
