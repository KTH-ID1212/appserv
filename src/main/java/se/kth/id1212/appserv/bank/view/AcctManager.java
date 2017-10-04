package se.kth.id1212.appserv.bank.view;

import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import se.kth.id1212.appserv.bank.controller.CashierFacade;
import se.kth.id1212.appserv.bank.model.AccountDTO;

@Named("acctManager")
@ConversationScoped
public class AcctManager implements Serializable {

    private static final long serialVersionUID = 16247164405L;
    @EJB
    private CashierFacade cashierFacade;
    private AccountDTO currentAcct;
    private String newAccountHolderFirstName;
    private String newAccountHolderLastName;
    private Integer newAccountBalance;
    private Integer transactionAmount;
    private Integer searchedAcct;
    private Exception transactionFailure;
    @Inject
    private Conversation conversation;

    private void startConversation() {
        if (conversation.isTransient()) {
            conversation.begin();
        }
    }

    private void stopConversation() {
        if (!conversation.isTransient()) {
            conversation.end();
        }
    }

    private void handleException(Exception e) {
        stopConversation();
        e.printStackTrace(System.err);
        transactionFailure = e;
    }

    private void readAcctData() {
        searchedAcct = currentAcct.getAcctNo();
        findAccount();
    }

    /**
     * @return <code>true</code> if the latest transaction succeeded, otherwise
     * <code>false</code>.
     */
    public boolean getSuccess() {
        return transactionFailure == null;
    }

    /**
     * Returns the latest thrown exception.
     */
    public Exception getException() {
        return transactionFailure;
    }

    /**
     * This return value is needed because of a JSF 2.2 bug. Note 3 on page 7-10
     * of the JSF 2.2 specification states that action handling methods may be
     * void. In JSF 2.2, however, a void action handling method plus an
     * if-element that evaluates to true in the faces-config navigation case
     * causes an exception.
     *
     * @return an empty string.
     */
    private String jsf22Bugfix() {
        return "";
    }

    /**
     * Withdraws the amount set by the latest call to
     * <code>setTransactionAmount</code> from the account specified by
     * <code>currentAcct.getAcctNo()</code>.
     */
    public String withdraw() {
        try {
            transactionFailure = null;
            cashierFacade.withdraw(currentAcct.getAcctNo(), transactionAmount);
            readAcctData();
        } catch (Exception e) {
            handleException(e);
        }
        return jsf22Bugfix();
    }

    /**
     * Searches for the account specified by the latest call to
     * <code>setSearchedAcct</code>.
     */
    public String findAccount() {
        try {
            startConversation();
            transactionFailure = null;
            currentAcct = cashierFacade.findAccount(searchedAcct);
        } catch (Exception e) {
            handleException(e);
        }
        return jsf22Bugfix();
    }

    /**
     * Deposits the amount set by the latest call to
     * <code>setTransactionAmount</code> from the account specified by
     * <code>currentAcct.getAcctNo()</code>.
     */
    public String deposit() {
        try {
            transactionFailure = null;
            cashierFacade.deposit(currentAcct.getAcctNo(), transactionAmount);
            readAcctData();
        } catch (Exception e) {
            handleException(e);
        }
        return jsf22Bugfix();
    }

    /**
     * Creates a new account. The holder's name is specified by the latest calls
     * to
     * <code>setNewAccountHolderFirstName</code> and
     * <code>setNewAccountHolderLastName</code>. The initial balance is
     * specified by the latest call to
     * <code>setNewAccountBalance</code>.
     */
    public String createAccount() {
        try {
            startConversation();
            transactionFailure = null;
            currentAcct = cashierFacade.createAccount(newAccountHolderFirstName,
                    newAccountHolderLastName, newAccountBalance);
        } catch (Exception e) {
            handleException(e);
        }
        return jsf22Bugfix();
    }

    /**
     * Set the value of searchedAcct
     *
     * @param searchedAcct new value of searchedAcct
     */
    public void setSearchedAcct(Integer searchedAcct) {
        this.searchedAcct = searchedAcct;
    }

    /**
     * Never used but JSF does not support write-only properties.
     */
    public Integer getSearchedAcct() {
        return null;
    }

    /**
     * Set the value of transactionAmount
     *
     * @param transactionAmount new value of transactionAmount
     */
    public void setTransactionAmount(Integer transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    /**
     * Never used but JSF does not support write-only properties.
     */
    public Integer getTransactionAmount() {
        return null;
    }

    /**
     * Set the value of newAccountBalance
     *
     * @param newAccountBalance new value of newAccountBalance
     */
    public void setNewAccountBalance(Integer newAccountBalance) {
        this.newAccountBalance = newAccountBalance;
    }

    /**
     * Never used but JSF does not support write-only properties.
     */
    public Integer getNewAccountBalance() {
        return null;
    }

    /**
     * Set the value of newAccountHolderLastName
     *
     * @param newAccountHolderLastName new value of newAccountHolderLastName
     */
    public void setNewAccountHolderLastName(String newAccountHolderLastName) {
        this.newAccountHolderLastName = newAccountHolderLastName;
    }

    /**
     * Never used but JSF does not support write-only properties.
     */
    public String getNewAccountHolderLastName() {
        return null;
    }

    /**
     * Set the value of newAccountHolderFirstName
     *
     * @param newAccountHolderFirstName new value of newAccountHolderFirstName
     */
    public void setNewAccountHolderFirstName(String newAccountHolderFirstName) {
        this.newAccountHolderFirstName = newAccountHolderFirstName;
    }

    /**
     * Never used but JSF does not support write-only properties.
     */
    public String getNewAccountHolderFirstName() {
        return null;
    }

    /**
     * Get the value of currentAcct
     *
     * @return the value of currentAcct
     */
    public AccountDTO getCurrentAcct() {
        return currentAcct;
    }
}