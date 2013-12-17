package redboxws
import java.io.Serializable;
import java.util.Map;
import java.util.Map.Entry;

import grails.rest.render.*
import grails.rest.render.atom.*
import grails.converters.XML
import grails.rest.Link
import grails.rest.render.RenderContext
import grails.rest.render.hal.HalXmlRenderer
import groovy.transform.CompileStatic
import groovy.transform.TypeCheckingMode
import org.codehaus.groovy.grails.web.mime.MimeType

import org.codehaus.groovy.grails.web.xml.PrettyPrintXMLStreamWriter
import org.codehaus.groovy.grails.web.xml.StreamingMarkupWriter
import org.codehaus.groovy.grails.web.xml.XMLStreamWriter
import org.grails.datastore.mapping.model.PersistentEntity
import org.grails.datastore.mapping.model.types.ToOne
import org.springframework.http.HttpMethod
import java.text.SimpleDateFormat

import org.codehaus.groovy.grails.web.mime.MimeType

class DatasetRenderer extends AtomRenderer<Dataset> {
	DatasetRenderer()  {
		super(Dataset)
	}	

	@Override
	void renderInternal(Dataset dataset, RenderContext context) {
		Map properties = new HashMap()
		properties.put("location", "Australia")
		properties.put("name", "RedboxRESTFULService")
		final streamingWriter = new StreamingMarkupWriter(context.writer, encoding)
		XMLStreamWriter w = prettyPrint ? new PrettyPrintXMLStreamWriter(streamingWriter) : new XMLStreamWriter(streamingWriter)
		XML xml = new XML(w)
		final entity = mappingContext.getPersistentEntity(dataset.class.name)
		boolean isDomain = entity != null
		Set writtenObjects = []
		w.startDocument(encoding, "1.0")
		if (isDomain) {
			writeDomainWithEmbeddedAndLinks(entity, dataset, context, xml, writtenObjects, properties)
		} else if (dataset instanceof Collection) {
			final locale = context.locale
			String resourceHref = linkGenerator.link(uri: context.resourcePath, method: HttpMethod.GET, absolute:true)
			final title = getResourceTitle(context.resourcePath, locale)
			XMLStreamWriter writer = xml.getWriter()
			writer
				.startNode(FEED_TAG)
				.attribute(XMLNS_ATTRIBUTE, ATOM_NAMESPACE)
					.startNode(TITLE_ATTRIBUTE)
					.characters(title)
					.end()
					.startNode(ID_TAG)
					.characters(generateIdForURI(resourceHref))
					.end()

			def linkSelf = new Link(RELATIONSHIP_SELF, resourceHref)
			linkSelf.title = title
			linkSelf.contentType=mimeTypes[0].name
			linkSelf.hreflang = locale
			writeLink(linkSelf, locale, xml)
			def linkAlt = new Link(RELATIONSHIP_ALTERNATE, resourceHref)
			linkAlt.title = title
			linkAlt.hreflang = locale
			writeLink(linkAlt, locale, xml)
    		for (o in ((Collection) object)) {
				final currentEntity = mappingContext.getPersistentEntity(o.class.name)
				if (currentEntity) {
					writeDomainWithEmbeddedAndLinks(currentEntity, o, context, xml, writtenObjects, false)
				} else {
					throw new IllegalArgumentException("Cannot render object [$o] using Atom. The AtomRenderer can only be used with domain classes that specify 'dateCreated' and 'lastUpdated' properties")
				}
			}
			writer.end()
		} else {
			throw new IllegalArgumentException("Cannot render object [$object] using Atom. The AtomRenderer can only be used with domain classes that specify 'dateCreated' and 'lastUpdated' properties")
		}

	}
	
	
	protected void writeDomainWithEmbeddedAndLinks(PersistentEntity entity, Object object, RenderContext context, XML xml, Set writtenObjects, boolean isFirst = true, Map properties) {
		if (!entity.getPropertyByName('lastUpdated')) {
			throw new IllegalArgumentException("Cannot render object [$object] using Atom. The AtomRenderer can only be used with domain classes that specify 'dateCreated' and 'lastUpdated' properties")
		}
		final locale = context.locale
		String resourceHref = linkGenerator.link(resource: object, method: HttpMethod.GET, absolute:true)
		final title = getLinkTitle(entity, locale)
		XMLStreamWriter writer = xml.getWriter()
		writer.startNode(isFirst ? FEED_TAG : ENTRY_TAG)
		if (isFirst) {
			writer.attribute(XMLNS_ATTRIBUTE, ATOM_NAMESPACE)
		}
		if (!entity.getPropertyByName(TITLE_ATTRIBUTE)) {
			writer.startNode(TITLE_ATTRIBUTE)
				.characters(object.toString())
				.end()
		}
		final dateCreated = formatDateCreated(object)
		if (dateCreated) {

			writer.startNode(PUBLISHED_TAG)
				.characters(dateCreated)
				.end()
		}
		final lastUpdated = formatLastUpdated(object)
		if (lastUpdated) {
			writer.startNode(UPDATED_TAG)
				.characters(lastUpdated)
				.end()
		}
		writer.startNode(ID_TAG)
			.characters(getObjectId(entity, object))
			.end()
		
		for(Map.Entry<String, String> entry :properties.entrySet()){
			   writer.startNode(entry.getKey())
			   .characters(entry.getValue())
			   .end()
		   }			
	  	def linkSelf = new Link(RELATIONSHIP_SELF, resourceHref)
		linkSelf.title = title
		linkSelf.contentType=mimeTypes[0].name
		linkSelf.hreflang = locale
		writeLink(linkSelf, locale, xml)
		def linkAlt = new Link(RELATIONSHIP_ALTERNATE, resourceHref)
		linkAlt.title = title
		linkAlt.hreflang = locale

		writeLink(linkAlt,locale, xml)
		final metaClass = GroovySystem.metaClassRegistry.getMetaClass(entity.javaClass)
		final associationMap = writeAssociationLinks(context,object, locale, xml, entity, metaClass)
		writeDomain(context, metaClass, entity, object, xml)

		if (associationMap) {
			for (entry in associationMap.entrySet()) {
				final property = entry.key
				final isSingleEnded = property instanceof ToOne
				if (isSingleEnded) {
					Object value = entry.value
					if (writtenObjects.contains(value)) {
						continue
					}

					if (value != null) {
						final associatedEntity = property.associatedEntity
						if (associatedEntity) {
							writtenObjects << value
							writeDomainWithEmbeddedAndLinks(associatedEntity, value, context, xml, writtenObjects, false)
						}
					}
				} else {
					final associatedEntity = property.associatedEntity
					if (associatedEntity) {
						writer.startNode(property.name)
						for (obj in entry.value) {
							writtenObjects << obj
							writeDomainWithEmbeddedAndLinks(associatedEntity, obj, context, xml, writtenObjects, false)
						}
						writer.end()
					}
				}
			}
		}
		writer.end()
	}
}