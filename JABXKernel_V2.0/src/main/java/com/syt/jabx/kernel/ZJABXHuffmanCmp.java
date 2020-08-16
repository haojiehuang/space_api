package com.syt.jabx.kernel;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

class Node {
	int m_Weight;
	int m_Parent;
	int m_ChildIsLeaf;
	int m_Child;
};

class Tree {
	final short SYMBOL_COUNT = 258;
	final int NODE_TABLE_COUNT = ((SYMBOL_COUNT * 2) - 1);
	final static int MAX_WEIGHT = 0x8000;

	int m_Leaf[] = new int[SYMBOL_COUNT];
	int m_NextFreeNode;
	Node m_nodes[] = new Node[NODE_TABLE_COUNT];

	Tree() {
		for (int n = 0;n < m_nodes.length;n++) {
			m_nodes[n] = new Node();
		}
	}
};

/**
 * Huffman解壓縮法之最終類別
 * @author jason
 *
 */
final class ZJABXHuffmanCmp extends ZAJABXCompress {
	static final int BUFFERED_SIZE = 512;
	final short ROOT_NODE = 0;
    final short END_OF_STREAM = 256;
    final short ESCAPE = 257;

    final byte YESCHILD = 1;
    final byte NOCHILD = 0;

    short m_shRack, m_shMask;
    Tree m_Tree = new Tree();

    public ZJABXHuffmanCmp(byte[] abyInBuf, int nBufSize, byte[][] abyOutBuf) throws IOException, IllegalArgumentException {
    	m_bEncode = true;
    	m_nRealBytes = nBufSize;

    	if (abyInBuf == null || abyOutBuf == null)
    		throw new IllegalArgumentException();

    	ByteArrayOutputStream oStm = new ByteArrayOutputStream();

    	m_InStm = new BufferedInputStream(new ByteArrayInputStream(abyInBuf), BUFFERED_SIZE);
    	m_OutStm = new BufferedOutputStream(oStm, BUFFERED_SIZE);
    	EnCode();

    	try {
    		m_OutStm.flush();
    		abyOutBuf[0] = oStm.toByteArray();
    	}catch(IOException e) {
    		throw e;
    	}
    }

    public ZJABXHuffmanCmp(byte[] abyInBuf, int nCmpBufSize, int nBufSize, byte[][] abyOutBuf) throws IOException, IllegalArgumentException {
    	m_bEncode = false;
    	m_nRealBytes = nBufSize;
    	m_nCmpBytes = nCmpBufSize;

    	if (abyInBuf == null || abyOutBuf == null) {
    		throw new IllegalArgumentException();
    	}

    	ByteArrayOutputStream oStm = new ByteArrayOutputStream();

    	m_InStm = new BufferedInputStream(new ByteArrayInputStream(abyInBuf), BUFFERED_SIZE);
    	m_OutStm = new BufferedOutputStream(oStm, BUFFERED_SIZE);
    	DeCode();

    	try {
    		m_OutStm.flush();
    		abyOutBuf[0] = oStm.toByteArray();
    	}catch(IOException e) {
    		throw e;
    	}
    }

    public ZJABXHuffmanCmp(String strInFile, String strOutFile, boolean bEncode) throws IOException, FileNotFoundException {
    	m_shType = ZAJABXCompress.HUFFMAN;
    	m_bEncode = bEncode;
    	FileInputStream  InFStm;
    	FileOutputStream OutFStm;

    	try {
    		InFStm = new FileInputStream(strInFile);
    	}catch(FileNotFoundException e) {
    		throw e;
    	}

    	try {
    		OutFStm = new FileOutputStream(strOutFile);
    	}catch(IOException e) {
    		throw e;
    	}

    	try {
    		m_InStm = new BufferedInputStream(InFStm, BUFFERED_SIZE);
    		m_OutStm = new BufferedOutputStream(OutFStm, BUFFERED_SIZE);

    		if (bEncode) {
    			DataOutputStream OutDStm = new DataOutputStream(m_OutStm);

    			m_nRealBytes = InFStm.available();
    			OutDStm.writeShort(m_shType);
    			OutDStm.writeInt(m_nRealBytes);
    			EnCode();
    		}else {
    			DataInputStream InDStm = new DataInputStream(m_InStm);
    			short shType = InDStm.readShort();

    			if (shType == ZAJABXCompress.HUFFMAN) {
    				m_nRealBytes = InDStm.readInt();								  
    				DeCode();
    			}
    		}//End if (strFlags.compareTo("c") == 0)

    		m_OutStm.flush();
    		InFStm.close();
    		OutFStm.close();
    	}catch(IOException e) {
    		throw e;
    	}

    }

