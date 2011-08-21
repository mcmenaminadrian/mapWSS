import org.xml.sax.Attributes;


import javax.xml.parsers.SAXParserFactory
import org.xml.sax.helpers.DefaultHandler
import org.xml.sax.*

class PtraceSecondHandler extends DefaultHandler {

	List wssListP = []
	List wssListS = []
	List wssListM = []
	def sampleRate
	def stepsMade = 0
	
	PtraceSecondHandler(def sampleRate)
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
				case 'trace':
					stepsMade++
					if (stepsMade >= sampleRate){
						wssListP << Long.decode(attrs.getValue('present'))
						wssListS << Long.decode(attrs.getValue('swapped'))
						wssListM << Long.decode(attrs.getValue('presonly'))
						stepsMade = 0
					}
			}
	}
}
