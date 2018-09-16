package org.xsl781.db;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.xsl781.db.assit.QueryBuilder;
import org.xsl781.db.impl.SQLStatement;
import org.xsl781.db.model.ColumnsValue;
import org.xsl781.db.model.ConflictAlgorithm;
import org.xsl781.db.model.Relation;

import android.database.sqlite.SQLiteDatabase;

/**
 * data base operation
 *
 * 
 * @date 2013-6-2上午2:37:56
 */
public interface DataBase {
    /**
     * Execute this SQL statement, if it is not a SELECT / INSERT / DELETE / UPDATE, for example
     * CREATE / DROP table, view, trigger, index etc.
     */
    void execute(SQLiteDatabase db, SQLStatement statement);

    /**
     * save: insert or update a single entity
     *
     * @return the number of rows affected by this SQL statement execution.
     */
    long save(Object entity);

    /**
     * save: insert or update a collection
     *
     * @return the number of affected rows
     */
    int save(Collection<?> collection);

    /**
     * insert a single entity
     *
     * @return the number of rows affected by this SQL statement execution.
     */
    long insert(Object entity);


    /**
     * insert a single entity with conflict algorithm
     *
     * @return the number of rows affected by this SQL statement execution.
     */
    long insert(Object entity, ConflictAlgorithm conflictAlgorithm);

    /**
     * insert a collection
     *
     * @return the number of affected rows
     */
    int insert(Collection<?> collection);

    /**
     * insert a collection with conflict algorithm
     *
     * @return the number of affected rows
     */
    int insert(Collection<?> collection, ConflictAlgorithm conflictAlgorithm);

    /**
     * update a single entity
     *
     * @return the number of affected rows
     */
    int update(Object entity);

    /**
     * update a single entity with conflict algorithm
     *
     * @return the number of affected rows
     */
    int update(Object entity, ConflictAlgorithm conflictAlgorithm);

    /**
     * update a single entity with conflict algorithm, and only update columns in {@link ColumnsValue}
     * if param {@link ColumnsValue} is null, update all columns.
     *
     * @return the number of affected rows
     */
    int update(Object entity, ColumnsValue cvs, ConflictAlgorithm conflictAlgorithm);

    /**
     * update a collection
     *
     * @return the number of affected rows
     */
    int update(Collection<?> collection);

    /**
     * update a collection with conflict algorithm
     *
     * @return the number of affected rows
     */
    int update(Collection<?> collection, ConflictAlgorithm conflictAlgorithm);

    /**
     * update a collection with conflict algorithm, and only update columns in {@link ColumnsValue}
     * if param {@link ColumnsValue} is null, update all columns.
     *
     * @return the number of affected rows
     */
    int update(Collection<?> collection, ColumnsValue cvs, ConflictAlgorithm conflictAlgorithm);

    /**
     * delete a single entity
     *
     * @return the number of affected rows
     */
    int delete(Object entity);

    /**
     * delete all rows
     *
     * @return the number of affected rows
     */
    int deleteAll(Class<?> claxx);

    /**
     * <b>start must >=0 and smaller than end</b>
     * <p>delete from start to the end, <b>[start,end].</b>
     * <p>set end={@link Integer#MAX_VALUE} will delete all rows from the start
     *
     * @return the number of affected rows
     */
    int delete(Class<?> claxx, long start, long end, String orderAscColu);

    /**
     * delete a collection
     *
     * @return the number of affected rows
     */
    int delete(Collection<?> collection);

    /**
     * query count of table rows and return
     *
     * @return the count of query result
     */
    long queryCount(Class<?> claxx);

    /**
     * query count of your sql query result rows and return
     *
     * @return the count of query result
     */
    long queryCount(QueryBuilder qb);

    /**
     * custom query
     *
     * @return the query result list
     */
    <T> ArrayList<T> query(QueryBuilder qb);

    /**
     * query entity by long id
     *
     * @return the query result
     */
    <T> T queryById(long id, Class<T> clazz);

    /**
     * query entity by string id
     *
     * @return the query result
     */
    <T> T queryById(String id, Class<T> clazz);

    /**
     * query all data of this type
     *
     * @return the query result list
     */
    <T> ArrayList<T> queryAll(Class<T> claxx);

    /**
     * find and return relation between two diffirent collection.
     *
     * @return the relation list of class1 and class2;
     */
    ArrayList<Relation> queryRelation(Class class1, Class class2, List<String> key1List, List<String> key2List);

    /**
     * auto entity relation mapping
     */
    <E, T> boolean mapping(Collection<E> col1, Collection<T> col2);

    /**
     * 获取可读数据库操作对象
     */
    SQLiteDatabase getReadableDatabase();

    /**
     * 获取可写数据库操作对象
     */
    SQLiteDatabase getWritableDatabase();

    /**
     * 获取表管理对象
     */
    TableManager getTableManager();

    /**
     * 关闭数据库，清空缓存。
     */
    void close();
}
