package org.xsl781.db.model;

import java.io.Serializable;

import org.xsl781.db.annotation.Column;


/**
 * 列结构
 * 
 * @date 2013-6-7上午1:17:49
 */
public class SQLiteColumn implements Serializable{
	private static final long serialVersionUID = 8822000632819424751L;

    @Column("cid")
	public long cid;
    @Column("name")
	public String name;
    @Column("type")
	public String type;
    @Column("notnull")
	public short notnull;
    @Column("dflt_value")
	public String dflt_value;
    @Column("pk")
	public short pk;
	
	@Override
	public String toString() {
		return "Column [cid=" + cid + ", name=" + name + ", type=" + type + ", notnull=" + notnull + ", dflt_value="
				+ dflt_value + ", pk=" + pk + "]";
	}
	
}
