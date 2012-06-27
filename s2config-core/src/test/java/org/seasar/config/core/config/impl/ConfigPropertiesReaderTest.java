package org.seasar.config.core.config.impl;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seasar.framework.unit.Seasar2;

@RunWith(Seasar2.class)
public class ConfigPropertiesReaderTest {
	private ConfigPropertiesReader configPropertiesReader;

	@Test
	public void testToMap() {
		configPropertiesReader.open("test");
		Map<String, Object> map = configPropertiesReader.toMap();
		assertEquals("abc", map.get("test"));
		assertEquals("true", map.get("app.debug"));
		assertEquals("100", map.get("xxx"));
		configPropertiesReader.close();
	}

	@Test
	public void testLoad() {
		configPropertiesReader.open("test");
		Map<String, Object> map = configPropertiesReader.toMap();
		configPropertiesReader.close();
		assertEquals("abc", map.get("test"));
		assertEquals("true", map.get("app.debug"));
		assertEquals("100", map.get("xxx"));
		configPropertiesReader.open("testNew");
		configPropertiesReader.load(map);
		Map<String, Object> map2 = configPropertiesReader.toMap();
		assertEquals("abc", map2.get("test"));
		assertEquals("true", map2.get("app.debug"));
		assertEquals("100", map2.get("xxx"));
		configPropertiesReader.close();
	}

	@Test
	public void testReadConfigValue() {
		configPropertiesReader.open("test");
		int ret =
			configPropertiesReader.readConfigValue(Integer.class, "xxx", 0);
		configPropertiesReader.close();
		assertEquals(100, ret);
	}

	/**
	 * isOpenedがTrueを返す場合のテスト
	 */
	@Test
	public void testIsOpenedがTrueを返す場合のテスト(){
		configPropertiesReader.open("test");
		assertTrue(configPropertiesReader.isOpened());
	}

	/**
	 * isOpenedがFalseを返す場合のテスト
	 */
	@Test
	public void testIsOpenedがFalseを返す場合のテスト(){
		configPropertiesReader.open("test");
		configPropertiesReader.close();
		assertFalse(configPropertiesReader.isOpened());
	}

	/**
	 * propertyがnullの時、readConfigValueがデフォルトの値（null）を返す場合
	 */
	@Test
	public void testConfigValue_PropertyNull(){
		Object object = configPropertiesReader.readConfigValue(null, null);
		assertNull(object);
	}
}
