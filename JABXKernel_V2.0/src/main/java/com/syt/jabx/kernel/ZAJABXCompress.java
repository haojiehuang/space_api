package com.syt.jabx.kernel;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;

/**
 * 資料壓縮及解壓縮介面
 * @author jason
 *
 */
abstract class ZAJABXCompress {
	
	/**
	 * 壓縮方式: 非壓縮格式
	 */
	public static final short NOCOMPRESS = 0;
	
	/**
	 * 壓縮方式: Huffman壓縮法
	 */
	public static final short HUFFMAN = 1;

	/**
	 * 原始之byte數
	 */
	protected int m_nRealBytes;
	
	/**
	 * 壓縮後之byte數
	 */
	protected int m_nCmpBytes;
	
	/**
	 * 壓縮方式
	 */
	protected short m_shType;
	
	/**
	 * True when compressed status.
	 */
	protected boolean m_bEncode = true;

	/**
	 * Input stream
	 */
	BufferedInputStream m_InStm;
	
	/**
	 * Output stream
	 */
	BufferedOutputStream m_OutStm;

	/**
	 * 取得實際之byte數
	 * @return int
	 */
	public int getRealSize() {
		return m_nRealBytes;
	}

	/**
	 * 取得壓縮後之byte數
	 * @return int
	 */
	public int getCompressedSize() {
		return m_nCmpBytes;
	}

	/**
	 * 取得壓縮方式
	 * @return short
	 */
	public short getCompressType() {
		return m_shType;
	}

	/**
	 * 設定壓縮方式
	 * @param shType - short
	 */
	public void setCompressType(short shType) {
		m_shType = shType;
	}

	/**
	 * 是否被Compressed
	 * @return boolean
	 */
	public boolean IsCompressed() {
		return m_bEncode;
	}

	/**
	 * 壓縮資料
	 */
	abstract protected void EnCode();
	
	/**
	 * 解壓縮資料
	 */
	abstract protected void DeCode();
}

