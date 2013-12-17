import grails.converters.XML
import redboxws.Dataset

class BootStrap {

    def init = { servletContext ->
		new Dataset(title:"Hello", description:"My First Dataset", dateCreated: 12-12-2013, lastUpdated: 12-12-2013).save()
		new Dataset(title:"Hello", description:"My Second Dataset", dateCreated: 12-12-2013, lastUpdated: 12-12-2013).save()
		//new Dataset(title:"Hello", description:"My First Dataset").save()
		//new Dataset(title:"Hello", description:"My Second Dataset").save()
		
		//		XML.registerObjectMarshaller Dataset, { Dataset dataset, XML xml ->
//			xml.attribute 'id', dataset.id
//			xml.build {
//			  title(dataset.title)
//			}
//		}
    }
    def destroy = {
    }
}
