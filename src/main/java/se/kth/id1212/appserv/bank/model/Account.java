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
package se.kth.id1212.appserv.bank.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * A persistent representation of an account.
 */
@Entity
public class Account implements AccountDTO, Serializable {

    private static final long serialVersionUID = 16247164401L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int acctNo;
    private int balance;
    private String firstName;
    private String lastName;

    /**
     * Creates a new instance of Account
     */
    public Account() {
    }

    /**
     * Creates a new instance of Account
     */
    public Account(int balance, String firstName, String lastName) {
        this.balance = balance;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * Get the value of lastNAme
     *
     * @return the value of lastNAme
     */
    @Override
    public String getLastName() {
        return lastName;
    }

    /**
     * Get the value of firstName
     *
     * @return the value of firstName
     */
    @Override
    public String getFirstName() {
        return firstName;
    }

    /**
     * Get the value of balance
     *
     * @return the value of balance
     */
    @Override
    public int getBalance() {
        return balance;
    }

    /**
     * Get the value of account number.
     *
     * @return the value of account number.
     */
    @Override
    public int getAcctNo() {
        return acctNo;
    }

    /**
     * Withdraws the specified amount.
     *
     * @param amount The amount to withdraw.
     * @throws OverdraftException If withdrawal would result in a negative balance.
     */
    public void withdraw(int amount) throws OverdraftException {
        if (amount > balance) {
            throw new OverdraftException(
                    "Overdraft attempt, balance: " + balance + ", amount: " + amount);
        }
        balance = balance - amount;
    }

    /**
     * Deposits the specified amount.
     *
     * @param amount The amount to deposit.
     */
    public void deposit(int amount) {
        balance = balance + amount;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        return new Integer(acctNo).hashCode();
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Account)) {
            return false;
        }
        Account other = (Account) object;
        return this.acctNo == other.acctNo;
    }

    @Override
    public String toString() {
        return "bank.model.Account[id=" + acctNo + "]";
    }
}
