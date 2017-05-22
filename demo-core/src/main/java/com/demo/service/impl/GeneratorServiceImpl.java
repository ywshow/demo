package com.demo.service.impl;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.dao.GeneratorDao;
import com.demo.service.GeneratorService;
import com.demo.util.generator.GenUtils;

/**
 * 代码生成器
 * 
 * @author ywshow
 *
 */
@Service
public class GeneratorServiceImpl implements GeneratorService {
	
	@Autowired
	private GeneratorDao generatorDao;

	/**
	 * 列表
	 */
	public List<Map<String, Object>> queryList(Map<String, Object> map) {
		return generatorDao.queryList(map);
	}

	/**
	 * 统计
	 */
	public int queryTotal(Map<String, Object> map) {
		return generatorDao.queryTotal(map);
	}

	/**
	 * 表查询
	 */
	public Map<String, String> queryTable(String tableName) {
		return generatorDao.queryTable(tableName);
	}

	/**
	 * 列查询
	 */
	public List<Map<String, String>> queryColumns(String tableName) {
		return generatorDao.queryColumns(tableName);
	}

	/**
	 * 代码生成
	 */
	public byte[] generatorCode(String[] tableNames) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ZipOutputStream zip = new ZipOutputStream(outputStream);

		for (String tableName : tableNames) {
			// 查询表信息
			Map<String, String> table = queryTable(tableName);
			// 查询列信息
			List<Map<String, String>> columns = queryColumns(tableName);
			// 生成代码
			GenUtils.generatorCode(table, columns, zip);
		}
		IOUtils.closeQuietly(zip);
		return outputStream.toByteArray();
	}

}
