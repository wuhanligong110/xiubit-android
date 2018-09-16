package org.xsl781.db.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 冲突策略
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Conflict {
    Strategy value();

    enum Strategy {
        ROLLBACK(" ROLLBACK "),
        ABORT(" ABORT "),
        FAIL(" FAIL "),
        IGNORE(" IGNORE "),
        REPLACE(" REPLACE ");

        Strategy(String sql) {
            this.sql = sql;
        }

        public String sql;

        public String getSql() {
            return sql;
        }
    }
}
