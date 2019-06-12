package visualisation;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import utilities.Preferences;

public class Visualisation {

	public static void visualize(String s)
	{
		String a ="<!DOCTYPE html>\r\n" + 
				"<meta charset=\"utf-8\">\r\n" + 
				"<script src=\"https://d3js.org/d3.v4.js\"></script>\r\n" + 
				"<div id=\"my_dataviz\"></div>\r\n" + 
				"<style type=\"text/css\">\r\n" + 
				"    \r\n" + 
				"    .toolTip {\r\n" + 
				"        position: absolute;\r\n" + 
				"        display: none;\r\n" + 
				"        width: auto;\r\n" + 
				"        height: auto;\r\n" + 
				"        background: none repeat scroll 0 0 white;\r\n" + 
				"        border: 0 none;\r\n" + 
				"        border-radius: 8px 8px 8px 8px;\r\n" + 
				"        box-shadow: -3px 3px 15px #888888;\r\n" + 
				"        color: black;\r\n" + 
				"        padding: 5px;\r\n" + 
				"        text-align: center;\r\n" + 
				"    }\r\n" + 
				"</style>\r\n" + 
				"<script>\n";
		a+=s;
		a+="\nvar h = "+Preferences.height + ";\nvar w = "+Preferences.width+";\n";
		a+="var margin = {top: 10, right: 10, bottom: 10, left: 10},\r\n" + 
				"  width = w - margin.left - margin.right,\r\n" + 
				"  height = h - margin.top - margin.bottom;\r\n" + 
				"\r\n" + 
				"var svg = d3.select(\"#my_dataviz\")\r\n" + 
				".append(\"svg\")\r\n" + 
				"  .attr(\"width\", width + margin.left + margin.right)\r\n" + 
				"  .attr(\"height\", height + margin.top + margin.bottom)\r\n" + 
				".append(\"g\")\r\n" + 
				"  .attr(\"transform\",\r\n" + 
				"        \"translate(\" + margin.left + \",\" + margin.top + \")\");\r\n" + 
				"		\r\n" + 
				"var div = d3.select(\"#my_dataviz\").append(\"div\")\r\n" + 
				"            .attr(\"class\",\"treemap\")\r\n" + 
				"            .style(\"position\", \"relative\")\r\n" + 
				"            .style(\"width\", width + \"px\")\r\n" + 
				"            .style(\"height\", height + \"px\")\r\n" + 
				"			\r\n" + 
				"var tool = d3.select(\"body\").append(\"div\").attr(\"class\", \"toolTip\");\r\n" + 
				"\r\n" + 
				"d3.scan(datelemele, function(data) {\r\n" + 
				"  var root = d3.hierarchy(data).sum(function(d){ return d.noc}) // Here the size of each leave is given in the 'value' field in input data\r\n" + 
				"\r\n" + 
				"  d3.treemap()\r\n" + 
				"    .size([width, height])\r\n" + 
				"    .paddingTop(3)\r\n" + 
				"    .paddingRight(3)\r\n" + 
				"    .paddingInner(3)\r\n" +
				"    (root)\r\n" + 
				"\r\n" + 
				"  var color = d3.scaleLinear()\r\n" + 
				"	.domain([1, 100])\r\n" + 
				"	.range(['white','red'])\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"function translateForVisualisation(x) {\r\n" + 
				"  y=x/100;\r\n" + 
				"  return y;\r\n" + 
				"}\r\n" + 
				"  svg\r\n" + 
				"    .selectAll(\"rect\")\r\n" + 
				"    .data(root.leaves())\r\n" + 
				"    .enter()\r\n" + 
				"    .append(\"rect\")\r\n" + 
				"      .attr('x', function (d) { return d.x0; })\r\n" + 
				"      .attr('y', function (d) { return d.y0; })\r\n" + 
				"      .attr('width', function (d) { return d.x1 - d.x0; })\r\n" + 
				"      .attr('height', function (d) { return d.y1 - d.y0; })\r\n" + 
				"      .style(\"stroke\", \"black\")\r\n" + 
				"      .style(\"fill\", function(d){ return color(d.data.su)} )\r\n" + 
				"      .style(\"opacity\", function(d){ return 1})\r\n" + 
				"	  .on(\"mousemove\", function (d) {\r\n" + 
				"                    tool.style(\"left\", d3.event.pageX + 10 + \"px\")\r\n" + 
				"                    tool.style(\"top\", d3.event.pageY - 20 + \"px\")\r\n" + 
				"                    tool.style(\"display\", \"inline-block\");\r\n" + 
				"                    tool.html(\"Package: \"+d.data.package +\"<br>Interface: \"+ d.data.name + \"<br>SU: \"+ translateForVisualisation(d.data.su) + \"<br>WU: \"+ translateForVisualisation(d.data.wu) + \"<br>NU: \" + translateForVisualisation(d.data.nu) +\"<br>NOC: \" + d.data.noc);\r\n" + 
				"				}).on(\"mouseout\", function (d) {\r\n" + 
				"                    tool.style(\"display\", \"none\");\r\n" + 
				"                });"+
				"})\r\n" + 
				"</script>";
		PrintWriter writer;
//		File htmlFile = null;
		File htmlFile = new File("C:\\Users\\Body\\Desktop\\work2.html");
		try {
//			htmlFile = File.createTempFile("Visualisation", ".html"); 
			writer = new PrintWriter(htmlFile,"UTF-8");
			writer.println(a);
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		try {
			Desktop.getDesktop().browse(htmlFile.toURI());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
