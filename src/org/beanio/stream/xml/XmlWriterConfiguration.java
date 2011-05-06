/*
 * Copyright 2011 Kevin Seim
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.beanio.stream.xml;

import java.util.*;

/**
 * Stores XML writer configuration settings.
 * <p>
 * By default, indentation is disabled and no XML header will be written to an output stream.
 * 
 * @author Kevin Seim
 * @since 1.1
 * @see XmlWriter
 */
public class XmlWriterConfiguration implements Cloneable {

    private int indentation = -1;
    private String lineSeparator = null;
    
    private boolean headerEnabled = false;
    private String version = "1.0";
    private String encoding = "utf-8";
    
    /* Map of namespace prefixes to namespace uri's */
    private Map<String,String> namespaceMap = new HashMap<String,String>();
    
    /**
     * Constructs a new <tt>XmlWriterConfiguration</tt>.
     */
    public XmlWriterConfiguration() { }

    /**
     * Returns the number of spaces to indent each level of XML, or <tt>-1</tt>
     * if indentation is disabled.
     * @return the number of spaces to indent each level of XML, 
     *   or <tt>-1</tt> to disable indentation
     */
    public int getIndentation() {
        return indentation;
    }

    /**
     * Enables and sets the indentation level in spaces.  If set to <tt>-1</tt>
     * (the default value), indentation is disabled.
     * @param indentation the number of spaces to indent each level of XML, 
     *   or <tt>-1</tt> to disable indentation
     */
    public void setIndentation(int indentation) {
        this.indentation = indentation;
    }
    
    /**
     * Returns whether XML output will be indented.
     * @return <tt>true</tt> if indentation is enabled
     */
    public boolean isIndentationEnabled() {
        return indentation >= 0;
    }

    /**
     * Returns the text used to terminate a line when indentation is enabled. 
     * When set to <tt>null</tt> (the default), the line separator is set to the 
     * value of the <tt>line.separator</tt> system property.
     * @return the line separation text
     */
    public String getLineSeparator() {
        return lineSeparator;
    }

    /**
     * Sets the text used to terminate a line when indentation is enabled.  
     * When set to <tt>null</tt> (the default), the line separator is set to the 
     * value of the <tt>line.separator</tt> system property.
     * @param lineSeparator the line separation text
     */
    public void setLineSeparator(String lineSeparator) {
        this.lineSeparator = lineSeparator;
    }

    /**
     * Returns whether a XML header will be written to the stream.
     * @return <tt>true</tt> if a XML header will be written to the stream
     */
    public boolean isHeaderEnabled() {
        return headerEnabled;
    }

    /**
     * Sets whether a XML header will be written to the stream.
     * @param headerEnabled <tt>true</tt> to write a XML header to the stream
     */
    public void setHeaderEnabled(boolean headerEnabled) {
        this.headerEnabled = headerEnabled;
    }

    /**
     * Returns the XML version to include in the document header.
     * @return the XML version
     */
    public String getVersion() {
        return version;
    }

    /**
     * Sets the XML version to include in the document header.  Defaults to <tt>1.0</tt>.
     * May not be set to <tt>null</tt>.
     * @param version the XML version
     */
    public void setVersion(String version) {
        if (version == null) {
            throw new IllegalArgumentException("null version");
        }
        this.version = version;
    }

    /**
     * Returns the XML character encoding to include in the document header.
     * @return the XML character encoding
     */
    public String getEncoding() {
        return encoding;
    }

    /**
     * Sets the XML character encoding to include in the document header.  Defaults
     * to '<tt>utf-8</tt>'.  If set to <tt>null</tt> the document header will not
     * include the encoding setting. 
     * @param encoding the XML character encoding
     */
    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }
    
    /**
     * Adds a namespace to be set on the root element.
     * @param prefix the namespace prefix
     * @param uri the namespace URI
     */
    public void addNamespace(String prefix, String uri) {
        if (prefix == null) {
            throw new IllegalArgumentException("null prefix");
        }
        if (uri == null) {
            throw new IllegalArgumentException("null uri");
        }
        namespaceMap.put(uri, prefix);
    }
    
    /**
     * Sets the list of namespaces to be set on the root element.  The list should be formatted
     * as a space delimited list of alternating prefixes and uri's.  For example,
     * <pre>
     * setNamespaces("xsd http://www.w3.org/2001/XMLSchema b http://www.beanio.org/2011/01");
     * </pre>
     * @param list the space delimited list of namespaces
     */
    public void setNamespaces(String list) {
        namespaceMap.clear();
        
        if (list == null) {
            return;
        }

        String [] s = list.trim().split("\\s+");
        if (s.length % 2 != 0) {
            throw new IllegalArgumentException(
                "Invalid namespaces setting.  Must follow 'prefix uri prefix uri' pattern.");
        }
        
        for (int i=0; i<s.length; i+=2) {
            addNamespace(s[i+1], s[i]);
        }
    }
    
    /**
     * Returns a map of namespace URI's to prefixes to be set on the root element.
     * @return the map of namespace prefixes to URI's
     */
    public Map<String,String> getNamespaceMap() {
        return namespaceMap;
    }
    
    @Override
    protected XmlWriterConfiguration clone() {
        try {
            XmlWriterConfiguration config = (XmlWriterConfiguration) super.clone();
            return config;
        }
        catch (CloneNotSupportedException ex) {
            throw new IllegalStateException();
        }
    }
}
