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
package se.kth.id1212.appserv.bank.integration;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import se.kth.id1212.appserv.bank.model.Account;

/**
 * Handles all interaction with the entity manager. No code outside of this class, except for the
 * JPA entities, shall haver anything to do with JPA.
 */
@TransactionAttribute(TransactionAttributeType.MANDATORY)
@Stateless
public class BankDAO {
    @PersistenceContext(unitName = "bankPU")
    private EntityManager em;

    /**
     * Searches for the account with the specified account number.
     *
     * @param acctNo The number of the searched account.
     * @return The account with the specified number, if such an account was found.
     * @throws EntityNotFoundException If no account with the specified number was found.
     */
    public Account findAccountByAcctNo(int acctNo) {
        Account account = em.find(Account.class, acctNo);
        if (account == null) {
            throw new EntityNotFoundException("No account with number " + acctNo);
        }
        return account;
    }
    
    /**
     * Stores the specified account in the database.
     * @param acct The account to store.
     */
    public void storeAccount(Account acct) {
        em.persist(acct);
    }

}
