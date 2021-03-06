package org.dice.ida.action.def;

import org.dice.ida.constant.IDAConst;
import org.dice.ida.model.ChatMessageResponse;
import org.dice.ida.model.ChatUserMessage;
import org.dice.ida.util.FileUtil;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Map;

@Component
public class ListDataSetsAction implements Action {
	@Override
	public void performAction(Map<String, Object> paramMap, ChatMessageResponse resp, ChatUserMessage userMessage) {
		try {
			FileUtil fileUtil = new FileUtil();
			ArrayList<String> datasets = fileUtil.getListOfDatasets();
			int datasetsSize = datasets.size();

			StringBuilder message = new StringBuilder();
			String datasetName = "";

			if (datasetsSize == 1) {
				datasetName = datasets.get(0);
				message.append("I just have one <ida-btn msg='load " + datasetName + " dataset' value='" + datasetName + "' style='default'> dataset right now!");
			} else if (datasetsSize == 0) {
				message.append("I don't have any dataset right now!");
			} else {
				message.append("I have total ");
				message.append(datasetsSize);
				message.append(" datasets. ");
				for (int i = 0; i < datasetsSize; i++)
				{
					if (i == (datasetsSize-1)) {
						message.append(" and ");
					} else if (i != 0) {
						message.append(", ");
					}
					datasetName = datasets.get(i);
					message.append(" <ida-btn msg='load " + datasetName + " dataset' value='" + datasetName + "' style='default'> ");
				}
				message.append(" datasets.");
			}
			resp.setMessage(message.toString());
			resp.setUiAction(IDAConst.UAC_NRMLMSG);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}

