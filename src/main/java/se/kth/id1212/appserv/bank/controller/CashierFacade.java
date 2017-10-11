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
package se.kth.id1212.appserv.bank.controller;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import se.kth.id1212.appserv.bank.model.Account;
import se.kth.id1212.appserv.bank.model.AccountDTO;
import se.kth.id1212.appserv.bank.model.OverdraftException;

/**
 * A controller. All calls to the model that are executed because of an action taken by the cashier
 * pass through here.
 */
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Stateless
public class CashierFacade {
    @PersistenceContext(unitName = "bankPU")
    private EntityManager em;

    /**
     * Creates a new account with the specified data.
     *
     * @param firstName Holder's first name.
     * @param lastName  Holder's last name.
     * @param balance   Initial balance.
     */
    public AccountDTO createAccount(String firstName, String lastName, int balance) {
        Account newAcct = new Account(balance, firstName, lastName);
        em.persist(newAcct);
        return newAcct;
    }

    /**
     * Search for the specified account.
     *
     * @param acctNo The account number of the searched account.
     * @return The account if it was found.
     * @throws EntityNotFoundException If the account was not found.
     */
    public AccountDTO findAccount(int acctNo) {
        AccountDTO found = em.find(Account.class, acctNo);
        if (found == null) {
            throw new EntityNotFoundException("No account with number " + acctNo);
        }
        return found;
    }

    /**
     * Withdraws the specified amount.
     *
     * @param amount The amount to withdraw.
     * @throws OverdraftException If withdrawal would result in a negative balance.
     */
    public void withdraw(int acctNo, int amount) throws OverdraftException {
        Account acct = em.find(Account.class, acctNo);
        acct.withdraw(amount);
    }

    /**
     * Deposits the specified amount.
     *
     * @param amount The amount to deposit.
     */
    public void deposit(int acctNo, int amount) {
        Account acct = em.find(Account.class, acctNo);
        acct.deposit(amount);
    }

}
