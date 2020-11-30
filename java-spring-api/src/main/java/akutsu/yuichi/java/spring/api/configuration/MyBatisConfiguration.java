package akutsu.yuichi.java.spring.api.configuration;

import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * Mybatis用のデータベース接続設定.
 */
@Configuration
@MapperScan("akutsu.yuichi.java.spring.api.mapper")
@PropertySource({"classpath:database.properties"})
public class MyBatisConfiguration {

  @Value("${database.driver}")
  String driver;
  @Value("${database.url}")
  String url;
  @Value("${database.user}")
  String user;
  @Value("${database.password}")
  String password;

  /**
   * データベース接続情報
   *
   * @return データベース接続情報
   */
  @Bean
  DataSource dataSource() {
    // プロパティに定義した接続情報を設定
    PooledDataSource dataSource = new PooledDataSource();
    dataSource.setDriver(driver);
    dataSource.setUrl(url);
    dataSource.setUsername(user);
    dataSource.setPassword(password);
    // コネクションプールに３コネクションを保持
    dataSource.setPoolMaximumActiveConnections(3);
    // オートコミットをオフ(例外発生時にロールバックする)
    dataSource.setDefaultAutoCommit(false);
    // コネクションがDBから切断されない用にダミーSQLを10秒毎に実行
    dataSource.setPoolPingEnabled(true);
    dataSource.setPoolPingConnectionsNotUsedFor(10 * 1000);
    dataSource.setPoolPingQuery("select 1;");
    return dataSource;
  }

  /**
   * トランザクション管理
   *
   * @param dataSource データベース接続情報
   * @return トランザクション管理
   */
  @Bean
  DataSourceTransactionManager dataSourceTransactionManager(DataSource dataSource) {
    DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
    dataSourceTransactionManager.setDataSource(dataSource);
    dataSourceTransactionManager.setRollbackOnCommitFailure(true);
    return dataSourceTransactionManager;
  }

  /**
   * Mybatis設定
   *
   * @param dataSource データベース接続情報
   * @return Mybatis設定
   */
  @Bean
  ConfigurationCustomizer mybatisConfigurationCustomizer(DataSource dataSource) {
    return (org.apache.ibatis.session.Configuration configuration) -> {
      // デフォルト設定として生成
      JdbcTransactionFactory jdbcTransactionFactory = new JdbcTransactionFactory();
      Environment environment = new Environment("default", jdbcTransactionFactory, dataSource);
      configuration.setEnvironment(environment);
      configuration.setDatabaseId("default");
      // Entityクラスのフィールド名(キャメルケース)とDBカラム名(スネークケース)を自動的にマッピングするように設定
      configuration.setMapUnderscoreToCamelCase(true);
    };
  }
}
