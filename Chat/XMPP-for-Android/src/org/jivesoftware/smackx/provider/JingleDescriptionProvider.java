/**
 * $RCSfile: JingleDescriptionProvider.java,v $
 * $Revision: 1.1 $
 * $Date: 2007/07/02 17:41:11 $
 *
 * Copyright 2003-2005 Jive Software.
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

import org.jivesoftware.smack.packet.PacketExtension;
import org.jivesoftware.smack.provider.PacketExtensionProvider;
import org.jivesoftware.smackx.jingle.media.PayloadType;
import org.jivesoftware.smackx.packet.JingleDescription;
import org.xmlpull.v2.XmlPullParser;

/**
 * Parser for a Jingle description
 * 
 * @author Alvaro Saurin <alvaro.saurin@gmail.com>
 */
public abstract class JingleDescriptionProvider implements
		PacketExtensionProvider {

	/**
	 * Jingle audio
	 */
	public static class Audio extends JingleDescriptionProvider {

		/**
		 * Default constructor
		 */
		public Audio() {
			super();
		}

		/**
		 * Get a new instance of this object.
		 */
		@Override
		protected JingleDescription getInstance() {
			return new JingleDescription.Audio();
		}

		/**
		 * Parse an audio payload type.
		 */
		@Override
		public PayloadType parsePayload(final XmlPullParser parser)
				throws Exception {
			final PayloadType pte = super.parsePayload(parser);
			final PayloadType.Audio pt = new PayloadType.Audio(pte);
			int ptClockRate = 0;

			try {
				ptClockRate = Integer.parseInt(parser.getAttributeValue("",
						"clockrate"));
			} catch (final Exception e) {
			}
			pt.setClockRate(ptClockRate);

			return pt;
		}
	}

	/**
	 * Default constructor
	 */
	public JingleDescriptionProvider() {
		super();
	}

	/**
	 * Return a new instance of this class. Subclasses must overwrite this
	 * method.
	 */
	protected abstract JingleDescription getInstance();

	/**
	 * Parse a iq/jingle/description element.
	 * 
	 * @param parser
	 *            the input to parse
	 * @return a description element
	 * @throws Exception
	 */
	@Override
	public PacketExtension parseExtension(final XmlPullParser parser)
			throws Exception {
		boolean done = false;
		final JingleDescription desc = getInstance();

		while (!done) {
			final int eventType = parser.next();
			final String name = parser.getName();

			if (eventType == XmlPullParser.START_TAG) {
				if (name.equals(PayloadType.NODENAME)) {
					desc.addPayloadType(parsePayload(parser));
				} else {
					throw new Exception("Unknow element \"" + name
							+ "\" in content.");
				}
			} else if (eventType == XmlPullParser.END_TAG) {
				if (name.equals(JingleDescription.NODENAME)) {
					done = true;
				}
			}
		}
		return desc;
	}

	/**
	 * Parse a iq/jingle/description/payload-type element.
	 * 
	 * @param parser
	 *            the input to parse
	 * @return a payload type element
	 * @throws Exception
	 */
	protected PayloadType parsePayload(final XmlPullParser parser)
			throws Exception {
		int ptId = 0;
		String ptName;
		int ptChannels = 0;

		try {
			ptId = Integer.parseInt(parser.getAttributeValue("", "id"));
		} catch (final Exception e) {
		}

		ptName = parser.getAttributeValue("", "name");

		try {
			ptChannels = Integer.parseInt(parser.getAttributeValue("",
					"channels"));
		} catch (final Exception e) {
		}

		return new PayloadType(ptId, ptName, ptChannels);
	}
}