    private void InitializeTree() {
    	short i;
        m_Tree = new Tree();

        m_Tree.m_nodes[ROOT_NODE].m_Child = ROOT_NODE + 1;
        m_Tree.m_nodes[ROOT_NODE].m_ChildIsLeaf = NOCHILD;
        m_Tree.m_nodes[ROOT_NODE].m_Weight = 2;
        m_Tree.m_nodes[ROOT_NODE].m_Parent = -1;

        m_Tree.m_nodes[ROOT_NODE+1].m_Child = END_OF_STREAM;
        m_Tree.m_nodes[ROOT_NODE+1].m_ChildIsLeaf = YESCHILD;
        m_Tree.m_nodes[ROOT_NODE+1].m_Weight = 1;
        m_Tree.m_nodes[ROOT_NODE+1].m_Parent = ROOT_NODE;
        m_Tree.m_Leaf[END_OF_STREAM] = ROOT_NODE + 1;

        m_Tree.m_nodes[ROOT_NODE+2].m_Child = ESCAPE;
        m_Tree.m_nodes[ROOT_NODE+2].m_ChildIsLeaf = YESCHILD;
        m_Tree.m_nodes[ROOT_NODE+2].m_Weight = 1;
        m_Tree.m_nodes[ROOT_NODE+2].m_Parent = ROOT_NODE;

        m_Tree.m_Leaf[ESCAPE] = ROOT_NODE + 2;
        m_Tree.m_NextFreeNode = ROOT_NODE + 3;

        for (i = 0;i < END_OF_STREAM;i++) {
        	m_Tree.m_Leaf[i] = -1;
        }

    }

    private short OutputBits(int nCode, short shCount, int nPos) {
    	int   nMask;
    	short shOutBytes = 0;

    	nMask = 1 << ( shCount - 1 );

    	try {
    		while (nMask != 0) {
    			if ((nMask & nCode) != 0) {
    				m_shRack |= m_shMask;
    			}

    			m_shMask >>= 1;
    	
    			if (m_shMask == 0) {
					m_OutStm.write((byte)m_shRack);
					shOutBytes++;
					m_shRack = 0;
					m_shMask = 0x0080;
				}//if (!m_shMask)
		
    			nMask >>= 1;
    		}//while (nMask)
    	}catch(IOException e) {    		
    		e.printStackTrace();
    	}

    	return shOutBytes;

    }

    private void AddNewNode(short shSymbol) {
    	short LightestNode = (short)(m_Tree.m_NextFreeNode - 1);
    	short NewNode = (short)m_Tree.m_NextFreeNode;
    	short ZeroWeightNode = (short)(m_Tree.m_NextFreeNode + 1);

    	m_Tree.m_NextFreeNode += 2;
    	m_Tree.m_nodes[NewNode].m_Child	= m_Tree.m_nodes[LightestNode].m_Child;
    	m_Tree.m_nodes[NewNode].m_ChildIsLeaf = m_Tree.m_nodes[LightestNode].m_ChildIsLeaf;
    	m_Tree.m_nodes[NewNode].m_Parent = m_Tree.m_nodes[LightestNode].m_Parent;
    	m_Tree.m_nodes[NewNode].m_Weight =  m_Tree.m_nodes[LightestNode].m_Weight;
    	m_Tree.m_nodes[NewNode].m_Parent = LightestNode;
    	m_Tree.m_Leaf[m_Tree.m_nodes[NewNode].m_Child] = NewNode;

    	m_Tree.m_nodes[LightestNode].m_Child = NewNode;
    	m_Tree.m_nodes[LightestNode].m_ChildIsLeaf = NOCHILD;

    	m_Tree.m_nodes[ZeroWeightNode].m_Child = shSymbol;
    	m_Tree.m_nodes[ZeroWeightNode].m_ChildIsLeaf = YESCHILD;
    	m_Tree.m_nodes[ZeroWeightNode].m_Weight = 0;
    	m_Tree.m_nodes[ZeroWeightNode].m_Parent = LightestNode;
    	m_Tree.m_Leaf[shSymbol] = ZeroWeightNode;
    }

