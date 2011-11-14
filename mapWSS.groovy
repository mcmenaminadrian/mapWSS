import org.xml.sax.Attributes;
import javax.xml.parsers.SAXParserFactory
import org.xml.sax.helpers.DefaultHandler
import org.xml.sax.*


class GraphWSS {

	GraphWSS(def iFile, width, height, marks, margins, showFaults)
	{
		println("Beginning first pass")
		def handler = new PtraceFirstHandler()
		def reader =
			SAXParserFactory.newInstance().newSAXParser().XMLReader
		reader.setContentHandler(handler)
		reader.parse(new InputSource(new FileInputStream(iFile)))
		
		def maxSteps = handler.maxSteps
		def maxPagesP = handler.maxPagesP
		def maxPagesS = handler.maxPagesS
<<<<<<< HEAD
		def maxPagesR = handler.maxPagesR
=======
		def maxPagesM = handler.maxPagesM
>>>>>>> 5cde9ec0623ada4a2613e68eb84cb0846b841ef2
		def sampleRate = (int) maxSteps/width
		println("Beginning second pass")
		def handler2 = new PtraceSecondHandler(sampleRate)
		reader.setContentHandler(handler2)
		reader.parse(new InputSource(new FileInputStream(iFile)))
		
		def listP = handler2.wssListP
		def listS = handler2.wssListS
<<<<<<< HEAD
		def listR = handler2.wssListR
		println("Drawing graph")
		new PtraceDrawWSSGraph(listP, listS, listR, width, height, marks,
			margins, maxSteps, maxPagesP, maxPagesS, maxPagesR);
		if (showFaults) {
			println("Finding maxima of hard and soft faults")
			def handler3 = new PtraceThirdHandler()
			reader.setContentHandler(handler3)
			reader.parse(new InputSource(new FileInputStream(iFile)))
			def maxSoft = handler3.maxSoft
			def maxHard = handler3.maxHard
			println("Sampling fault count")
			def handler4 = new PtraceFourthHandler(sampleRate)
			reader.setContentHandler(handler4)
			reader.parse(new InputSource(new FileInputStream(iFile)))
			def softList = handler4.softList
			def hardList = handler4.hardList
			new PtraceDrawFaultGraph(softList, hardList, width, height, marks,
				margins, maxSteps, maxSoft, maxHard)
		}
=======
		def listM = handler2.wssListM
		println("Drawing graph")
		new PtraceDrawWSSGraph(listP, listS, listM, width, height,
			marks, margins, maxSteps, maxPagesP,
			maxPagesS, maxPagesM);
>>>>>>> 5cde9ec0623ada4a2613e68eb84cb0846b841ef2
	}
		
}



def ptraceCli = new CliBuilder
	(usage: 'mapWSS [options] <ptracexml file>')
ptraceCli.w(longOpt:'width', args: 1,
'width of SVG ouput - default 800')
ptraceCli.h(longOpt:'height', args: 1,
 'height of SVG output - default 600')
ptraceCli.u(longOpt: 'usage', 'prints this information')
ptraceCli.m(longOpt: 'gridmarks', args: 1, 'grid marks on graph - default 4')
ptraceCli.b(longOpt: 'margins', args: 1,
	'margin size on graphs (default 100px)')
ptraceCli.f(longOpt: 'faults', 'graph soft and hard faults')

def width = 800
def height = 600
def marks = 4
def margins = 100
def showFaults = false

def pAss = ptraceCli.parse(args)
if (pAss.u || args.size() == 0) {
	ptraceCli.usage()
} else {
	if (pAss.w)
		width = Integer.parseInt(pAss.w)
	if (pAss.h)
		height = Integer.parseInt(pAss.h)
	if (pAss.m)
		marks = Integer.parseInt(pAss.m)
	if (pAss.b)
		margins = Integer.parseInt(pAss.b)
	if (pAss.f)
		showFaults = true

	def gWSS = new GraphWSS(args[args.size() -1], width, height, marks,
		margins, showFaults)
}


	
