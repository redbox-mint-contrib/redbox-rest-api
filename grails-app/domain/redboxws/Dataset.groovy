package redboxws

import grails.rest.*
import javassist.CtClass

@Resource(formats=['atom'])
class Dataset {

	String title
    String description
	Date dateCreated
	Date lastUpdated

	static constraints = {
		title blank:false
		description blank:false
    }
}
