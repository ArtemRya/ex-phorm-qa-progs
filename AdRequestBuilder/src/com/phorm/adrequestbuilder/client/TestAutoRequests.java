package com.phorm.adrequestbuilder.client;

import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.Style.Orientation;
import com.extjs.gxt.ui.client.core.El;
import com.extjs.gxt.ui.client.data.BaseListLoader;
import com.extjs.gxt.ui.client.data.HttpProxy;
import com.extjs.gxt.ui.client.data.ListLoadResult;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.data.ModelType;
import com.extjs.gxt.ui.client.data.XmlLoadResultReader;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.GridEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.store.GroupingStore;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.ProgressBar;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.grid.CheckBoxSelectionModel;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridGroupRenderer;
import com.extjs.gxt.ui.client.widget.grid.GridSelectionModel;
import com.extjs.gxt.ui.client.widget.grid.GridViewConfig;
import com.extjs.gxt.ui.client.widget.grid.GroupColumnData;
import com.extjs.gxt.ui.client.widget.grid.GroupingView;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.RowLayout;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.phorm.adrequestbuilder.resources.Resources;

public class TestAutoRequests extends ContentPanel {
	private String checkedStyle = "x-grid3-group-check";
	private String uncheckedStyle = "x-grid3-group-uncheck";
	GroupingView view;
	public static Button btnNewButton = new Button("Make Request");;
	int i = 0;
	float j = 0;
	public static int timeout;
	private ModelData dt;
	private Grid<ModelData> grid;
	private final RequestServiceAsync requestServer = GWT.create(RequestService.class);
	private String requestParameters;
	private String result;
	
	public TestAutoRequests(String root, String path) {
		setFrame(true);
		setLayout(new RowLayout(Orientation.VERTICAL));

		final CheckBoxSelectionModel<ModelData> sm = new CheckBoxSelectionModel<ModelData>() {
			@Override
			public void deselectAll() {
				super.deselectAll();
				NodeList<com.google.gwt.dom.client.Element> groups = view.getGroups();
				for (int i = 0; i < groups.getLength(); i++) {
					com.google.gwt.dom.client.Element group = groups.getItem(i).getFirstChildElement();
					setGroupChecked((Element) group, false);
				}
			}

			@Override
			public void selectAll() {
				super.selectAll();
				NodeList<com.google.gwt.dom.client.Element> groups = view.getGroups();
				for (int i = 0; i < groups.getLength(); i++) {
					com.google.gwt.dom.client.Element group = groups.getItem(i).getFirstChildElement();
					setGroupChecked((Element) group, true);
				}
			}

			@Override
			protected void doDeselect(List<ModelData> models,
					boolean supressEvent) {
				super.doDeselect(models, supressEvent);
				NodeList<com.google.gwt.dom.client.Element> groups = view.getGroups();
				search: for (int i = 0; i < groups.getLength(); i++) {
					com.google.gwt.dom.client.Element group = groups.getItem(i);
					NodeList<Element> rows = El.fly(group).select(".x-grid3-row");
					for (int j = 0, len = rows.getLength(); j < len; j++) {
						Element r = rows.getItem(j);
						int idx = grid.getView().findRowIndex(r);
						ModelData m = grid.getStore().getAt(idx);
						if (!isSelected(m)) {
							setGroupChecked((Element) group, false);
							continue search;
						}
					}
				}

			}

			@Override
			protected void doSelect(List<ModelData> models,
					boolean keepExisting, boolean supressEvent) {
				super.doSelect(models, keepExisting, supressEvent);
				NodeList<com.google.gwt.dom.client.Element> groups = view.getGroups();
				search: for (int i = 0; i < groups.getLength(); i++) {
					com.google.gwt.dom.client.Element group = groups.getItem(i);
					NodeList<Element> rows = El.fly(group).select(".x-grid3-row");
					for (int j = 0, len = rows.getLength(); j < len; j++) {
						Element r = rows.getItem(j);
						int idx = grid.getView().findRowIndex(r);
						ModelData m = grid.getStore().getAt(idx);
						if (!isSelected(m)) {
							continue search;
						}
					}
					setGroupChecked((Element) group, true);
				}
			}
		};
		
		List<ColumnConfig> columns = new ArrayList<ColumnConfig>();
		columns.add(sm.getColumn());
		columns.add(new ColumnConfig("testcase", "Test Case", 100));
		columns.add(new ColumnConfig("result", "Result", 80));
		columns.add(new ColumnConfig("tid", "Tag Id", 60));
		columns.add(new ColumnConfig("referer-kw", "Referer Keyword", 100));
		columns.add(new ColumnConfig("referer", "Referer", 165));
		columns.add(new ColumnConfig("colo", "Colo", 50));
		columns.add(new ColumnConfig("require-debug-info", "Debug Info", 50));
		columns.add(new ColumnConfig("country", "Country", 35));
		columns.add(new ColumnConfig("glbfcap", "glbfcap", 30));
		columns.add(new ColumnConfig("setuid", "Set UID", 35));
		columns.add(new ColumnConfig("click", "Click", 25));
		columns.add(new ColumnConfig("repeat", "Repeat", 30));
		columns.add(new ColumnConfig("cleare-cookie", "Cleare Cookie", 45));

		// create the column model
		final ColumnModel cm = new ColumnModel(columns);

		// defines the xml structure
		ModelType type = new ModelType();
		type.setRoot(root);
		type.setRecordName("request");
		type.addField("testcase", "testcase");
		type.addField("result", "result");
		type.addField("tid", "tid");
		type.addField("referer-kw", "referer-kw");
		type.addField("referer", "referer");
		type.addField("colo", "colo");
		type.addField("require-debug-info", "require-debug-info");
		type.addField("country", "country");
		type.addField("glbfcap", "glbfcap");
		type.addField("setuid", "setuid");
		type.addField("click", "click");
		type.addField("repeat", "repeat");
		type.addField("cleare-cookie", "cleare-cookie");

		// use a http proxy to get the data
		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, path);

