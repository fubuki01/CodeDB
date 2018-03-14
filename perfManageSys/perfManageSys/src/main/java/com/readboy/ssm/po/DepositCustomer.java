package com.readboy.ssm.po;

import java.util.List;

public class DepositCustomer {
	private List<DepositAccountFamily> depositAccountFamilys;
	private List<DepositAccountPerson> depositAccountPersons;
	public List<DepositAccountFamily> getDepositAccountFamilys() {
		return depositAccountFamilys;
	}
	public void setDepositAccountFamilys(
			List<DepositAccountFamily> depositAccountFamilys) {
		this.depositAccountFamilys = depositAccountFamilys;
	}
	public List<DepositAccountPerson> getDepositAccountPersons() {
		return depositAccountPersons;
	}
	public void setDepositAccountPersons(
			List<DepositAccountPerson> depositAccountPersons) {
		this.depositAccountPersons = depositAccountPersons;
	}

	
}
