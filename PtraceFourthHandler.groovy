import javax.xml.parsers.SAXParserFactory
import org.xml.sax.helpers.DefaultHandler
import org.xml.sax.*

class PtraceFourthHandler extends DefaultHandler {

	List softList = []
	List hardList = []
	def sampleRate
	def stepsMade = 0
	
	PtraceFourthHandler(def sampleRate)
	{
		super()
		this.sampleRate = sampleRate
	}
	
	/**
	 * Sample data points where appropriate
	 * @param ns
	 * @param localName
	 * @param qName
	 * @param attrs
	 */
	void startElement(String ns, String localName, String qName,
		Attributes attrs) {
			switch(qName) {
				case 'faults':
					stepsMade++
					if (stepsMade >= sampleRate){
						softList << Long.decode(attrs.getValue('soft'))
						hardList << Long.decode(attrs.getValue('hard'))
						stepsMade = 0
					}
			}
	}
	
}