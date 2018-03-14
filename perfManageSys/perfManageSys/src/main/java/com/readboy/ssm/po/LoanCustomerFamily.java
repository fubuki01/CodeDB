package com.readboy.ssm.po;

import java.util.List;

/**
 * 贷款中，一个客户对应多个合同，一个合同对应多个管户人；一个客户对应多个管户列表
 * @author Administrator
 *
 */
public class LoanCustomerFamily {
	private String hth;		//合同号
	private List<LoanCustomerPerson> loanCustomerPerson;
	private LoanCustomerProduct loanCustomerProduct;
	
	public String getHth() {
		return hth;
	}
	public void setHth(String hth) {
		this.hth = hth;
	}
	public List<LoanCustomerPerson> getLoanCustomerPerson() {
		return loanCustomerPerson;
	}
	public void setLoanCustomerPerson(List<LoanCustomerPerson> loanCustomerPerson) {
		this.loanCustomerPerson = loanCustomerPerson;
	}
	public LoanCustomerProduct getLoanCustomerProduct() {
		return loanCustomerProduct;
	}
	public void setLoanCustomerProduct(
			LoanCustomerProduct loanCustomerProduct) {
		this.loanCustomerProduct = loanCustomerProduct;
	}
	
}
