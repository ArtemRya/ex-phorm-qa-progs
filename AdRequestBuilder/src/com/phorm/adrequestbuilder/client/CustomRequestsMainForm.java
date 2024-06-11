package com.phorm.adrequestbuilder.client;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.layout.AccordionLayout;

public class CustomRequestsMainForm extends TabItem {
	public CustomRequestsMainForm() {
		setFiresEvents(true);
		setClosable(true);
		setText("Custom Requests");
		setLayout(new AccordionLayout());
		
		//OIX Korean Commission Test Plan
		ContentPanel koreanCommission = new CustomRequests("koreanCommission", "KoreanCommission.xml");
		koreanCommission.setHeading("OIX Korean Commission Test Plan");
		add(koreanCommission);
		//OIX Fraud Billing Test Plan
		ContentPanel fraudCondition = new CustomRequests("fraudBilling", "FraudBilling.xml");
		fraudCondition.setHeading("OIX Fraud Billing Test Plan");
		add(fraudCondition);
		//OIX Oracle Financials Test Plan
	}
}
