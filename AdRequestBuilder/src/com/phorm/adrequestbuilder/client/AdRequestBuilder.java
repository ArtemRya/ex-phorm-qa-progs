package com.phorm.adrequestbuilder.client;

import java.util.List;

import com.extjs.gxt.ui.client.GXT;
import com.extjs.gxt.ui.client.Style;
import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.Style.Orientation;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.FieldEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.util.Theme;
import com.extjs.gxt.ui.client.widget.Header;
import com.extjs.gxt.ui.client.widget.HtmlContainer;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.TabPanel;
import com.extjs.gxt.ui.client.widget.Text;
import com.extjs.gxt.ui.client.widget.Viewport;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.LabelField;
import com.extjs.gxt.ui.client.widget.form.SimpleComboBox;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.AccordionLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.layout.CenterLayout;
import com.extjs.gxt.ui.client.widget.layout.ColumnLayout;
import com.extjs.gxt.ui.client.widget.layout.FillLayout;
import com.extjs.gxt.ui.client.widget.layout.FitData;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.HBoxLayout;
import com.extjs.gxt.ui.client.widget.layout.HBoxLayoutData;
import com.extjs.gxt.ui.client.widget.layout.RowData;
import com.extjs.gxt.ui.client.widget.layout.RowLayout;
import com.extjs.gxt.ui.client.widget.layout.VBoxLayout;
import com.extjs.gxt.ui.client.widget.layout.VBoxLayout.VBoxLayoutAlign;
import com.extjs.gxt.ui.client.widget.layout.VBoxLayoutData;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.storage.client.Storage;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;

public class AdRequestBuilder implements EntryPoint {
	private final RequestServiceAsync requestServer = GWT.create(RequestService.class);
	private String requestParameters = "";
	public static String clientCookie;
	
	public static Text txtUID;
	public static String host;
	public static String scheme;
	public static String adService;
	
	public static TextField txtRequestParameters;
	
