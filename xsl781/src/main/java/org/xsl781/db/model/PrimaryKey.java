package org.xsl781.db.model;


import java.lang.reflect.Field;

/**
 * 主键
 * 
 * 
 * @date 2013-6-9上午1:09:33
 */
public class PrimaryKey extends Property {
    private static final long serialVersionUID = 2304252505493855513L;

    public org.xsl781.db.annotation.PrimaryKey.AssignType assign;

    public PrimaryKey(Property p, org.xsl781.db.annotation.PrimaryKey.AssignType assign) {
        this(p.column, p.field, assign);
    }

    public PrimaryKey(String column, Field field, org.xsl781.db.annotation.PrimaryKey.AssignType assign) {
		super(column, field);
		this.assign = assign;
	}

	public boolean isAssignedBySystem() {
		return assign == org.xsl781.db.annotation.PrimaryKey.AssignType.AUTO_INCREMENT;
	}

	public boolean isAssignedByMyself() {
		return assign == org.xsl781.db.annotation.PrimaryKey.AssignType.BY_MYSELF;
	}
}