		HttpProxy<String> proxy = new HttpProxy<String>(builder);

		// need a loader, proxy, and reader
		XmlLoadResultReader<ListLoadResult<ModelData>> reader = new XmlLoadResultReader<ListLoadResult<ModelData>>(type);

		final BaseListLoader<ListLoadResult<ModelData>> loader = new BaseListLoader<ListLoadResult<ModelData>>(proxy, reader);

		final GroupingStore<ModelData> store = new GroupingStore<ModelData>(loader);
		store.setMonitorChanges(true);
		store.groupBy("testcase");

		view = new GroupingView() {
			@Override
			protected void onMouseDown(GridEvent<ModelData> ge) {
				El hd = ge.getTarget(".x-grid-group-hd", 10);
				El target = ge.getTargetEl();
				if (hd != null && target.hasStyleName(uncheckedStyle)
						|| target.hasStyleName(checkedStyle)) {
					boolean checked = !ge.getTargetEl().hasStyleName(uncheckedStyle);
					checked = !checked;
					if (checked) {
						ge.getTargetEl().replaceStyleName(uncheckedStyle, checkedStyle);
					} else {
						ge.getTargetEl().replaceStyleName(checkedStyle, uncheckedStyle);
					}

					Element group = (Element) findGroup(ge.getTarget());
					if (group != null) {
						NodeList<Element> rows = El.fly(group).select(".x-grid3-row");
						List<ModelData> temp = new ArrayList<ModelData>();
						for (int i = 0; i < rows.getLength(); i++) {
							Element r = rows.getItem(i);
							int idx = findRowIndex(r);
							ModelData m = grid.getStore().getAt(idx);
							temp.add(m);
						}
						if (checked) {
							grid.getSelectionModel().select(temp, true);
						} else {
							grid.getSelectionModel().deselect(temp);
						}
					}
					return;
				}
				super.onMouseDown(ge);
			}
		};

		view.setShowGroupedColumn(false);
		view.setForceFit(true);
		
		view.setGroupRenderer(new GridGroupRenderer() {  
		      public String render(GroupColumnData data) {  
		        String f = cm.getColumnById(data.field).getHeader();  
		        String l = data.models.size() == 1 ? "Item" : "Items";  
		        return "<div class='x-grid3-group-checker'><div class='" + uncheckedStyle + "'> </div></div> " + f  
		            + ": " + data.group + " (" + data.models.size() + " " + l + ")";  
		     }  
		});  
		
		grid = new Grid<ModelData>(store, cm);
		grid.setBorders(true);
		grid.setView(view);
		grid.addPlugin(sm);
		grid.setSelectionModel(sm);

		btnNewButton.addListener(Events.Select, new Listener<ButtonEvent>() {
			public void handleEvent(ButtonEvent e) {
			    final int step;
				Timer timer;
			    
				GridSelectionModel<ModelData> sel = grid.getSelectionModel();
				final List<ModelData> list = sel.getSelectedItems();
			
				if (list.isEmpty()) {
					Window.alert("Please, select at least one AdRequest!");
					return;
				}
				
				final MessageBox box = MessageBox.progress("Please wait", "Executing Requests...", "Initializing...");  
			    final ProgressBar bar = box.getProgressBar();  
				
				step = 100 / list.size();
				
				dt = list.get(i);
				dt.set("result", "Executed");
				store.update(dt);
				AdRequestBuilder.txtRequestParameters.setRawValue(GetParameters(dt));
				dt.set("result", "Success");
				store.update(dt);
				
				j += (int) step;
				bar.updateProgress(j / 100, (int) j + "% Complete");
				
				if (i == list.size() - 1) {
					box.close();
					Info.display("Message", "Request Completed Successfully!", "");
					j = 0;
					return;
				}
				
				j += (int) step;
				i++;
				
                timer = new Timer() {
                    @Override
                    public void run() {
                      	dt = list.get(i);
            				
            			dt.set("result", "Executed");
            			store.update(dt);
                       	AdRequestBuilder.txtRequestParameters.setRawValue(GetParameters(dt));
                       	dt.set("result", "Success");
                       	store.update(dt);
                       	bar.updateProgress(j / 100, (int) j + "% Complete");              
                        j += (int) step;
                       	
            			if (i == list.size() - 1) {
                			j = 0;
            				i = 0;
                			box.close();
                			Info.display("Message", "All Requests Completed Successfully!", "");
                       		cancel();
                       	} else i++;
                    }
                };
                timer.scheduleRepeating(timeout);
			}
		});

