package org.grails.plugins.listbuilder

import org.codehaus.groovy.grails.plugins.web.taglib.JavascriptTagLib
import org.springframework.context.ApplicationContextAware
import org.springframework.context.ApplicationContext

class ListBuilderTagLib implements ApplicationContextAware {
    static namespace = "lstbld"
    static REQ_ATTRIB_RESOURCES_LOADED = "listbuilder.resources.loaded"


    ApplicationContext applicationContext

    /**
     * Tag to pull in the CSS & Javascript
     */
    def resources = { attrs ->

        log.debug "in the resources"
        
        if ((attrs.override != 'true') && !Boolean.valueOf(attrs.override)) {
            out << "<link rel=\"stylesheet\" type=\"text/css\" href=\"${resource(plugin:'listbuilder', dir:'css', file:'list-builder.css')}\"/>"
        }
        request[REQ_ATTRIB_RESOURCES_LOADED] = true

        if (!request[JavascriptTagLib.INCLUDED_LIBRARIES]) {
            request[JavascriptTagLib.INCLUDED_LIBRARIES] = []
        }

        log.debug "JavascriptTagLib.LIBRARY_MAPPINGS:${JavascriptTagLib.LIBRARY_MAPPINGS}"
        /*
        if (!request[JavascriptTagLib.INCLUDED_LIBRARIES].contains("jquery")) {
            out << '<script type="text/javascript" src="' +
            g.resource(dir: 'js', file: JavascriptTagLib.LIBRARY_MAPPINGS.jquery[0] + '.js') + '"></script>'
        }

        log.debug "pluginContextPath:${pluginContextPath}"
*/



        out << '<script type="text/javascript" src="' +
        g.resource(dir: pluginContextPath, file:'js/jquery-1.2.6.min.js') + '"></script>'

        out << '<script type="text/javascript" src="' +
        g.resource(dir: pluginContextPath, file:'js/list-builder.js') + '"></script>'
    }


    def listBuilder = { attrs ->

        def from = attrs.from
      
        def optionKey = attrs.optionKey
        def optionValue = attrs.optionValue ?: optionKey
        def size = attrs.size ?: 10


        out << "<fieldset>\n"
        out << "<input type='hidden' name='listBuilderState' id='listBuilderState'/>\n"
        out << "<div>\n"
        out << "<div id='leftButtons'>\n"
        out << "<button type='button' id='leftUpButton' class='button'>+</button>\n"
        out << "<button type='button' id='leftDownButton' class='button'>-</button>\n"
        out << "</div>\n"
  

        out <<"<div id='leftList'>\n"
        renderSelect("leftSelect",from,optionKey,optionValue,size)
        out <<"</div>\n"

        out <<"<div id='middleButtons'>\n"
        out <<"<div id='addRemoveButtons'>\n"
        out <<"<button type='button' id='addButton' class='button'>></button>\n"
        out <<"<button type='button' id='removeButton' class='button'><</button>\n"
        out <<"</div>\n"
        out <<"<div id='addAllRemoveAllButtons'>\n"
        out <<"<button type='button' id='addAllButton' class='button'>>></button>\n"
        out <<"<button type='button' id='removeAllButton' class='button'><<</button>\n"
        out <<"</div>\n"
        out <<"</div>\n"
        out <<"<div id='rightList'>\n"
        out <<"<select id='rightSelect' size='10' multiple='multiple'>\n"
        out <<"</select>\n"
        out <<"</div>\n"
        out <<"<div id='rightButtons'>\n"
        out <<"<button type='button' id='rightUpButton' class='button'>+</button>\n"
        out <<"<button type='button' id='rightDownButton' class='button'>-</button>\n"
        out <<"</div>\n"
        out <<"</div>\n"
        out <<"</fieldset>\n"

    }



    def private renderSelect(theName,theList,theOption,theValue,theSize) {

        out <<"${select(name:theName,from:theList,optionKey:theOption,optionValue:theValue,size:theSize,multiple:'multiple')}\n"

    }

}
