package com.phorm.adrequestbuilder.client;

import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.Style.Orientation;
import com.extjs.gxt.ui.client.data.BaseListLoader;
import com.extjs.gxt.ui.client.data.HttpProxy;
import com.extjs.gxt.ui.client.data.ListLoadResult;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.data.ModelType;
import com.extjs.gxt.ui.client.data.XmlLoadResultReader;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.GridEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.store.GroupingStore;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridGroupRenderer;
import com.extjs.gxt.ui.client.widget.grid.GroupColumnData;
import com.extjs.gxt.ui.client.widget.grid.GroupingView;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.RowLayout;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.user.client.Window;
import com.phorm.adrequestbuilder.resources.Resources;

public class CustomRequests extends ContentPanel {
	String requestParams;

	public CustomRequests(String root, String path) {
		setFrame(true);
		setLayout(new RowLayout(Orientation.VERTICAL));

		final Grid<ModelData> grid;

		setFiresEvents(true);
		List<ColumnConfig> columns = new ArrayList<ColumnConfig>();
		columns.add(new ColumnConfig("testcase", "Test Case", 100));
		columns.add(new ColumnConfig("tid", "Tag Id", 100));
		columns.add(new ColumnConfig("referer-kw", "Referer Keyword", 165));
		columns.add(new ColumnConfig("referer", "Referer", 165));
		columns.add(new ColumnConfig("colo", "Colo", 50));
		columns.add(new ColumnConfig("require-debug-info", "Debug Info", 65));
		columns.add(new ColumnConfig("country", "Country", 65));
		columns.add(new ColumnConfig("glbfcap", "glbfcap", 65));
		columns.add(new ColumnConfig("prck", "prck", 65));

		// create the column model
		final ColumnModel cm = new ColumnModel(columns);

		// defines the xml structure
		ModelType type = new ModelType();
		type.setRoot("fraudBilling");
		type.setRecordName("request");
		type.addField("testcase", "testcase");
		type.addField("tid", "tid");
		type.addField("referer-kw", "referer-kw");
		type.addField("referer", "referer");
		type.addField("colo", "colo");
		type.addField("require-debug-info", "require-debug-info");
		type.addField("country", "country");
		type.addField("glbfcap", "glbfcap");
		type.addField("prck", "prck");

		// use a http proxy to get the data
		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, path);

		HttpProxy<String> proxy = new HttpProxy<String>(builder);

		// need a loader, proxy, and reader
		XmlLoadResultReader<ListLoadResult<ModelData>> reader = new XmlLoadResultReader<ListLoadResult<ModelData>>(
				type);

		final BaseListLoader<ListLoadResult<ModelData>> loader = new BaseListLoader<ListLoadResult<ModelData>>(
				proxy, reader);

		final GroupingStore<ModelData> store = new GroupingStore<ModelData>(loader);
		store.setMonitorChanges(true);
		store.groupBy("testcase");

		GroupingView view = new GroupingView();
		view.setShowGroupedColumn(false);
		view.setForceFit(true);
		view.setGroupRenderer(new GridGroupRenderer() {
			public String render(GroupColumnData data) {
				String f = cm.getColumnById(data.field).getHeader();
				String l = data.models.size() == 1 ? "Item" : "Items";
				return f + ": " + data.group + " (" + data.models.size() + " "
						+ l + ")";
			}
		});

		view.setShowGroupedColumn(false);
		view.setForceFit(true);

		grid = new Grid<ModelData>(store, cm);
		grid.setBorders(true);
		grid.setView(view);

		grid.addListener(Events.CellClick, new Listener<BaseEvent>() {
			public void handleEvent(BaseEvent be) {
				GridEvent ge = (GridEvent) be;
				String test;
				
				requestParams = "tid=" + ge.getModel().get("tid").toString();
				requestParams += "&colo=" + ge.getModel().get("colo").toString();
				if (ge.getModel().get("referer-kw") != null) {
					test = ge.getModel().get("referer-kw").toString();
					if (!test.isEmpty()) requestParams += "&referer-kw=" + test;
				}
				if (ge.getModel().get("referer") != null) {
					test = ge.getModel().get("referer").toString();
					requestParams += "&referer=" + test;
				}
				requestParams += "&require-debug-info=" + ge.getModel().get("require-debug-info").toString();
				requestParams += "&country"	+ ge.getModel().get("country").toString();
				requestParams += "&glbfcap" + ge.getModel().get("glbfcap").toString();
				requestParams += "&prck" + ge.getModel().get("prck").toString();
				AdRequestBuilder.txtRequestParameters.setRawValue(requestParams);
			}
		});

		
		setCollapsible(true);
		setAnimCollapse(true);
		setButtonAlign(HorizontalAlignment.CENTER);
		setIcon(Resources.ICONS.table());
		setLayout(new FitLayout());
		setAutoWidth(true);
		add(grid);
		setSize(938, 533);
		//grid.setSize("894px", "448px");

		loader.load();
	}
}