    private short EncodeSymbol (short shSymbol, int nPos) {    	
    	short shOutBytes = 0;
    	int nCode = 0;
    	int nCurrentBit = 1;
    	short shCodeSize = 0;
    	short shCurrentNode = (short)m_Tree.m_Leaf[shSymbol];

    	if (shCurrentNode == -1) {
    		shCurrentNode = (short)m_Tree.m_Leaf[ESCAPE];
    	} 

    	while (shCurrentNode != ROOT_NODE) {
    		if ((shCurrentNode & 1) == 0) {
    			nCode |= nCurrentBit;
    		}

    		nCurrentBit <<= 1;
    		shCodeSize++;
    		shCurrentNode = (short)m_Tree.m_nodes[shCurrentNode].m_Parent;
    	}//while (shCurrentNode != ROOT_NODE);

    	shOutBytes += OutputBits(nCode, shCodeSize, nPos);

    	if (m_Tree.m_Leaf[shSymbol] == -1) {
    		shOutBytes += OutputBits ((int)shSymbol, (short)8, (int)(nPos + shOutBytes));

    		if (shSymbol >= 0 && shSymbol < 256 ) {
    			AddNewNode (shSymbol);
    		}
    	}//if (m_Tree.m_Leaf[shSymbol] == -1)

    	return shOutBytes;
    }

    private void SwapNodes(short i, short j) {
    	if (m_Tree.m_nodes[i].m_ChildIsLeaf == YESCHILD) {
    		m_Tree.m_Leaf[m_Tree.m_nodes[i].m_Child] = j;
    	}else {
    		m_Tree.m_nodes[m_Tree.m_nodes[i].m_Child].m_Parent = j;
    		m_Tree.m_nodes[m_Tree.m_nodes[i].m_Child+1].m_Parent = j;
    	}

    	if (m_Tree.m_nodes[j].m_ChildIsLeaf == YESCHILD) {
    		m_Tree.m_Leaf[m_Tree.m_nodes[j].m_Child] = i;
    	}else {
    		m_Tree.m_nodes[m_Tree.m_nodes[j].m_Child].m_Parent = i;
    		m_Tree.m_nodes[m_Tree.m_nodes[j].m_Child+1].m_Parent = i;
    	}

    	Node temp = new Node();

    	temp.m_Weight = m_Tree.m_nodes[i].m_Weight;
    	temp.m_Parent = m_Tree.m_nodes[i].m_Parent;
    	temp.m_ChildIsLeaf = m_Tree.m_nodes[i].m_ChildIsLeaf;
    	temp.m_Child = m_Tree.m_nodes[i].m_Child;

    	m_Tree.m_nodes[i].m_Weight = m_Tree.m_nodes[j].m_Weight;
    	m_Tree.m_nodes[i].m_ChildIsLeaf = m_Tree.m_nodes[j].m_ChildIsLeaf;
    	m_Tree.m_nodes[i].m_Child = m_Tree.m_nodes[j].m_Child;
    	m_Tree.m_nodes[i].m_Parent = temp.m_Parent;
    	temp.m_Parent = m_Tree.m_nodes[j].m_Parent;

    	m_Tree.m_nodes[j].m_Weight = temp.m_Weight;
    	m_Tree.m_nodes[j].m_Parent = temp.m_Parent;
    	m_Tree.m_nodes[j].m_ChildIsLeaf = temp.m_ChildIsLeaf;
    	m_Tree.m_nodes[j].m_Child = temp.m_Child;
    	
    	temp = null;
    }

