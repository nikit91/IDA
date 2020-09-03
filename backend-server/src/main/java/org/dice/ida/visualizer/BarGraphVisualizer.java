package org.dice.ida.visualizer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.dice.ida.model.AttributeSummary;
import org.dice.ida.model.DataSummary;
import org.dice.ida.model.bargraph.BarGraphData;
import org.dice.ida.model.bargraph.BarGraphItem;
import org.dice.ida.util.MetaFileReader;
import weka.core.Attribute;
import weka.core.Instances;
/**
 * Class to provide required attributes for bar graph visualization and apply data filters.
 *
 * @author Sourabh
 */

public class BarGraphVisualizer {
	String xAxisLabel;
	String yAxisLabel;
	String label = "Bar Graph";
	String dataSetName;
	String tableName;
	Attribute xaxis ;
	Attribute yaxis;
	List<BarGraphItem> items;
	Instances data;
	DataSummary DS;
	public BarGraphVisualizer(String xAxis, String yAxis, String dsName,
			String tableName, Instances data)
	{
		this.xAxisLabel = xAxis;
		this.yAxisLabel = yAxis;
		this.dataSetName = dsName;
		this.tableName = tableName;
		this.data = data;
		try {
			DS = new MetaFileReader().createDataSummary(dataSetName, tableName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public BarGraphData createBarGraph()
	{
		
		items = new ArrayList<BarGraphItem>();
		loadBarGraphItem();  
		
		return new BarGraphData(label, items , xAxisLabel, yAxisLabel,dataSetName,
				tableName);
	}
	private void loadBarGraphItem()
	{
		//check for filter and load bar graph items 
		
        //Loads the File
			xaxis = data.attribute(xAxisLabel);
			yaxis = data.attribute(yAxisLabel);
			if (xaxis.isNominal())
			{
				loadNominal(); 
			}
			if (xaxis.isNumeric())
			{
				
			}
			if (xaxis.isDate())
			{
				
			}
	}
	public void loadNominal()
	{
		HashMap<String,Double> temp = new HashMap<String,Double>();
		AttributeSummary xaxisSummary =DS.getAttributeSummaryList().stream().filter(x->x.getName().equalsIgnoreCase(xAxisLabel)).collect(Collectors.toList()).get(0);
		AttributeSummary yaxisSummary =DS.getAttributeSummaryList().stream().filter(x->x.getName().equalsIgnoreCase(yAxisLabel)).collect(Collectors.toList()).get(0);
		if(yaxisSummary.getType().equalsIgnoreCase("Num"))
		{
			for (int i =0;i<data.numInstances();i++)
			{
				if(temp.containsKey(data.instance(i).stringValue(xaxis)))
				{
					double yvalue = temp.get(data.instance(i).stringValue(xaxis));
					temp.put(data.instance(i).stringValue(xaxis),yvalue+data.instance(i).value(yaxis));
				}
				else
				{
					temp.put(data.instance(i).stringValue(xaxis),data.instance(i).value(yaxis));
				}
			}
		}
		if(yaxisSummary.getType().equalsIgnoreCase("Nom"))
		{
			for (int i =0;i<data.numInstances();i++)
			{
				if(temp.containsKey(data.instance(i).stringValue(xaxis)))
				{
					double yvalue = temp.get(data.instance(i).stringValue(xaxis));
					temp.put(data.instance(i).stringValue(xaxis),yvalue+1);
				}
				else
				{
					temp.put(data.instance(i).stringValue(xaxis),1.0);
				}
			}
		}
		for (String key:temp.keySet() )
		{
			items.add(new BarGraphItem(key,temp.get(key)));
		}
	}
}
