
import javax.xml.parsers.SAXParserFactory
import org.xml.sax.helpers.DefaultHandler
import org.xml.sax.*

class PtraceThirdHandler extends DefaultHandler {

	def maxSoft = 0
	def maxHard = 0
	
	
	/**
	 * Look for the maxima of the hard and soft fault counts
	 * @param ns
	 * @param localName
	 * @param qName
	 * @param attrs
	 */
	void startElement(String ns, String localName, String qName,
		Attributes attrs) {
		
			switch(qName) {
				case 'faults':
				def soft = Long.decode(attrs.getValue('soft'))
				if (soft > maxSoft)
					maxSoft = soft
				def hard = Long.decode(attrs.getValue('hard'))
				if (hard > maxHard)
					maxHard = hard
			}
		}
}
