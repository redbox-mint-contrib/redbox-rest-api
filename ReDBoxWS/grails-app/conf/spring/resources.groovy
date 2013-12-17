
import redboxws.Dataset
import grails.rest.render.json.*
import org.codehaus.groovy.grails.web.mime.*
import grails.rest.render.atom.*
import redboxws.DatasetRenderer
	beans = {
		//datasetRendererV1(JsonRenderer, ReDBoxWS.v1.Dataset, new MimeType("application/vnd.datasets.org.dataset+json", [v:"1.0"]))
		//datasetRendererV2(JsonRenderer, ReDBoxWS.v2.Dataset, new MimeType("application/vnd.datasets.org.dataset+json", [v:"2.0"]))
		halDatasetRenderer(DatasetRenderer)
		}
	//halDatasetRenderer(HalJsonRenderer, Dataset)
	
	

//		datasetRenderer(XmlRenderer, Dataset) {
//           includes = ['title']
//		}
//		beans = {
			//halDatasetRenderer(HalJsonRenderer, Dataset)
//		}
