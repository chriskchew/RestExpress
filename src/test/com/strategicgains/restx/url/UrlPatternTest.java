/*
    Copyright 2010, Strategic Gains, Inc.

	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at

		http://www.apache.org/licenses/LICENSE-2.0

	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
*/
package com.strategicgains.restx.url;

import static org.junit.Assert.*;

import org.junit.Test;


/**
 * @author toddf
 * @since Apr 28, 2010
 */
public class UrlPatternTest
{
	private UrlPattern p = new UrlPattern("/xxx/{a_id}/yyy/{b_id}");
	private UrlPattern pFormat = new UrlPattern("/xxx/{a_id}/yyy/{b_id}.{format}");

	@Test
	public void shouldMatchUrlWithFormat()
	{
		assertNotNull(pFormat.matches("/xxx/toddf/yyy/joez.json"));
		assertNotNull(pFormat.matches("/xxx/12345/yyy/67890.json"));
		assertNotNull(pFormat.matches("/xxx/toddf/yyy/joez.xml"));
		assertNotNull(pFormat.matches("/xxx/toddf/yyy/joez.json?x=y&a=b"));
		assertNotNull(pFormat.matches("/xxx/toddf/yyy/joez"));
	}

	@Test
	public void shouldMatchUrlWithoutFormat()
	{
		assertNotNull(p.matches("/xxx/toddf/yyy/joez"));
		assertNotNull(p.matches("/xxx/12345/yyy/67890"));
		assertNotNull(p.matches("/xxx/toddf/yyy/joez"));
		assertNotNull(p.matches("/xxx/toddf/yyy/joez?x=y&a=b"));
	}

	@Test
	public void shouldNotMatchUrlWithFormat()
	{
		assertNull(pFormat.matches("/xxx/toddf/yyy/joez/"));
		assertNull(pFormat.matches("/xxx/toddf/yyy/joez."));
		assertNull(pFormat.matches("/aaa/toddf/yyy/joez.json"));
		assertNull(pFormat.matches("/xxx/toddf/bbb/joez.json"));
		assertNull(pFormat.matches("/xxx/toddf/yyy/"));
		assertNull(pFormat.matches("/xxx/toddf/yyy"));
	}

	@Test
	public void shouldNotMatchUrlWithoutFormat()
	{
		assertNull(p.matches("/xxx/toddf/yyy/joez/"));
		assertNull(p.matches("/xxx/toddf/yyy/joez."));
		assertNull(p.matches("/aaa/toddf/yyy/joez.json"));
		assertNull(p.matches("/xxx/toddf/bbb/joez.json"));
		assertNull(p.matches("/xxx/toddf/yyy/"));
		assertNull(p.matches("/xxx/toddf/yyy"));
	}

	@Test
	public void shouldParseParametersWithFormat()
	{
		UrlMatch match = pFormat.matches("/xxx/12345/yyy/67890.json");
		assertNotNull(match);
		assertEquals("json", match.get("format"));
		assertEquals("12345", match.get("a_id"));
		assertEquals("67890", match.get("b_id"));
	}

	@Test
	public void shouldParseParametersWithoutFormat()
	{
		UrlMatch match = p.matches("/xxx/12345/yyy/67890");
		assertNotNull(match);
		assertNull(match.get("format"));
		assertEquals("12345", match.get("a_id"));
		assertEquals("67890", match.get("b_id"));
	}

	@Test
	public void shouldParseParametersWithQueryStringAndFormat()
	{
		UrlMatch match = pFormat.matches("/xxx/12345/yyy/67890.json?x=y&a=b");
		assertNotNull(match);
		assertEquals("json", match.get("format"));
		assertEquals("12345", match.get("a_id"));
		assertEquals("67890", match.get("b_id"));
	}

	@Test
	public void shouldParseParametersWithQueryStringAndWithoutFormat()
	{
		UrlMatch match = p.matches("/xxx/12345/yyy/67890?x=y&a=b");
		assertNotNull(match);
		assertNull(match.get("format"));
		assertEquals("12345", match.get("a_id"));
		assertEquals("67890", match.get("b_id"));
	}
}