package org.xsl781.db.assit;

import org.xsl781.db.impl.SQLStatement;
import org.xsl781.utils.Log;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * 辅助查询
 *
 * @author MaTianyu
 * @date 2013-6-15下午11:11:02
 */
public class Querier {
    private static final String TAG = Querier.class.getSimpleName();

    /**
     * 因为每个查询都不一样，但又有相同的结构，这种形式维持代码的统一性，也可以个性化解析。
     *
     * @param db
     * @param st
     * @param parser
     */
    public static void doQuery(SQLiteDatabase db, SQLStatement st, CursorParser parser) {
        if (Log.isDBPrint) Log.getLog(Querier.class).v("----> Query Start: " + st.toString());
        Cursor cursor = db.rawQuery(st.sql, (String[]) st.bindArgs);
        if (cursor != null) {
            parser.process(db, cursor);
            if (Log.isDBPrint) Log.getLog(Querier.class).v("<---- Query End , cursor size : " + cursor.getCount());
        } else {
            if (Log.isDBPrint) Log.getLog(Querier.class).v("<---- Query End : nothing find");
        }
    }

    /**
     * A simple cursor parser
     *
     * @author MaTianyu
     */
    public static abstract class CursorParser {
        public final void process(SQLiteDatabase db, Cursor cursor) {
            try {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    parseEachCursor(db, cursor);
                    cursor.moveToNext();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (cursor != null) cursor.close();
            }
        }

        public abstract void parseEachCursor(SQLiteDatabase db, Cursor c) throws Exception;
    }
}