    void RebuildTree() {
    	    
    	int i, j, k;
    	int nWeight;

    	j = m_Tree.m_NextFreeNode - 1;

    	for (i = j;i >= ROOT_NODE;i--) {
    		if (m_Tree.m_nodes[i].m_ChildIsLeaf == YESCHILD) {
    			m_Tree.m_nodes[j].m_Parent = m_Tree.m_nodes[i].m_Parent;
    			m_Tree.m_nodes[j].m_Child = m_Tree.m_nodes[i].m_Child;
    			m_Tree.m_nodes[j].m_ChildIsLeaf = m_Tree.m_nodes[i].m_ChildIsLeaf;
    			m_Tree.m_nodes[j].m_Weight = m_Tree.m_nodes[i].m_Weight;
    			m_Tree.m_nodes[j].m_Weight = (m_Tree.m_nodes[j].m_Weight + 1 ) >> 1;
    			j--;
    		}
    	}//for (i = j;i >= ROOT_NODE;i--)

    	for (i = m_Tree.m_NextFreeNode-2;j >= ROOT_NODE;i -= 2, j--) {
    		k = i + 1;
    		m_Tree.m_nodes[j].m_Weight = m_Tree.m_nodes[i].m_Weight + m_Tree.m_nodes[k].m_Weight;
    		nWeight = m_Tree.m_nodes[j].m_Weight;
    		m_Tree.m_nodes[j].m_ChildIsLeaf = NOCHILD;

    		for (k = j + 1;nWeight < m_Tree.m_nodes[k].m_Weight;k++);

    		k--;

    		for (int n = j;n < k;n++) {
    			m_Tree.m_nodes[n].m_Weight = m_Tree.m_nodes[n+1].m_Weight;
    			m_Tree.m_nodes[n].m_Parent = m_Tree.m_nodes[n+1].m_Parent ;
    			m_Tree.m_nodes[n].m_ChildIsLeaf = m_Tree.m_nodes[n+1].m_ChildIsLeaf;
    			m_Tree.m_nodes[n].m_Child = m_Tree.m_nodes[n+1].m_Child;
    		}


    		m_Tree.m_nodes[k].m_Weight = nWeight;
    		m_Tree.m_nodes[k].m_Child = i;
    		m_Tree.m_nodes[k].m_ChildIsLeaf = NOCHILD;
    	}//for (i = m_Tree.m_NextFreeNode-2;j >= ROOT_NODE;i -= 2,j--)

    	for (i = m_Tree.m_NextFreeNode-1;i >= ROOT_NODE;i--) {
    		if (m_Tree.m_nodes[i].m_ChildIsLeaf == YESCHILD) {
    			k = m_Tree.m_nodes[i].m_Child;
    			m_Tree.m_Leaf[k] = i;
    		}else {
    			k = m_Tree.m_nodes[i].m_Child;
    			m_Tree.m_nodes[k].m_Parent = m_Tree.m_nodes[k+1].m_Parent = i;
    		}
    	}//for (i = m_Tree.m_NextFreeNode-1;i >= ROOT_NODE;i--)
    }

    private void UpdateModel(short shSymbol) {
    	short shCurrentNode;
    	short shNewNode;

    	if (m_Tree.m_nodes[ROOT_NODE].m_Weight == Tree.MAX_WEIGHT) {
    		RebuildTree();
    	}

    	shCurrentNode = (short)m_Tree.m_Leaf[shSymbol];

    	while (shCurrentNode != -1) {
    		m_Tree.m_nodes[shCurrentNode].m_Weight++;

    		for (shNewNode = shCurrentNode;shNewNode > ROOT_NODE;shNewNode--) {
    			if (m_Tree.m_nodes[shNewNode - 1].m_Weight >= m_Tree.m_nodes[shCurrentNode].m_Weight) {
    				break;
    			}
    		}

    		if (shCurrentNode != shNewNode) {
    			SwapNodes(shCurrentNode, shNewNode);
    			shCurrentNode = shNewNode;
    		}

    		shCurrentNode = (short)m_Tree.m_nodes[shCurrentNode].m_Parent;
    	}//while (shCurrentNode != -1)

    }

