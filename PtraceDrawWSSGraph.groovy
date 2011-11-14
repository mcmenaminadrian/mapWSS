import groovy.xml.MarkupBuilder


class PtraceDrawWSSGraph {

	/**
	 * Draw the graph based on sampled points
	 * 
	 * @param listP  sampled number of pages in page table
	 * @param listS  sampled number of swapped pages
	 * @param listR  sampled number of pages present but not in page table
	 * @param width  width of graph
	 * @param height height of graph
	 * @param marks  number of grid marks to draw
	 * @param margins margins in graph
	 * @param maxSteps total number of steps taken by program
	 * @param maxPagesP maximum number of pages in page table
	 * @param maxPagesS maximum number of swapped pages
	 * @param maxPagesR maximum number of pages only present
	 */
	PtraceDrawWSSGraph(def listP, def listS, def listR, def width, def height,
		 def marks, def margins, def maxSteps, def maxPagesP, def maxPagesS,
		 def maxPagesR)
	{
		def fileName = "SampledWSS${new Date().time.toString()}.svg"
		def writer = new FileWriter(fileName)
		println("Writing $fileName")
		def svg = new MarkupBuilder(writer)
		//header etc
		def cWidth = width + 2 * margins
		def cHeight = height + 2 * margins
		writer.write(
			"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n")
		writer.write("<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\" ")
		writer.write("\"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\">\n")
		writer.write(
		"<svg width=\"${cWidth}px\" height=\"${cHeight}px\" version=\"1.1\" ")
		writer.write("xmlns=\"http://www.w3.org/2000/svg\">\n")
		
		svg.line(x1:margins - 5, y1:height + margins + 5,
			x2: margins + width, y2:height + margins + 5,
			stroke:"black", "stroke-width":10 )
		svg.line(x1:margins - 5, y1: height + margins + 5,
			x2: margins - 5, y2: margins,
			stroke:"black", "stroke-width":10)
		if (maxPagesR > maxPagesP)
			maxPagesP = maxPagesR
		def biggestP = maxPagesP > maxPagesS ? maxPagesP : maxPagesS
		biggestP = biggestP > maxPagesM ? biggestP : maxPagesM
		(0 .. marks).each { i ->
			svg.line(x1:(int)(margins + width * i/marks),
				y1:15 + height + margins,
				x2:(int)(margins + width * i/marks),
				y2: margins,
				stroke:"lightgrey", "stroke-width":1){}

			svg.text(x:(int)(margins - 5 + width * i/marks),
				y:20 + height + margins,
				style: "font-family: Helvetica; font-size:10; fill: maroon",
				((int) maxSteps * i / marks))

			svg.line(x1:margins - 20,
				y1:(int)(height * i/marks + margins),
				x2:width + margins,
				y2:(int)(height * i/marks + margins),
				stroke:"lightgrey", "stroke-width":1){}
	
			svg.text(x:margins - 60,
				y: (int)(5 + height * i/marks + margins),
				style: "font-family: Helvetica; font-size:10; fill: maroon",
				(Long.toString((int)(biggestP - (biggestP * i/marks)), 10)))
		}
		
		def yFact = height/biggestP
		def lastX = margins
		def lastYP = margins + height
		def lastYS = lastYP
		def lastYR = lastYS
		listP.eachWithIndex { value, i ->
			def yPointP = margins + (height - value * yFact)
			def yPointS = margins + (height - listS[i] * yFact)
			def yPointR = margins + (height - listR[i] * yFact)
			def xPoint = margins + i
			svg.line(x1:lastX, y1:lastYP, x2: xPoint, y2: yPointP,
				style:"fill:none; stroke:blue; stroke-width:1;") 
			svg.line(x1:lastX, y1:lastYS, x2: xPoint, y2: yPointS,
				style:"fill:none; stroke:red; stroke-width:1;")
			svg.line(x1:lastX, y1:lastYR, x2: xPoint, y2: yPointR,
				style:"fill:none; stroke:green; stroke-width:1;");
			lastX = xPoint
			lastYP = yPointP
			lastYS = yPointS
			lastYR = yPointR
		}
		svg.text(x:margins/4, y: height * 0.75,
			transform:"rotate(270, ${margins/4}, ${height*0.75})",
			style: "font-family: Helvetica; font-size:10; fill:red",
			"Pages: red swapped, blue in page table, green present only")
		def strInst = "Steps"
		svg.text(x:margins, y: height + margins * 1.5,
				style: "font-family:Helvetica; font-size:10; fill:red",
				strInst)
		writer.write("\n</svg>")
		writer.close()
	}
}
