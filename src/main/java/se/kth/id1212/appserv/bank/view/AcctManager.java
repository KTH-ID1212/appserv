/*
 * The MIT License
 *
 * Copyright 2017 Leif Lindbäck <leifl@kth.se>.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package se.kth.id1212.appserv.bank.view;

import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import se.kth.id1212.appserv.bank.controller.CashierFacade;
import se.kth.id1212.appserv.bank.model.AccountDTO;

/**
 * Handles all interaction with the account JSF page.
 */
@Named("acctManager")
@ConversationScoped
public class AcctManager implements Serializable {
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
     * Withdraws the amount set by the latest call to
     * <code>setTransactionAmount</code> from the account specified by
     * <code>currentAcct.getAcctNo()</code>.
     */
    public void withdraw() {
        try {
            transactionFailure = null;
            cashierFacade.withdraw(currentAcct.getAcctNo(), transactionAmount);
            readAcctData();
        } catch (Exception e) {
            handleException(e);
        }
    }

    /**
     * Searches for the account specified by the latest call to
     * <code>setSearchedAcct</code>.
     */
    public void findAccount() {
        try {
            startConversation();
            transactionFailure = null;
            currentAcct = cashierFacade.findAccount(searchedAcct);
        } catch (Exception e) {
            handleException(e);
        }
    }

    /**
     * Deposits the amount set by the latest call to
     * <code>setTransactionAmount</code> from the account specified by
     * <code>currentAcct.getAcctNo()</code>.
     */
    public void deposit() {
        try {
            transactionFailure = null;
            cashierFacade.deposit(currentAcct.getAcctNo(), transactionAmount);
            readAcctData();
        } catch (Exception e) {
            handleException(e);
        }
    }

    /**
     * Creates a new account. The holder's name is specified by the latest calls
     * to
     * <code>setNewAccountHolderFirstName</code> and
     * <code>setNewAccountHolderLastName</code>. The initial balance is
     * specified by the latest call to
     * <code>setNewAccountBalance</code>.
     */
    public void createAccount() {
        try {
            startConversation();
            transactionFailure = null;
            currentAcct = cashierFacade.createAccount(newAccountHolderFirstName,
                    newAccountHolderLastName, newAccountBalance);
        } catch (Exception e) {
            handleException(e);
        }
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