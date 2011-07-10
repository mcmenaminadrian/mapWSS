import org.xml.sax.Attributes;
import javax.xml.parsers.SAXParserFactory
import org.xml.sax.helpers.DefaultHandler
import org.xml.sax.*


class GraphWSS {

	GraphWSS(def iFile, width, height, marks, margins)
	{
		println("Beginning first pass")
		def handler = new PtraceFirstHandler()
		def reader = SAXParserFactory.newInstance().newSAXParser().XMLReader
		reader.setContentHandler(handler)
		reader.parse(new InputSource(new FileInputStream(iFile)))
		
		def maxSteps = handler.maxSteps
		def maxPagesP = handler.maxPagesP
		def maxPagesS = handler.maxPagesS
		def sampleRate = (int) maxSteps/width
		println("Beginning second pass")
		def handler2 = new PtraceSecondHandler(sampleRate)
		reader.setContentHandler(handler2)
		reader.parse(new InputSource(new FileInputStream(iFile)))
		
		def listP = handler2.wssListP
		def listS = handler2.wssListS
		println("Drawing graph")
		new PtraceDrawWSSGraph(listP, listS, width, height, marks, margins,
			maxSteps, maxPagesP, maxPagesS);
		
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

def width = 800
def height = 600
def marks = 4
def margins = 100

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

	def gWSS = new GraphWSS(args[args.size() -1], width, height, marks,
		margins)
}


	