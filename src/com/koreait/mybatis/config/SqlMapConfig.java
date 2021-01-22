package com.koreait.mybatis.config;

import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class SqlMapConfig {
   //작성한 config.xml를 객체화하는 클래스
   private static SqlSessionFactory sqlsession;
   
   //클래스 초기화 블럭
   static {
      try {
         String resource = "./com/koreait/mybatis/config/config.xml";
         Reader reader = Resources.getResourceAsReader(resource);
         sqlsession = new SqlSessionFactoryBuilder().build(reader);
         reader.close();
         
      }catch(IOException e) {
         System.out.println(e);
         throw new RuntimeException("초기화 문제 발생, SqlMapConfig.java");
      }
   }
   
   public static SqlSessionFactory getSqlMapInstance() {
      return sqlsession;
   }
   
}