		grid.addListener(Events.CellClick, new Listener<BaseEvent>() {
			public void handleEvent(BaseEvent be) {
				GridEvent ge = (GridEvent) be;
				AdRequestBuilder.txtRequestParameters.setRawValue(GetParameters(ge.getModel()));
				AdRequestBuilder.txtUID.setText(Utilities.GetUID(Utilities.GetCookie(AdRequestBuilder.host)));
				//grid.getView().getRow(ge.getRowIndex()).getStyle().setColor("green");
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
		//grid.getView().getRow(1).getStyle().setColor("green");
		//grid.getView().setViewConfig(new GVC());
		
		loader.load();
	}
	
	/*
	private class GVC extends GridViewConfig
	{
		@Override
		public String getRowStyle(ModelData model, int rowIndex, ListStore<ModelData> ds) {
			if (model != null)
			{
				if ("Executed".equals(model.get("result")))
					return ".green";
				else if ("Success".equals(model.get("result"))) {
					return ".blue";
				} else if ("Error".equals(model.get("result"))) {
					return ".red";
				}
			}
			return "";
		}
	}*/
	
	private String GetParameters(ModelData model) {
		String propString = "";
		String tmpString = "";
		
		if (model.get("tid") != null) {
			tmpString = model.get("tid").toString();
			if (!tmpString.isEmpty()) propString = "tid=" + model.get("tid").toString();
		}
		if (model.get("referer-kw") != null) {
			tmpString = model.get("referer-kw").toString();
			if (!tmpString.isEmpty()) propString += "&referer-kw=" + model.get("referer-kw").toString();
		}
		if (model.get("referer") != null) {
			tmpString = model.get("referer").toString();
			if (!tmpString.isEmpty()) propString += "&referer=" + model.get("referer").toString();
		}
		if (model.get("colo") != null) {
			tmpString = model.get("colo").toString();
			if (!tmpString.isEmpty()) propString += "&colo=" + model.get("colo").toString();
		}
		if (model.get("require-debug-info") != null) {
			tmpString = model.get("require-debug-info").toString();
			if (!tmpString.isEmpty()) propString += "&require-debug-info=" + model.get("require-debug-info").toString();
		}
		if (model.get("country") != null) {
			tmpString = model.get("country").toString();
			if (!tmpString.isEmpty()) propString += "&country=" + model.get("country").toString();
		}
		if (model.get("glbfcap") != null) {
			tmpString = model.get("glbfcap").toString();
			if (!tmpString.isEmpty()) propString += "&glbfcap=" + model.get("glbfcap").toString();
		}
		if (model.get("setuid") != null) {
			tmpString = model.get("setuid").toString();
			if (!tmpString.isEmpty()) propString += "&setuid=" + model.get("setuid").toString();
		}
	
		return propString;
	}
	
	private El findCheck(Element group) {
		return El.fly(group).selectNode(".x-grid3-group-checker").firstChild();
	}

	private void setGroupChecked(Element group, boolean checked) {
		findCheck(group).replaceStyleName(
				checked ? uncheckedStyle : checkedStyle,
				checked ? checkedStyle : uncheckedStyle);
	}
	
	private String sendRequestToServer() {
		final String host = AdRequestBuilder.host;
		String scheme = AdRequestBuilder.scheme;
		String path = AdRequestBuilder.adService;
		String method = "GET";
		String clientCookie = Utilities.GetCookie(host);

		//txtResult.setHtml("");

		requestServer.executeRequest(scheme, host, path, requestParameters, method, clientCookie, new AsyncCallback<List<String>>() {
			public void onFailure(Throwable caught) {
				result = caught.getLocalizedMessage();
			}

			@Override
			public void onSuccess(List<String> requestResult) {
				result = requestResult.get(1);
				String tmpCookie = requestResult.get(0);
				if (!tmpCookie.isEmpty()) {
					AdRequestBuilder.txtUID.setText(Utilities.GetUID(tmpCookie));
					Utilities.SetCookie(host, tmpCookie);
				}
			}
		});
		return result;
	}
}