    private short InputBit(int[] anPos) {
    	
    	short shValue;

    	if (m_shMask == 0x0080) {
    		try {
    			byte byVal = (byte)m_InStm.read();

    			if (byVal < 0) {
    				m_shRack = (short)(256 + (int)byVal);
    			} else {
    				m_shRack = (short)byVal;
    			}

    			anPos[0]++;
    		}catch(IOException e) {
    			e.printStackTrace();
    		}
    	}

    	shValue = (short)(m_shRack & m_shMask);
    	m_shMask >>= 1;

		if (m_shMask == 0) {
			m_shMask = 0x0080;
		}

		return (short)(shValue != 0 ? 1 : 0);
    }

    private int InputBits(short shCount, int[] anPos) {
    	int nMask, nReturnValue = 0;

    	nMask = 1 << ( shCount - 1 );

    	try {
    		while (nMask != 0) {
    			if (m_shMask == 0x0080) {
    				byte byVal = (byte)m_InStm.read();

    				if (byVal < 0) {
    					m_shRack = (short)(256 + (int)byVal);
    				}else {
    					m_shRack = (short)byVal;
    				}

    				anPos[0]++;
    			}//if (m_shMask == 0x0080)

    			if ((m_shRack & m_shMask) != 0) {
    				nReturnValue |= nMask;
    			}

    			nMask >>= 1;
    			m_shMask >>= 1;

    			if (m_shMask == 0) {
    				m_shMask = 0x0080;
    			}
    		}//while (nMask != 0)
    	}catch(IOException e){
    		e.printStackTrace();
    	}

    	return nReturnValue;
    }

    private short DecodeSymbol(int[] anPos) {
    	short shCurrentNode;
    	short shSymbol = 0;

    	if (anPos[0] == m_nCmpBytes) {
    		return shSymbol;
    	}

    	shCurrentNode = ROOT_NODE;

    	while (m_Tree.m_nodes[shCurrentNode].m_ChildIsLeaf == NOCHILD) {
    		shCurrentNode = (short)m_Tree.m_nodes[shCurrentNode].m_Child;
    		shCurrentNode += (short)InputBit(anPos);
    	}//while (m_Tree.m_nodes[m_shCurrentNode].m_ChildIsLeaf == NOCHILD)

        shSymbol = (short)m_Tree.m_nodes[shCurrentNode].m_Child;

        if (shSymbol == ESCAPE) {
        	shSymbol = (short) InputBits((short)8, anPos);
        	AddNewNode(shSymbol);
        }//if (shSymbol == ESCAPE)

        return shSymbol;
    }

    protected void EnCode() {
    	int i;

    	if (m_OutStm == null) {
    		return;
    	}

        m_shMask = 0x0080;
        m_shRack = 0;
        InitializeTree();

        try {
        	for (i = 0; i < m_nRealBytes; i++) {
        		byte byVal = (byte)m_InStm.read();
        		short shVal;

        		if (byVal < 0)
        			shVal = (short)(256 + (int)byVal);
        		else
        			shVal = (short)byVal;

        		m_nCmpBytes += EncodeSymbol(shVal, m_nCmpBytes);
        		UpdateModel(shVal);
        	}//for (i = 0; i < nCount; i++)
        }catch(IOException e) {
        	e.printStackTrace();
        }

        m_Tree.m_Leaf[END_OF_STREAM] = -1;
        m_nCmpBytes += EncodeSymbol(END_OF_STREAM, m_nCmpBytes);
    }

    protected void DeCode() {
    	int i;
        short shSymbol = 0;
        int[] anPos = {0};

        m_shMask = 0x0080;
        m_shRack = 0;
        InitializeTree();

        try {
        	for (i = 0; i < m_nRealBytes; i++) {
        		shSymbol = DecodeSymbol(anPos);
        		if (shSymbol == END_OF_STREAM)
        			i = m_nRealBytes;
        		else {
        			m_OutStm.write((byte)shSymbol);
        			UpdateModel(shSymbol);
        		}
        	}//for (i = 0; i < nUnCompressCount; i++)
        }catch(IOException e) {
        	e.printStackTrace();
        }
    }

}