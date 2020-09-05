package org.dice.ida.action.def;

import java.io.File;
import java.util.Map;

import org.dice.ida.constant.IDAConst;
import org.dice.ida.model.ChatMessageResponse;
import org.dice.ida.model.bargraph.BarGraphData;
import org.dice.ida.visualizer.BarGraphVisualizer;
import weka.core.Instances;
import weka.core.converters.CSVLoader;

/**
 * Class to perform action on bar-graph intent detection.
 *
 * @author Sourabh
 */

public class BarGraphAction implements Action {

	@Override
	public void performAction(Map<String, Object> paramMap, ChatMessageResponse chatMessageResponse) {
		// TODO Auto-generated method stub

		try {
			Map<String, Object> payload = chatMessageResponse.getPayload();
			if (payload.get("activeDS") == null || payload.get("activeTable") == null) {
				chatMessageResponse.setMessage(IDAConst.BOT_SOMETHING_WRONG);
				chatMessageResponse.setUiAction(IDAConst.UAC_NRMLMSG);
				return;
			} else {
				String datasetName = payload.get("activeDS").toString();
				String tableName = payload.get("activeTable").toString();
				if (datasetName.isEmpty() || tableName.isEmpty()) {
					if (datasetName.isEmpty()) {
						chatMessageResponse.setMessage(IDAConst.BOT_LOAD_DS_BEFORE);
					} else if (tableName.isEmpty()) {
						chatMessageResponse.setMessage(IDAConst.BOT_SELECT_TABLE);
					}
					chatMessageResponse.setUiAction(IDAConst.UAC_NRMLMSG);
				} else {
					String xAxis = paramMap.get(IDAConst.PARAM_XAXIS_NAME).toString();
					String yAxis = paramMap.get(IDAConst.PARAM_YAXIS_NAME).toString();
					BarGraphData barGraph;

					if (xAxis != null && !xAxis.isEmpty() && yAxis != null && !yAxis.isEmpty()) {
						boolean xaxist = false;
						boolean yaxist = false;
						CSVLoader loader = new CSVLoader();
						//Loads the File
						loader.setSource(new File("./src/main/resources/datasets/" + datasetName + "/" + tableName));
						Instances data = loader.getDataSet();
						for (int i = 0; i < data.numAttributes(); i++) {
							if (data.attribute(i).name().trim().equalsIgnoreCase(xAxis.trim())) {
								xAxis = data.attribute(i).name();
								xaxist = true;
							} if (data.attribute(i).name().trim().equalsIgnoreCase(yAxis.trim())) {
								yAxis = data.attribute(i).name();
								yaxist = true;
							}
						}
						if (xaxist && yaxist) {
							barGraph = new BarGraphVisualizer(xAxis, yAxis, datasetName, tableName, data).createBarGraph();
							payload.put("barGraphData", barGraph);
							chatMessageResponse.setPayload(payload);
							chatMessageResponse.setMessage(IDAConst.BAR_GRAPH_LOADED);
							chatMessageResponse.setUiAction(IDAConst.UIA_BARGRAPH);
						} else {
							chatMessageResponse.setMessage(IDAConst.INVALID_BG_DATA_PROVIDED);
							chatMessageResponse.setUiAction(IDAConst.UAC_NRMLMSG);
						}
					} else {
						SimpleTextAction.setSimpleTextResponse(paramMap, chatMessageResponse);
					}
				}
			}

		} catch (Exception e) {
			chatMessageResponse.setMessage(IDAConst.BOT_SOMETHING_WRONG);
			chatMessageResponse.setUiAction(IDAConst.UAC_NRMLMSG);
		}

	}


}