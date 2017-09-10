/*
 * DaoUtil.h
 *
 *  Created on: 2017年2月7日
 *      Author: chenmx
 */

#ifndef DATA_MYSQLUTIL_H_
#define DATA_MYSQLUTIL_H_
#include <mysql/mysql.h>

class MysqlUtil {
public:
	MysqlUtil();
	MysqlUtil(char* host, char* user, char* pwd, char* database);
	virtual ~MysqlUtil();

	bool init();
	void query(char* sql);
	bool close();

private:
	MYSQL *conn;
	char *server = "127.0.0.1"; //数据库地址
	char *user = "root"; //数据库用户名
	char *password = ""; //数据库密码
	char *database = "test"; //使用的库名
};

#endif /* DATA_MYSQLUTIL_H_ */
