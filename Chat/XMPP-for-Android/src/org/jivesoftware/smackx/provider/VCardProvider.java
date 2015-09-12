/**
 * $RCSfile$
 * $Revision: 10858 $
 * $Date: 2008-10-30 23:04:15 -0500 (Thu, 30 Oct 2008) $
 *
 * Copyright 2003-2007 Jive Software.
 *
 * All rights reserved. Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jivesoftware.smackx.provider;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.provider.IQProvider;
import org.jivesoftware.smack.util.StringUtils;
import org.jivesoftware.smackx.packet.VCard;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xmlpull.v2.XmlPullParser;
import org.xmlpull.v2.XmlPullParserException;

/**
 * vCard provider.
 * 
 * @author Gaston Dombiak
 * @author Derek DeMoro
 */
public class VCardProvider implements IQProvider {

	private static class VCardReader {

		private final VCard vCard;
		private final Document document;

		VCardReader(VCard vCard, Document document) {
			this.vCard = vCard;
			this.document = document;
		}

		private void appendText(StringBuilder result, Node node) {
			final NodeList childNodes = node.getChildNodes();
			for (int i = 0; i < childNodes.getLength(); i++) {
				final Node nd = childNodes.item(i);
				final String nodeValue = nd.getNodeValue();
				if (nodeValue != null) {
					result.append(nodeValue);
				}
				appendText(result, nd);
			}
		}

		private String getTagContents(String tag) {
			final NodeList nodes = document.getElementsByTagName(tag);
			if (nodes != null && nodes.getLength() == 1) {
				return getTextContent(nodes.item(0));
			}
			return null;
		}

		private String getTextContent(Node node) {
			final StringBuilder result = new StringBuilder();
			appendText(result, node);
			return result.toString();
		}

		public void initializeFields() {
			vCard.setFirstName(getTagContents("GIVEN"));
			vCard.setLastName(getTagContents("FAMILY"));
			vCard.setMiddleName(getTagContents("MIDDLE"));
			vCard.setEncodedImage(getTagContents("BINVAL"));

			setupEmails();

			vCard.setOrganization(getTagContents("ORGNAME"));
			vCard.setOrganizationUnit(getTagContents("ORGUNIT"));

			setupSimpleFields();

			setupPhones();
			setupAddresses();
		}

		private boolean isWorkHome(String nodeName) {
			return "HOME".equals(nodeName) || "WORK".equals(nodeName);
		}

		private void setupAddresses() {
			final NodeList allAddresses = document.getElementsByTagName("ADR");
			if (allAddresses == null) {
				return;
			}
			for (int i = 0; i < allAddresses.getLength(); i++) {
				final Element addressNode = (Element) allAddresses.item(i);

				String type = null;
				final List<String> code = new ArrayList<String>();
				final List<String> value = new ArrayList<String>();
				final NodeList childNodes = addressNode.getChildNodes();
				for (int j = 0; j < childNodes.getLength(); j++) {
					final Node node = childNodes.item(j);
					if (node.getNodeType() != Node.ELEMENT_NODE) {
						continue;
					}
					final String nodeName = node.getNodeName();
					if (isWorkHome(nodeName)) {
						type = nodeName;
					} else {
						code.add(nodeName);
						value.add(getTextContent(node));
					}
				}
				for (int j = 0; j < value.size(); j++) {
					if ("HOME".equals(type)) {
						vCard.setAddressFieldHome(code.get(j), value.get(j));
					} else { // By default, setup work address
						vCard.setAddressFieldWork(code.get(j), value.get(j));
					}
				}
			}
		}

		private void setupEmails() {
			final NodeList nodes = document.getElementsByTagName("USERID");
			if (nodes == null) {
				return;
			}
			for (int i = 0; i < nodes.getLength(); i++) {
				final Element element = (Element) nodes.item(i);
				if ("WORK".equals(element.getParentNode().getFirstChild()
						.getNodeName())) {
					vCard.setEmailWork(getTextContent(element));
				} else {
					vCard.setEmailHome(getTextContent(element));
				}
			}
		}

		private void setupPhones() {
			final NodeList allPhones = document.getElementsByTagName("TEL");
			if (allPhones == null) {
				return;
			}
			for (int i = 0; i < allPhones.getLength(); i++) {
				final NodeList nodes = allPhones.item(i).getChildNodes();
				String type = null;
				String code = null;
				String value = null;
				for (int j = 0; j < nodes.getLength(); j++) {
					final Node node = nodes.item(j);
					if (node.getNodeType() != Node.ELEMENT_NODE) {
						continue;
					}
					final String nodeName = node.getNodeName();
					if ("NUMBER".equals(nodeName)) {
						value = getTextContent(node);
					} else if (isWorkHome(nodeName)) {
						type = nodeName;
					} else {
						code = nodeName;
					}
				}
				if (code == null || value == null) {
					continue;
				}
				if ("HOME".equals(type)) {
					vCard.setPhoneHome(code, value);
				} else { // By default, setup work phone
					vCard.setPhoneWork(code, value);
				}
			}
		}

		private void setupSimpleFields() {
			final NodeList childNodes = document.getDocumentElement()
					.getChildNodes();
			for (int i = 0; i < childNodes.getLength(); i++) {
				final Node node = childNodes.item(i);
				if (node instanceof Element) {
					final Element element = (Element) node;

					final String field = element.getNodeName();
					if (element.getChildNodes().getLength() == 0) {
						vCard.setField(field, "");
					} else if (element.getChildNodes().getLength() == 1
							&& element.getChildNodes().item(0) instanceof Text) {
						vCard.setField(field, getTextContent(element));
					}
				}
			}
		}
	}

	private static final String PREFERRED_ENCODING = "UTF-8";

	/**
	 * Builds a users vCard from xml file.
	 * 
	 * @param xml
	 *            the xml representing a users vCard.
	 * @return the VCard.
	 * @throws Exception
	 *             if an exception occurs.
	 */
	public static VCard createVCardFromXML(String xml) throws Exception {
		final VCard vCard = new VCard();

		final DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
				.newInstance();
		final DocumentBuilder documentBuilder = documentBuilderFactory
				.newDocumentBuilder();
		final Document document = documentBuilder
				.parse(new ByteArrayInputStream(xml
						.getBytes(PREFERRED_ENCODING)));

		new VCardReader(vCard, document).initializeFields();
		return vCard;
	}

	@Override
	public IQ parseIQ(XmlPullParser parser) throws Exception {
		final StringBuilder sb = new StringBuilder();
		try {
			int event = parser.getEventType();
			// get the content
			while (true) {
				switch (event) {
				case XmlPullParser.TEXT:
					// We must re-escape the xml so that the DOM won't throw an
					// exception
					sb.append(StringUtils.escapeForXML(parser.getText()));
					break;
				case XmlPullParser.START_TAG:
					sb.append('<').append(parser.getName()).append('>');
					break;
				case XmlPullParser.END_TAG:
					sb.append("</").append(parser.getName()).append('>');
					break;
				default:
				}

				if (event == XmlPullParser.END_TAG
						&& "vCard".equals(parser.getName())) {
					break;
				}

				event = parser.next();
			}
		} catch (final XmlPullParserException e) {
			e.printStackTrace();
		} catch (final IOException e) {
			e.printStackTrace();
		}

		final String xmlText = sb.toString();
		return createVCardFromXML(xmlText);
	}
}
