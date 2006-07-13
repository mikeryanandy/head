package org.mifos.application.customer.group.business;

import org.mifos.application.customer.business.CustomerPerformanceHistory;
import org.mifos.framework.business.PersistentObject;
import org.mifos.framework.exceptions.PersistenceException;
import org.mifos.framework.exceptions.ServiceException;
import org.mifos.framework.util.helpers.Money;

import com.sun.org.apache.bcel.internal.generic.GETFIELD;

public class GroupPerformanceHistoryEntity extends CustomerPerformanceHistory {

	private Integer id;

	private Integer clientCount;

	private Money lastGroupLoanAmount;

	private Money avgLoanForMember;

	private Money totalOutstandingPortfolio;

	private Money totalSavings;

	private Money portfolioAtRisk;

	private GroupBO group;
	
	public GroupPerformanceHistoryEntity(){}
	
	public GroupPerformanceHistoryEntity(Integer clientCount,
			Money lastGroupLoanAmount, Money avgLoanForMember,
			Money totalOutstandingPortfolio, Money totalSavings,
			Money portfolioAtRisk) {
		this.portfolioAtRisk=portfolioAtRisk;
		this.totalOutstandingPortfolio=totalOutstandingPortfolio;
		this.totalSavings=totalSavings;
		this.avgLoanForMember=avgLoanForMember;
		this.lastGroupLoanAmount=lastGroupLoanAmount;
		this.clientCount=clientCount;
	}

	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	private Money getAvgLoanForMember() {
		return avgLoanForMember;
	}

	private void setAvgLoanForMember(Money avgLoanForMember) {
		this.avgLoanForMember = avgLoanForMember;
	}
	
	public Money getAvgLoanAmountForMember() throws PersistenceException, ServiceException {
		return getGroup().getAverageLoanAmount();
	}


	public Integer getClientCount() {
		return clientCount;
	}

	public void setClientCount(Integer clientCount) {
		this.clientCount = clientCount;
	}

	public Money getLastGroupLoanAmount() {
		return lastGroupLoanAmount;
	}

	public void setLastGroupLoanAmount(Money lastGroupLoanAmount) {
		this.lastGroupLoanAmount = lastGroupLoanAmount;
	}

	private Money getTotalOutstandingPortfolio(){
		return totalOutstandingPortfolio;
	}
	
	private void setTotalOutstandingPortfolio(Money totalOutstandingPortfolio) {
		this.totalOutstandingPortfolio = totalOutstandingPortfolio;
	}
	
	public Money getTotalOutStandingLoanAmount() throws PersistenceException, ServiceException{
		return getGroup().getTotalOutStandingLoanAmount();
	}

	public Money getPortfolioAtRisk() {
		return portfolioAtRisk;
	}

	public void setPortfolioAtRisk(Money portfolioAtRisk) {
		this.portfolioAtRisk = portfolioAtRisk;
	}

	private Money getTotalSavings() {
		return totalSavings;
	}
	
	public Money getTotalSavingsAmount() throws PersistenceException, ServiceException{
		return getGroup().getTotalSavingsBalance();
	}

	private void setTotalSavings(Money totalSavings) {
		this.totalSavings = totalSavings;
	}

	public GroupBO getGroup() {
		return group;
	}

	public void setGroup(GroupBO group) {
		this.group = group;
	}
}
