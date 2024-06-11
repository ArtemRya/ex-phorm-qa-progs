package com.phorm.adrequestbuilder.client;

import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.ProgressBar;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.layout.AccordionLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.widget.layout.CenterLayout;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.widget.layout.VBoxLayout;
import com.extjs.gxt.ui.client.widget.form.LabelField;
import com.extjs.gxt.ui.client.widget.layout.AbsoluteLayout;
import com.extjs.gxt.ui.client.widget.layout.AbsoluteData;
import com.extjs.gxt.ui.client.Style;
import com.extjs.gxt.ui.client.widget.form.NumberField;
import com.extjs.gxt.ui.client.widget.layout.RowLayout;
import com.extjs.gxt.ui.client.Style.Orientation;
import com.extjs.gxt.ui.client.widget.layout.RowData;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.layout.HBoxLayout;
import com.extjs.gxt.ui.client.widget.layout.HBoxLayoutData;
import com.extjs.gxt.ui.client.widget.layout.FillLayout;
import com.extjs.gxt.ui.client.widget.form.CheckBox;
import com.google.gwt.user.client.Timer;

public class EnvAutoRequests extends TabItem {
	public EnvAutoRequests() {
		setFiresEvents(true);
		setClosable(true);
		setText("Automation Requests from Test Environment");
		setLayout(new BorderLayout());
		
		LayoutContainer layoutContainer = new LayoutContainer();
		layoutContainer.setLayout(new AccordionLayout());
		
		//OIX Korean Commission Test Plan
		//ContentPanel koreanCommission = new TestAutoRequests("koreanCommission", "KoreanCommission.xml");
		//koreanCommission.setHeading("OIX Korean Commission Test Plan");
		//add(koreanCommission);
		
		//OIX Fraud Billing Test Plan
		//ContentPanel fraudCondition = new TestAutoRequests("fraudBilling", "FraudBilling.xml");
		//fraudCondition.setHeading("OIX Fraud Billing Test Plan");
		//add(fraudCondition);
		
		//OIX Oracle Financials Test Plan
		ContentPanel oracleFinance = new TestAutoRequests("oracleFinancials", "OracleFinancials.xml");
		layoutContainer.add(oracleFinance);
		oracleFinance.setHeading("OIX Oracle Financials Test Plan");
		add(layoutContainer, new BorderLayoutData(LayoutRegion.CENTER));
		layoutContainer.setBorders(true);
		
		LayoutContainer layoutContainer_1 = new LayoutContainer();
		layoutContainer_1.setLayout(new FillLayout(Orientation.VERTICAL));
		
		LayoutContainer layoutContainer_2 = new LayoutContainer();
		layoutContainer_2.setLayout(new HBoxLayout());
		
		LabelField lblfldNewLabelfield = new LabelField("Timeout between requests (ms)");
		layoutContainer_2.add(lblfldNewLabelfield, new HBoxLayoutData(5, 5, 5, 5));
		
		final NumberField timeout = new NumberField();
		layoutContainer_2.add(timeout, new HBoxLayoutData(5, 5, 5, 5));
		timeout.setFieldLabel("New NumberField");
		
		CheckBox chckbxNewCheckbox = new CheckBox();
		chckbxNewCheckbox.setValue(true);
		chckbxNewCheckbox.setName("Stop on Error");
		layoutContainer_2.add(chckbxNewCheckbox, new HBoxLayoutData(7, 5, 5, 5));
		chckbxNewCheckbox.setBoxLabel("Stop on Error");
		chckbxNewCheckbox.setHideLabel(true);
		timeout.setValue(2000);
		layoutContainer_1.add(layoutContainer_2);
		layoutContainer_2.setBorders(true);
		
		LayoutContainer layoutContainer_3 = new LayoutContainer();
		layoutContainer_3.setLayout(new CenterLayout());
		
		Button btnNewButton = new Button("Run Selected Requests");
		layoutContainer_3.add(btnNewButton);
		btnNewButton.addListener(Events.Select, new Listener<ButtonEvent>() {
			public void handleEvent(ButtonEvent e) {
				TestAutoRequests.timeout = Integer.parseInt(timeout.getRawValue());
				TestAutoRequests.btnNewButton.fireEvent(Events.Select);
			}
		});
		layoutContainer_1.add(layoutContainer_3);
		layoutContainer_3.setBorders(true);
		add(layoutContainer_1, new BorderLayoutData(LayoutRegion.SOUTH, 80.0f));
		layoutContainer_1.setBorders(true);
	}
}