	public void onModuleLoad() {
		GXT.setDefaultTheme(Theme.GRAY, true);
		
		Viewport viewport = new Viewport();
		viewport.setLayout(new FitLayout());
		
		txtRequestParameters = new TextField();
		txtRequestParameters.setTabIndex(100);
		
		final TabPanel tabPanel = new TabPanel();
				
		final LayoutContainer layoutContainer = new LayoutContainer();
		layoutContainer.setLayout(new BorderLayout());

		final LayoutContainer layoutContainer_1 = new LayoutContainer();
		layoutContainer_1.setLayout(new AccordionLayout());

		final FormPanel frmpnlNewFormpanel = new FormPanel();
		frmpnlNewFormpanel.setHeading("Request for Advertising");
		frmpnlNewFormpanel.setCollapsible(true);
		VBoxLayout vbl_frmpnlNewFormpanel = new VBoxLayout();
		vbl_frmpnlNewFormpanel.setExtraStyle("myTextStyle");
		vbl_frmpnlNewFormpanel.setVBoxLayoutAlign(VBoxLayoutAlign.CENTER);
		frmpnlNewFormpanel.setLayout(vbl_frmpnlNewFormpanel);

		final CustomRequestsMainForm customRequests = new CustomRequestsMainForm();
		
		final AdvertisingRequest adRequest = new AdvertisingRequest();

		final Button btnNewButton_4 = new Button("Advertising Request");
		frmpnlNewFormpanel.add(btnNewButton_4);
		btnNewButton_4.addListener(Events.Select, new Listener<ButtonEvent>() {
			public void handleEvent(ButtonEvent e) {
				tabPanel.add(adRequest);
				tabPanel.setSelection(adRequest);
			}
		});
		
		final EnvAutoRequests automationRequests = new EnvAutoRequests();
		
		Button btnAutomationRequests = new Button("Requests from Test Environment");
		btnAutomationRequests.addListener(Events.Select, new Listener<ButtonEvent>() {
			public void handleEvent(ButtonEvent e) {
				tabPanel.add(automationRequests);
				tabPanel.setSelection(automationRequests);
			}
		});
		frmpnlNewFormpanel.add(btnAutomationRequests, new VBoxLayoutData(10, 0, 0, 0));
		
		final Button btnNewButton_2 = new Button("Custom Requests");
				
		btnNewButton_2.addListener(Events.Select, new Listener<ButtonEvent>() {
			public void handleEvent(ButtonEvent e) {
				tabPanel.add(customRequests);
				tabPanel.setSelection(customRequests);
			}
		});
				
		frmpnlNewFormpanel.add(btnNewButton_2, new VBoxLayoutData(10, 0, 10, 0));
		layoutContainer_1.add(frmpnlNewFormpanel);

		final FormPanel frmpnlNewFormpanel_1 = new FormPanel();
		frmpnlNewFormpanel_1.setHeading("Click Request");
		frmpnlNewFormpanel_1.setCollapsible(true);
		frmpnlNewFormpanel_1.setLayout(new VBoxLayout());
		layoutContainer_1.add(frmpnlNewFormpanel_1);

		final FormPanel frmpnlNewFormpanel_2 = new FormPanel();
		frmpnlNewFormpanel_2.setHeading("Action Request");
		frmpnlNewFormpanel_2.setCollapsible(true);
		layoutContainer_1.add(frmpnlNewFormpanel_2);

		final FormPanel frmpnlNewFormpanel_3 = new FormPanel();
		frmpnlNewFormpanel_3.setHeading("Pixel Tracking Request");
		frmpnlNewFormpanel_3.setCollapsible(true);
		layoutContainer_1.add(frmpnlNewFormpanel_3);

		final FormPanel frmpnlNewFormpanel_4 = new FormPanel();
		frmpnlNewFormpanel_4.setHeading("OptOut Request");
		frmpnlNewFormpanel_4.setCollapsible(true);
		layoutContainer_1.add(frmpnlNewFormpanel_4);

		final FormPanel frmpnlNewFormpanel_5 = new FormPanel();
		frmpnlNewFormpanel_5.setHeading("Discover and User Data Requests");
		frmpnlNewFormpanel_5.setCollapsible(true);
		layoutContainer_1.add(frmpnlNewFormpanel_5);

		final FormPanel frmpnlNewFormpanel_6 = new FormPanel();
		frmpnlNewFormpanel_6.setHeading("Passback Request");
		frmpnlNewFormpanel_6.setCollapsible(true);
		layoutContainer_1.add(frmpnlNewFormpanel_6);

		final FormPanel frmpnlNewFormpanel_7 = new FormPanel();
		frmpnlNewFormpanel_7.setHeading("Passback Pixel Request");
		frmpnlNewFormpanel_7.setCollapsible(true);
		layoutContainer_1.add(frmpnlNewFormpanel_7);

		final FormPanel frmpnlNewFormpanel_8 = new FormPanel();
		frmpnlNewFormpanel_8.setHeading("Request for dynamic creative content");
		frmpnlNewFormpanel_8.setCollapsible(true);
		layoutContainer_1.add(frmpnlNewFormpanel_8);

		final FormPanel frmpnlNewFormpanel_9 = new FormPanel();
		frmpnlNewFormpanel_9.setHeading("WebStat Request");
		frmpnlNewFormpanel_9.setCollapsible(true);
		layoutContainer_1.add(frmpnlNewFormpanel_9);
		BorderLayoutData bld_layoutContainer_1 = new BorderLayoutData(
				LayoutRegion.WEST, 220.0f);
		bld_layoutContainer_1.setSplit(true);
		bld_layoutContainer_1.setMinSize(200);
		layoutContainer.add(layoutContainer_1, bld_layoutContainer_1);
		layoutContainer_1.setBorders(true);

		final LayoutContainer layoutContainer_2 = new LayoutContainer();
		layoutContainer_2.setLayout(new RowLayout(Orientation.VERTICAL));

		final LayoutContainer layoutContainer_6 = new LayoutContainer();

		Header header = new Header();
		header.setTextStyle(".myHeader");
		header.setText("AdServer Request Builder");
		layoutContainer_6.add(header, new FitData(3));
		header.setSize("150px", "35px");
		layoutContainer_6.setLayout(new FitLayout());
		layoutContainer_2.add(layoutContainer_6, new RowData(Style.DEFAULT,
				40.0, new Margins()));
		layoutContainer_6.setBorders(true);

		LayoutContainer layoutContainer_7 = new LayoutContainer();
		layoutContainer_7.setLayout(new HBoxLayout());

		LabelField lblfldScheme = new LabelField("Scheme");
		layoutContainer_7.add(lblfldScheme, new HBoxLayoutData(5, 5, 5, 5));

		final SimpleComboBox<String> cmbScheme = new SimpleComboBox<String>();
		layoutContainer_7.add(cmbScheme, new HBoxLayoutData(5, 5, 5, 5));
		cmbScheme.setWidth("80");
		cmbScheme.add("HTTP");
		cmbScheme.add("HTTPS");
		cmbScheme.setSimpleValue("HTTP");
		cmbScheme.setFieldLabel("New SimpleComboBox");
		cmbScheme.setTriggerAction(TriggerAction.ALL);
		scheme = "HTTP";
		
		final LabelField lblfldNewLabelfield_2 = new LabelField(
				"Ad Server Host");
		layoutContainer_7.add(lblfldNewLabelfield_2, new HBoxLayoutData(5, 5, 5, 5));
		lblfldNewLabelfield_2.setHeight(20);
		
		final SimpleComboBox<String> cmbAdServerHost = new SimpleComboBox<String>();
		cmbAdServerHost.addListener(Events.Select, new Listener<FieldEvent>() {
			public void handleEvent(FieldEvent e) {
				host = cmbAdServerHost.getRawValue();
				clientCookie = Utilities.GetCookie(host);
				if (clientCookie != null) {
					txtUID.setText(Utilities.GetUID(clientCookie));
				} else txtUID.setText("");
			}
		});
		
		cmbAdServerHost.add("a.oix-rubytest.net");
		cmbAdServerHost.add("a.oix-stage.com");
		cmbAdServerHost.setWidth("200");
		//cmbAdServerHost.setTypeAhead(true);
		cmbAdServerHost.setFieldLabel("New SimpleComboBox");
		cmbAdServerHost.setTriggerAction(TriggerAction.ALL);
		cmbAdServerHost.setSimpleValue("a.oix-rubytest.net");
		host = "a.oix-rubytest.net";
	
		layoutContainer_7.add(cmbAdServerHost, new HBoxLayoutData(5, 5, 5, 5));

		final LabelField lblfldNewLabelfield_3 = new LabelField("Ad Service");
		layoutContainer_7.add(lblfldNewLabelfield_3, new HBoxLayoutData(5, 5, 5, 5));
		lblfldNewLabelfield_3.setHeight(20);

		final SimpleComboBox<String> cmbAdService = new SimpleComboBox<String>();
		layoutContainer_7.add(cmbAdService, new HBoxLayoutData(5, 5, 5, 5));
		cmbAdService.setWidth("200");
		cmbAdService.setFieldLabel("New SimpleComboBox");
		cmbAdService.add("/services/nslookup");
		cmbAdService.setSimpleValue("/services/nslookup");
		cmbAdService.setTriggerAction(TriggerAction.ALL);
		adService = "/services/nslookup";
		
		layoutContainer_2.add(layoutContainer_7, new RowData(Style.DEFAULT,
				35.0, new Margins()));
		layoutContainer_7.setBorders(true);
		
		LayoutContainer layoutContainer_10 = new LayoutContainer();
		layoutContainer_10.setLayout(new ColumnLayout());
		
		LabelField lblfldSigneduid = new LabelField("Signed_uid   ");
		layoutContainer_10.add(lblfldSigneduid);
		lblfldSigneduid.setSize("70", "20");
		
		txtUID = new Text("");
		layoutContainer_10.add(txtUID);
		txtUID.setSize("650","20");
		
		Button btnNewButton_5 = new Button("Reset UID");
		btnNewButton_5.addListener(Events.Select, new Listener<ButtonEvent>() {
			public void handleEvent(ButtonEvent e) {
				String host = cmbAdServerHost.getRawValue();
				Utilities.RemoveCookie(host);
				txtUID.setText("");
			}
		});
		layoutContainer_10.add(btnNewButton_5);
		
		HBoxLayoutData hbld_txtfldNewTextfield = new HBoxLayoutData(5, 5, 5, 5);
		hbld_txtfldNewTextfield.setFlex(9.0);
		layoutContainer_2.add(layoutContainer_10, new RowData(Style.DEFAULT, 35.0, new Margins(5, 5, 5, 5)));
		layoutContainer.add(layoutContainer_2, new BorderLayoutData(LayoutRegion.NORTH, 110.0f));
		layoutContainer_2.setBorders(true);

		final TabItem tabResult = new TabItem("Result");
		tabResult.setLayout(new FitLayout());

		ScrollPanel scrollPanel = new ScrollPanel();
		tabResult.add(scrollPanel);
		scrollPanel.setHeight("100%");

		final HtmlContainer txtResult = new HtmlContainer("");
		scrollPanel.setWidget(txtResult);
		txtResult.setSize("", "520px");
		tabPanel.add(tabResult);
		BorderLayoutData bld_tabPanel = new BorderLayoutData(LayoutRegion.CENTER);
		bld_tabPanel.setSplit(true);

		layoutContainer.add(tabPanel, bld_tabPanel);

		final LayoutContainer layoutContainer_3 = new LayoutContainer();
		layoutContainer_3.setLayout(new FillLayout(Orientation.VERTICAL));

		final LayoutContainer layoutContainer_4 = new LayoutContainer();
		layoutContainer_4.setLayout(new HBoxLayout());

		final LabelField lblfldNewLabelfield_1 = new LabelField("Request String");
		layoutContainer_4.add(lblfldNewLabelfield_1, new HBoxLayoutData(3, 0,
				0, 0));

		HBoxLayoutData hbld_txtRequestParameters = new HBoxLayoutData(3, 0, 0,
				0);
		hbld_txtRequestParameters.setFlex(7.0);
		layoutContainer_4.add(txtRequestParameters, hbld_txtRequestParameters);
		txtRequestParameters.setFieldLabel("New TextField");
		layoutContainer_3.add(layoutContainer_4);
		layoutContainer_4.setBorders(true);

		final LayoutContainer layoutContainer_5 = new LayoutContainer();
		layoutContainer_5.setLayout(new CenterLayout());
		
		viewport.add(layoutContainer, new FitData(0, 0, 10, 0));
		
		final Button btnNewButton_3 = new Button("Execute Request");
		btnNewButton_3.addListener(Events.Select, new Listener<ButtonEvent>() {
			public void handleEvent(ButtonEvent e) {
				txtResult.removeAll();
				requestParameters = txtRequestParameters.getRawValue();
				sendRequestToServer();
				tabPanel.setSelection(tabResult);
			}

			private void sendRequestToServer() {
				String method = "GET";
				
				txtResult.setHtml("");

				clientCookie = Utilities.GetCookie(host);

				requestServer.executeRequest(scheme, host, adService, requestParameters, method, clientCookie, new AsyncCallback<List<String>>() {
					public void onFailure(Throwable caught) {
					txtResult.setHtml(caught.getLocalizedMessage());
					}

					@Override
					public void onSuccess(List<String> result) {
						txtResult.setHtml(result.get(1));
						String tmpCookie = result.get(0);
						if (!tmpCookie.isEmpty()) {
							txtUID.setText(Utilities.GetUID(tmpCookie));
							Utilities.SetCookie(host, tmpCookie);
						}
					}
				});
			}
		});

		layoutContainer_5.add(btnNewButton_3);
		layoutContainer_3.add(layoutContainer_5);
		layoutContainer_5.setBorders(true);
		layoutContainer.add(layoutContainer_3, new BorderLayoutData(LayoutRegion.SOUTH, 65.0f));
		layoutContainer_3.setBorders(true);
		
		layoutContainer.setSize("747px", "609px");
		viewport.setSize("770px", "560px");
		
		RootPanel rootPanel = RootPanel.get();
		rootPanel.add(viewport);
		rootPanel.setWidgetPosition(viewport, 0, 0);
		
		txtUID.setText(Utilities.GetUID(Utilities.GetCookie(host)));
	}
}
