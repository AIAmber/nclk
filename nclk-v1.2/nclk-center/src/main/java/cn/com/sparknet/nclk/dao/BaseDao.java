package cn.com.sparknet.nclk.dao;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.Reader;
import java.io.Serializable;
import java.lang.reflect.Proxy;
import java.sql.Blob;
import java.sql.Clob;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.engine.jdbc.SerializableBlobProxy;
import org.hibernate.engine.jdbc.SerializableClobProxy;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import cn.com.sparknet.nclk.util.InputStreamUtil;

/**
 * Base Dao
 * @author chenxy
 *
 */
@Repository
public class BaseDao {
	
	@Resource
	private SessionFactory sessionFactory;
	private String dialect=null;
	
	/**
	 * 获取Hibernate方言
	 * @return
	 */
	public String getDialect(){
		if (dialect == null) {
			synchronized(BaseDao.class){
				if (dialect == null) {
					SessionFactoryImplementor sfi=(SessionFactoryImplementor)this.sessionFactory;
					dialect=sfi.getDialect().getClass().getName();
					if(dialect.indexOf("Oracle")>-1){
						dialect="Oracle";
					}else if(dialect.indexOf("MySQL")>-1){
						dialect="MySQL";
					}else if(dialect.indexOf("SQLServer")>-1){
						dialect="SQLServer";
					}else if(dialect.indexOf("DB2")>-1){
						dialect="DB2";
					}else if(dialect.indexOf("Sybase")>-1){
						dialect="Sybase";
					}else if(dialect.indexOf("PostgreSQL")>-1){
						dialect="PostgreSQL";
					}
				}
			}
		}
		return dialect;
	}
	
	/**
	 * 获取默认SessionFactory
	 * @return
	 */
	public SessionFactory getSessionFactory() {
		return this.sessionFactory;
	}
	
	/**
	 * 获取默认Session
	 * @return
	 */
	public Session getSession() {
		Session session=null;
		try {
			session=this.sessionFactory.getCurrentSession();
		} catch (HibernateException e) {
			throw new RuntimeException(e.getMessage(),e);
		}
		return session;
	}
	
	/**
	 * 获取自定义Session
	 * @param sessionFactory
	 * @return
	 */
	public Session getSession(SessionFactory sessionFactory) {
		Session session=null;
		try {
			session=sessionFactory.getCurrentSession();
		} catch (HibernateException e) {
			throw new RuntimeException(e.getMessage(),e);
		}
		return session;
	}
	
	/**
	 * 创建SQLQuery
	 * @param sql
	 * @param params
	 * @return
	 */
	public SQLQuery createSQLQuery(String sql,Object[]... params) {
		SQLQuery sqlQuery=null;
		try {
			sqlQuery=this.getSession().createSQLQuery(sql);
			this.setParameter(sqlQuery, params);
		} catch (HibernateException e) {
			throw new RuntimeException(e.getMessage(),e);
		}
		return sqlQuery;
	}
	
	/**
	 * 创建Query
	 * @param hql
	 * @param params
	 * @return
	 */
	public Query createQuery(String hql,Object[]... params) {
		Query query=null;
		try {
			query=this.getSession().createQuery(hql);
			this.setParameter(query, params);
		} catch (HibernateException e) {
			throw new RuntimeException(e.getMessage(),e);
		}
		return query;
	}
	
	/**
	 * 设置预编译参数
	 * @param query
	 * @param params
	 */
	private void setParameter(Query query,Object[]... params) {
		try {
			if(null!=params&&params.length>0){
				Object[] object=params[0];
				for(int i=0;i<object.length;i++){
					query.setParameter(i, object[i]);
				}
			}
		} catch (HibernateException e) {
			throw new RuntimeException(e.getMessage(),e);
		}
	}
	
	/**
	 * 判断是否是数字
	 * @param start
	 * @param limit
	 * @return
	 */
	public boolean isNumeric(String start,String limit){
		boolean isNumeric=true;
		try {
			if(StringUtils.isEmpty(start)||StringUtils.isEmpty(limit)){
				isNumeric=false;
			}else{
				if(!StringUtils.isNumeric(start)||!StringUtils.isNumeric(limit)){
					isNumeric=false;
				}
			}
		} catch (HibernateException e) {
			throw new RuntimeException(e.getMessage(),e);
		}
		return isNumeric;
	}
	
	/************************************************SQL操作******************************************************/
	
