import org.xml.sax.Attributes;


import javax.xml.parsers.SAXParserFactory
import org.xml.sax.helpers.DefaultHandler
import org.xml.sax.*

class PtraceFirstHandler extends DefaultHandler {

	def maxSteps = 0
	def maxPagesP = 0
	def maxPagesR = 0
	def maxPagesS = 0
	def maxPagesM = 0
	
	/**
	 * Simple, slow scan through the ptracexml file - checking maxima
	 * @param ns
	 * @param localName
	 * @param qName
	 * @param attrs
	 */
	void startElement(String ns, String localName, String qName,
		Attributes attrs) {
		
			switch(qName) {
				case 'trace':
				def steps = Long.decode(attrs.getValue('steps'))
				if (steps > maxSteps)
					maxSteps = steps
				def pagesP = Long.decode(attrs.getValue('present'))
				if (pagesP > maxPagesP)
					maxPagesP = pagesP
				def pagesS = Long.decode(attrs.getValue('swapped'))
				if (pagesS > maxPagesS)
					maxPagesS = pagesS
<<<<<<< HEAD
				def pagesR = Long.decode(attrs.getValue('presonly'))
				if (pagesR > maxPagesR)
					maxPagesR = pagesR
=======
				def pagesM = Long.decode(attrs.getValue('presonly'))
				if (pagesM > maxPagesM)
					maxPagesM = pagesM
>>>>>>> 5cde9ec0623ada4a2613e68eb84cb0846b841ef2
			}
	}
}
