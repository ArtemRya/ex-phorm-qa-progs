package com.phorm.adrequestbuilder.client;

import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.FieldEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionChangedEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.util.DateWrapper;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.CheckBox;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.extjs.gxt.ui.client.widget.form.DateField;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.LabelField;
import com.extjs.gxt.ui.client.widget.form.NumberField;
import com.extjs.gxt.ui.client.widget.form.SimpleComboBox;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.form.TimeField;
import com.extjs.gxt.ui.client.widget.layout.AccordionLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.layout.CenterLayout;
import com.extjs.gxt.ui.client.widget.layout.TableLayout;
import com.phorm.adrequestbuilder.data.Country;
import com.phorm.adrequestbuilder.data.TestData;

public class AdvertisingRequest extends TabItem {
	private String requestParameters = "";
	private String countryCode = "";
	private String debugDateTime = "";
	
	public AdvertisingRequest() {
		setFiresEvents(true);
		setClosable(true);
		setText("Advertising Request");
		setLayout(new BorderLayout());
		
		LayoutContainer layoutContainer_8 = new LayoutContainer();
		layoutContainer_8.setLayout(new CenterLayout());

		add(layoutContainer_8, new BorderLayoutData(LayoutRegion.SOUTH, 35.0f));
		layoutContainer_8.setBorders(true);
		
		LayoutContainer layoutContainer_9 = new LayoutContainer();
		layoutContainer_9.setLayout(new AccordionLayout());

		FormPanel tabAdvertisingRequest = new FormPanel();
		tabAdvertisingRequest.setLabelWidth(100);
		tabAdvertisingRequest.setHeading("Main Parameters");
		tabAdvertisingRequest.setCollapsible(true);
		TableLayout tl_tabAdvertisingRequest = new TableLayout(3);
		tl_tabAdvertisingRequest.setCellSpacing(4);
		tabAdvertisingRequest.setLayout(tl_tabAdvertisingRequest);

		LabelField lblfldTid = new LabelField("tid");
		tabAdvertisingRequest.add(lblfldTid);

		final NumberField txtTid = new NumberField();
		tabAdvertisingRequest.add(txtTid);
		txtTid.setFieldLabel("New NumberField");
		txtTid.setValue(209773);

		LabelField lblfldTagIdunsigned = new LabelField(
				"Tag ID (unsigned number). For PageSense (PS) client is set as a parameter for JavaScript tag provided to a WEB publisher.");
		tabAdvertisingRequest.add(lblfldTagIdunsigned);

		LabelField lblfldNewLabelfield_4 = new LabelField("tag.inv");
		tabAdvertisingRequest.add(lblfldNewLabelfield_4);

		final CheckBox chkTagInv = new CheckBox();
		tabAdvertisingRequest.add(chkTagInv);
		chkTagInv.setBoxLabel("");
		chkTagInv.setHideLabel(true);

		LabelField lblfldInventoryModeFor = new LabelField(
				"inventory mode for tag");
		tabAdvertisingRequest.add(lblfldInventoryModeFor);

		LabelField lblfldRefferer = new LabelField("referer");
		tabAdvertisingRequest.add(lblfldRefferer);

		final TextField<String> txtReferer = new TextField<String>();
		tabAdvertisingRequest.add(txtReferer);
		txtReferer.setFieldLabel("New TextField");

		LabelField lblfldUrlOfA = new LabelField(
				"URL of a page which have trigered ad request.");
		tabAdvertisingRequest.add(lblfldUrlOfA);

		LabelField lblfldRefererkw = new LabelField("referer-kw");
		tabAdvertisingRequest.add(lblfldRefererkw);

		final TextField<String> txtRefererKw = new TextField<String>();
		tabAdvertisingRequest.add(txtRefererKw);
		txtRefererKw.setFieldLabel("New TextField");
		txtRefererKw.setValue("testaddb2811");

		LabelField lblfldNewLabelfield_5 = new LabelField(
				"Comma separated list of keywords grabbed from a page which have triggered an ad request. Use it instead obsolete 'referer-kww'");
		tabAdvertisingRequest.add(lblfldNewLabelfield_5);

		LabelField lblfldKn = new LabelField("kn");
		tabAdvertisingRequest.add(lblfldKn);

		final CheckBox chkKN = new CheckBox();
		tabAdvertisingRequest.add(chkKN);
		chkKN.setBoxLabel("");
		chkKN.setHideLabel(true);

		LabelField lblfldKeywordsNormalized = new LabelField(
				"keywords normalized, 1 means that referer-kw already normalized(and segmented) and normalization isn't required. 0 means that need to normalize referer-kw value.");
		tabAdvertisingRequest.add(lblfldKeywordsNormalized);

		LabelField lblfldColo = new LabelField("colo");
		tabAdvertisingRequest.add(lblfldColo);

		final NumberField txtColo = new NumberField();
		tabAdvertisingRequest.add(txtColo);
		txtColo.setFieldLabel("New NumberField");
		txtColo.setValue(4149);

		LabelField lblfldIfColoDefined = new LabelField(
				"If colo defined. AdServer log user impression with this value, else AdServer use value defined in it's configuration.");
		tabAdvertisingRequest.add(lblfldIfColoDefined);

		LabelField lblfldRequiredebuginfo = new LabelField("require-debug-info");
		tabAdvertisingRequest.add(lblfldRequiredebuginfo);

		final SimpleComboBox<String> cmbDebugInfo = new SimpleComboBox<String>();
		cmbDebugInfo.add("header");
		cmbDebugInfo.add("body");
		cmbDebugInfo.add("none");
		cmbDebugInfo.setSimpleValue("header");
		tabAdvertisingRequest.add(cmbDebugInfo);
		cmbDebugInfo.setFieldLabel("New SimpleComboBox");
		cmbDebugInfo.setTriggerAction(TriggerAction.ALL);

		LabelField lblfldIfheaderAd = new LabelField(
				"If \"header\" ad server will put debug information into Debug-Info HTTP response header, if \"body\" than debug info will be returned as an HTTP body (possibly replacing ad cretive), if \"none\" or parameter not present - no debug info provided.");
		tabAdvertisingRequest.add(lblfldIfheaderAd);

		LabelField lblfldCountry = new LabelField("country");
		tabAdvertisingRequest.add(lblfldCountry);

		final ListStore<Country> country = new ListStore<Country>();
		country.add(TestData.getCountries());

		final ComboBox<Country> cmbCountry = new ComboBox<Country>();

		cmbCountry.setTriggerAction(TriggerAction.ALL);
		cmbCountry.setEmptyText("Select a Country...");
		cmbCountry.setDisplayField("name");
		cmbCountry.setStore(country);
		cmbCountry.setTypeAhead(true);
		cmbCountry.setFieldLabel("New SimpleComboBox");
		tabAdvertisingRequest.add(cmbCountry);

		final MessageBox box = new MessageBox();

		cmbCountry.addSelectionChangedListener(new SelectionChangedListener<Country>() {
					public void selectionChanged(
							SelectionChangedEvent<Country> se) {
						Country cont = se.getSelectedItem();
						countryCode = cont.getAbbr();
					}
				});

		LabelField lblfldClientCountryCode = new LabelField(
				"Client country code. Actually server detects country code by IP address of a client. Value of this parameter overrides the one produced by server. Parameter is for debugging/testing purposes only. Must be in lower case. If country value is incorrect - length != 2, server ignore it");
		tabAdvertisingRequest.add(lblfldClientCountryCode);
		layoutContainer_9.add(tabAdvertisingRequest);

		FormPanel frmpnlNewFormpanel_10 = new FormPanel();
		frmpnlNewFormpanel_10.setHeading("Debug-Time Parameters");
		frmpnlNewFormpanel_10.setCollapsible(true);
		TableLayout tl_frmpnlNewFormpanel_10 = new TableLayout(5);
		tl_frmpnlNewFormpanel_10.setCellSpacing(4);
		frmpnlNewFormpanel_10.setLayout(tl_frmpnlNewFormpanel_10);

		LabelField lblfldDebugtime = new LabelField("debug-time");
		frmpnlNewFormpanel_10.add(lblfldDebugtime);

		final CheckBox chkDebugTime = new CheckBox();

		frmpnlNewFormpanel_10.add(chkDebugTime);
		chkDebugTime.setBoxLabel("");
		chkDebugTime.setHideLabel(true);

		final DateField debugDate = new DateField();
		debugDate.setEnabled(false);
		frmpnlNewFormpanel_10.add(debugDate);
		debugDate.setFieldLabel("New DateField");

		final TimeField debugTime = new TimeField();
		debugTime.setEnabled(false);
		frmpnlNewFormpanel_10.add(debugTime);
		debugTime.setFieldLabel("New TimeField");

		chkDebugTime.addListener(Events.Change, new Listener<FieldEvent>() {
			public void handleEvent(FieldEvent e) {
				if (!chkDebugTime.getValue()) {
					debugDate.disable();
					debugTime.disable();
					cmbDebugInfo.setSimpleValue("header");
				} else {
					debugDate.enable();
					debugTime.enable();
					cmbDebugInfo.setSimpleValue("body");
				}
			}
		});

		LabelField lblfldDebugParameterFor = new LabelField(
				"Debug parameter for emulate request specific time. Sets in form 'dd-mm-YYYY:HH-MM-SS'");
		frmpnlNewFormpanel_10.add(lblfldDebugParameterFor);
		layoutContainer_9.add(frmpnlNewFormpanel_10);

		FormPanel frmpnlDebugParameters = new FormPanel();
		frmpnlDebugParameters.setHeading("Debug Parameters");
		frmpnlDebugParameters.setCollapsible(true);
		TableLayout tl_frmpnlDebugParameters = new TableLayout(3);
		tl_frmpnlDebugParameters.setCellSpacing(4);
		tl_frmpnlDebugParameters.setInsertSpacer(true);
		frmpnlDebugParameters.setLayout(tl_frmpnlDebugParameters);

		LabelField lblfldDebugccg = new LabelField("debug.ccg");
		frmpnlDebugParameters.add(lblfldDebugccg);

		final NumberField txtDebugCCG = new NumberField();
		frmpnlDebugParameters.add(txtDebugCCG);

		LabelField lblfldProvideInDebug = new LabelField(
				"provide in debug information: reason why expected CCG is not returned.");
		frmpnlDebugParameters.add(lblfldProvideInDebug);

		LabelField lblfldDebugnofraud = new LabelField("debug.nofraud");
		frmpnlDebugParameters.add(lblfldDebugnofraud);

		final CheckBox chkDebugNofraud = new CheckBox();
		frmpnlDebugParameters.add(chkDebugNofraud);
		chkDebugNofraud.setBoxLabel("");
		chkDebugNofraud.setHideLabel(true);

		LabelField lblfldNewLabelfield = new LabelField(
				"if not 0 - disable fraud checking for this request, but enable testrequest.");
		frmpnlDebugParameters.add(lblfldNewLabelfield);

		LabelField lblfldSetuid = new LabelField("setuid");
		frmpnlDebugParameters.add(lblfldSetuid);

		final CheckBox chkSetUID = new CheckBox();
		frmpnlDebugParameters.add(chkSetUID);
		chkSetUID.setBoxLabel("");
		chkSetUID.setHideLabel(true);

		Button btnMakeRequest = new Button("Make Request");
		btnMakeRequest.addListener(Events.Select, new Listener<ButtonEvent>() {
			public void handleEvent(ButtonEvent e) {
				requestParameters = "";

				// Main Parameters
				requestParameters += "require-debug-info=" + cmbDebugInfo.getRawValue();

				if (chkTagInv.getValue())
					requestParameters += "&" + "tag.inv=1";
				
				if (chkKN.getValue())
					requestParameters += "&" + "kn=1";

				if (!txtTid.getRawValue().isEmpty())
					requestParameters += "&" + "tid=" + txtTid.getRawValue();
				
				if (!txtReferer.getRawValue().isEmpty())
					requestParameters += "&" + "referer="
							+ txtReferer.getRawValue();
				if (!txtRefererKw.getRawValue().isEmpty())
					requestParameters += "&" + "referer-kw="
							+ txtRefererKw.getRawValue();
				if (!txtColo.getRawValue().isEmpty())
					requestParameters += "&" + "colo=" + txtColo.getRawValue();

				if (!countryCode.isEmpty())
					requestParameters += "&" + "country=" + countryCode;

				// Debug Parameters
				if (!txtDebugCCG.getRawValue().isEmpty())
					requestParameters += "&" + "debug.ccg="
							+ txtDebugCCG.getRawValue();
				if (chkDebugNofraud.getValue())
					requestParameters += "&" + "debug.nofraud=1";
				if (chkSetUID.getValue())
					requestParameters += "&" + "setuid=1";

				// Debug-Time Parameters
				if (chkDebugTime.getValue()
						& !debugDate.getRawValue().isEmpty()
						& !debugTime.getRawValue().isEmpty()) {
					DateWrapper date = new DateWrapper(debugDate.getValue());
					String time = debugTime.getValue().getText();

					debugDateTime = date.getDay() + "-" + date.getMonth() + "-"
							+ date.getFullYear() + ":" + time.replace(":", "-");
					requestParameters += "&" + "debug-time=" + debugDateTime;
				}

				AdRequestBuilder.txtRequestParameters.setRawValue(requestParameters);
				AdRequestBuilder.txtUID.setText(Utilities.GetUID(Utilities.GetCookie(AdRequestBuilder.host)));
			}
		});

		layoutContainer_8.add(btnMakeRequest);
		
		LabelField lblfldIfDefinedIt = new LabelField(
				"if defined it override config option set_uid, if equal 0, server don't set uid");
		frmpnlDebugParameters.add(lblfldIfDefinedIt);
		layoutContainer_9.add(frmpnlDebugParameters);
		layoutContainer_9.setBorders(true);
		add(layoutContainer_9, new BorderLayoutData(LayoutRegion.CENTER));
	}
}

