package com.readboy.ssm.po;

import java.util.List;

/**
 * 对应我的客户详情页面
 * 包含 基本信息，关联人信息和持有产品，其中持有产品包含存款、贷款、手机银行和ETC四部分
 *
 */
public class CustomerDetail {

	private Customer customer;

	private List<RelatedPerson> relatedPerson;

	private DepositAccountFamily depositAccountFamily;
	private List<DepositAccountPerson> depositAccountPerson;

	private LoanCustomerProduct loanCustomerProduct;
	private List<LoanCustomerPerson> loanCustomerPerson;
//	private List<LoanCustomerFamily> loanCustomerFamily;

	private CellBankCustomerProduct cellBankCustomerProduct;
	private List<CellBankCustomerPerson> cellBankCustomerPerson;

	private EtcCustomerFamily etcCustomerFamily;


	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public List<RelatedPerson> getRelatedPerson() {
		return relatedPerson;
	}

	public void setRelatedPerson(List<RelatedPerson> relatedPerson) {
		this.relatedPerson = relatedPerson;
	}

	public DepositAccountFamily getDepositAccountFamily() {
		return depositAccountFamily;
	}

	public void setDepositAccountFamily(
			DepositAccountFamily depositAccountFamily) {
		this.depositAccountFamily = depositAccountFamily;
	}

	public List<DepositAccountPerson> getDepositAccountPerson() {
		return depositAccountPerson;
	}

	public void setDepositAccountPerson(
			List<DepositAccountPerson> depositAccountPerson) {
		this.depositAccountPerson = depositAccountPerson;
	}

	
	
//	public List<LoanCustomerFamily> getLoanCustomerFamily() {
//		return loanCustomerFamily;
//	}
//
//	public void setLoanCustomerFamily(List<LoanCustomerFamily> loanCustomerFamily) {
//		this.loanCustomerFamily = loanCustomerFamily;
//	}

	public LoanCustomerProduct getLoanCustomerProduct() {
		return loanCustomerProduct;
	}

	public void setLoanCustomerProduct(LoanCustomerProduct loanCustomerProduct) {
		this.loanCustomerProduct = loanCustomerProduct;
	}

	public List<LoanCustomerPerson> getLoanCustomerPerson() {
		return loanCustomerPerson;
	}

	public void setLoanCustomerPerson(List<LoanCustomerPerson> loanCustomerPerson) {
		this.loanCustomerPerson = loanCustomerPerson;
	}

	public CellBankCustomerProduct getCellBankCustomerProduct() {
		return cellBankCustomerProduct;
	}

	public void setCellBankCustomerPerson(
			List<CellBankCustomerPerson> cellBankCustomerPerson) {
		this.cellBankCustomerPerson = cellBankCustomerPerson;
	}

	public void setCellBankCustomerProduct(
			CellBankCustomerProduct cellBankCustomerProduct) {
		this.cellBankCustomerProduct = cellBankCustomerProduct;
	}

	public List<CellBankCustomerPerson> getCellBankCustomerPerson() {
		return cellBankCustomerPerson;
	}

	public EtcCustomerFamily getEtcCustomerFamily() {
		return etcCustomerFamily;
	}

	public void setEtcCustomerFamily(EtcCustomerFamily etcCustomerFamily) {
		this.etcCustomerFamily = etcCustomerFamily;
	}


}
