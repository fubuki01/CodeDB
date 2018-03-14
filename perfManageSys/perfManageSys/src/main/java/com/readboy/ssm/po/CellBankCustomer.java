package com.readboy.ssm.po;

import java.util.List;


public class CellBankCustomer {
	private List<CellBankCustomerPerson> cellBankCustomerPersons;
	private List<CellBankCustomerProduct> cellBankCustomerProducts;
	
	public List<CellBankCustomerPerson> getCellBankCustomerPersons() {
		return cellBankCustomerPersons;
	}
	public void setCellBankCustomerPersons(
			List<CellBankCustomerPerson> cellBankCustomerPersons) {
		this.cellBankCustomerPersons = cellBankCustomerPersons;
	}
	public List<CellBankCustomerProduct> getCellBankCustomerProducts() {
		return cellBankCustomerProducts;
	}
	public void setCellBankCustomerProducts(
			List<CellBankCustomerProduct> cellBankCustomerProducts) {
		this.cellBankCustomerProducts = cellBankCustomerProducts;
	}
	
}