	/**
	 * 查询记录数
	 * @param sql
	 * @param params
	 * @return
	 */
	public int findIntBySql(String sql,Object[]... params) {
		int count = 0;
		try {
			count = ((Number) this.createSQLQuery(sql,params).uniqueResult()).intValue();
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		return count;
	}
	
	
	/**
	 * 查询Map
	 * @param sql
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String,Object> findMapBySql(String sql,Object[]... params) {
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			SQLQuery sqlQuery = this.createSQLQuery(sql,params);
			Query query = sqlQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			Object object = query.uniqueResult();
			if(null!=object){
				map=(HashMap<String, Object>) object;
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		return map;
	}
	
	/**
	 * 查询List
	 * @param sql
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> findListBySql(String sql,Object[]... params) {
		List<Map<String,Object>> list = null;
		try {
			SQLQuery sqlQuery = this.createSQLQuery(sql,params);
			Query query = sqlQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			list=(List<Map<String,Object>>) query.list();
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		return list;
	}
	
	/**
	 * 查询Clob
	 * @param sql
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<String> findClobBySql(String sql,Object[]... params) {
		List<String> list=null;
		try {
			list=new ArrayList<String>();
			List<Object> objectList=this.createSQLQuery(sql, params).list();
			for(Object obj : objectList){
				if(null!=obj){
					String classTypeName=obj.getClass().getSimpleName();
					if(classTypeName.indexOf("Proxy")>-1){//代理类
						SerializableClobProxy proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj);
						Clob clob=proxy.getWrappedClob();
						Reader reader=clob.getCharacterStream();
						BufferedReader bufferedReader=new BufferedReader(reader);
		        	    StringBuilder stringBuilder=new StringBuilder();
		        	    String line;
		        	    while((line=bufferedReader.readLine())!=null) {
		        	    	stringBuilder.append(line);
		        	    	stringBuilder.append("\r\n");
		        	    }
		        	    list.add(stringBuilder.toString());
		        	    if(null!=bufferedReader){
		        	    	bufferedReader.close();
		        	    	bufferedReader=null;
		        	    }
		        	    if(null!=reader){
		        	    	reader.close();
		        	    	reader=null;
		        	    }
					}else{//String
						list.add(obj.toString());
					}
					
				}else{
					list.add("");
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		return list;
	}
	
	/**
	 * 查询Blob
	 * @param sql
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<InputStream> findBlobBySql(String sql,Object[]... params) {
		List<InputStream> list=null;
		try {
			list=new ArrayList<InputStream>();
			List<Object> objectList=this.createSQLQuery(sql, params).list();
			for(Object obj : objectList){
				if(null!=obj){
					String classTypeName=obj.getClass().getSimpleName();
					if(classTypeName.indexOf("Proxy")>-1){//代理类
						SerializableBlobProxy proxy = (SerializableBlobProxy)Proxy.getInvocationHandler(obj);
						Blob blob=proxy.getWrappedBlob();
						list.add(blob.getBinaryStream());
					}else{//byte[]
						list.add(InputStreamUtil.byteToInputStream((byte[])obj));
					}
				}else{
					list.add(null);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		return list;
	}
	
	/**
	 * 查询分页（带COUNT总数）
	 * @param sql
	 * @param start
	 * @param limit
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Page findPageAllBySql(String sql,String start,String limit,Object[]... params) {
		int count=0;
		Page page=new Page();
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		try {
			if(this.isNumeric(start, limit)){
				count=this.findIntBySql("SELECT COUNT(*) FROM ("+sql+") TMP", params);
				SQLQuery sqlQuery = this.createSQLQuery(sql,params);
				sqlQuery.setFirstResult(Integer.parseInt(start));
				sqlQuery.setMaxResults(Integer.parseInt(limit));
				Query query = sqlQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				list=(List<Map<String,Object>>) query.list();
			}
			page.setCount(count);
			page.setList(list);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		return page;
	}
	
	/**
	 * 查询分页（不带COUNT总数）
	 * @param sql
	 * @param start
	 * @param limit
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> findPageListBySql(String sql,String start,String limit,Object[]... params) {
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		try {
			if(this.isNumeric(start, limit)){
				SQLQuery sqlQuery = this.createSQLQuery(sql,params);
				sqlQuery.setFirstResult(Integer.parseInt(start));
				sqlQuery.setMaxResults(Integer.parseInt(limit));
				Query query = sqlQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				list=(List<Map<String,Object>>) query.list();
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		return list;
	}
	
	/**
	 * 更新
	 * @param sql
	 * @param params
	 * @return
	 */
	public int updateBySql(String sql,Object[]... params) {
		int count = 0;
		try {
			count = this.createSQLQuery(sql,params).executeUpdate();
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		return count;
	}
	
	/************************************************HQL操作******************************************************/

	/**
	 * 查询记录数
	 * @param hql
	 * @param params
	 * @return
	 */
	public int findIntByHql(String hql,Object[]... params) {
		int count = 0;
		try {
			count = ((Number) this.createQuery(hql,params).uniqueResult()).intValue();
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		return count;
	}
	
	/**
	 * 查询Object
	 * @param hql
	 * @param params
	 * @return
	 */
	public Object findObjectByHql(String hql,Object[]... params) {
		Object object = new Object();
		try {
			Query query = this.createQuery(hql,params);
			object = query.uniqueResult();
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		return object;
	}
	
	/**
	 * 查询Map
	 * @param hql
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String,Object> findMapByHql(String hql,Object[]... params) {
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			Query query = this.createQuery(hql,params);
			Object object = query.uniqueResult();
			if(null!=object){
				map=(HashMap<String, Object>) object;
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		return map;
	}
	
	/**
	 * 查询List
	 * @param hql
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> findListByHql(String hql,Object[]... params) {
		List<Map<String,Object>> list = null;
		try {
			Query query = this.createQuery(hql,params);
			list=(List<Map<String,Object>>) query.list();
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		return list;
	}
	
	/**
	 * 查询分页（带COUNT总数）
	 * @param hql
	 * @param start
	 * @param limit
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Page findPageAllByHql(String hql,String start,String limit,Object[]... params) {
		int count=0;
		Page page=new Page();
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		try {
			if(this.isNumeric(start, limit)){
				Query query = this.createQuery(hql,params);
				count=query.list().size();
				query.setFirstResult(Integer.parseInt(start));
				query.setMaxResults(Integer.parseInt(limit));
				list=(List<Map<String,Object>>) query.list();
			}
			page.setCount(count);
			page.setList(list);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		return page;
	}
	
	/**
	 * 查询分页（不带COUNT总数）
	 * @param hql
	 * @param start
	 * @param limit
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> findPageListByHql(String hql,String start,String limit,Object[]... params) {
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		try {
			if(this.isNumeric(start, limit)){
				Query query = this.createQuery(hql,params);
				query.setFirstResult(Integer.parseInt(start));
				query.setMaxResults(Integer.parseInt(limit));
				list=(List<Map<String,Object>>) query.list();
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		return list;
	}
	
	/**
	 * 更新
	 * @param hql
	 * @param params
	 * @return
	 */
	public int updateByHql(String hql,Object[]... params) {
		int count = 0;
		try {
			count = this.createQuery(hql,params).executeUpdate();
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		return count;
	}
	
	/**********************************************Criteria操作***************************************************/
	
	/**
	 * 创建Criteria
	 * @param clazz
	 * @param alias
	 * @return
	 */
	public Criteria createCriteria(Class<?> clazz,String... alias) {
		Criteria criteria = null;
		try {
			if(alias.length>0){
				criteria = this.getSession().createCriteria(clazz, alias[0]);
			}else{
				criteria = this.getSession().createCriteria(clazz);
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		return criteria;
	}
	
	/**
	 * 查询（即时加载）
	 * @param clazz
	 * @param serializable
	 * @return
	 */
	public Object get(Class<?> clazz, Serializable serializable) {
		Object entity = null;
		try {
			entity = (Object) this.getSession().get(clazz, serializable);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		return entity;
	}
	
	/**
	 * 查询（延迟加载）
	 * @param clazz
	 * @param serializable
	 * @return
	 */
	public Object load(Class<?> clazz, Serializable serializable) {
		Object entity = null;
		try {
			entity = (Object) this.getSession().load(clazz, serializable);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		return entity;
	}
	
	/**
	 * 保存
	 * @param entity
	 * @return
	 */
	public Serializable save(Object entity) {
		Serializable serializable = null;
		try {
			serializable = this.getSession().save(entity);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		return serializable;
	}

	/**
	 * 更新
	 * @param entity
	 */
	public void update(Object entity) {
		try {
			this.getSession().update(entity);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/**
	 * 保存或更新
	 * @param entity
	 */
	public void saveOrUpdate(Object entity) {
		try {
			this.getSession().saveOrUpdate(entity);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/**
	 * 删除
	 * @param entity
	 */
	public void delete(Object entity) {
		try {
			this.getSession().delete(entity);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
}
