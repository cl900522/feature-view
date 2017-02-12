/*
 * DaoUtil.cpp
 *
 *  Created on: 2017年2月7日
 *      Author: chenmx
 */

#include "MysqlUtil.h"

#include <mysql/mysql.h>
#include <stdio.h>

MysqlUtil::MysqlUtil() {
}

MysqlUtil::MysqlUtil(char* host, char* user, char* pwd, char* database) {
    this->server = host;
    this->user = user;
    this->password = pwd;
    this->database = database;
}

MysqlUtil::~MysqlUtil() {
}

bool MysqlUtil::init() {
    conn = mysql_init(NULL);

    if (!mysql_real_connect(conn, server, user, password, database, 0, NULL,
            0)) {
        fprintf(stderr, "%s\n", mysql_error(conn));
        return -1;
    }

    return true;
}

void MysqlUtil::query(char* sql) {
    MYSQL_RES *res;
    MYSQL_ROW row;
    if (mysql_query(conn, sql)) {
        fprintf(stderr, "%s\n", mysql_error(conn));
    }
    res = mysql_use_result(conn);
    printf("MySQL Tables in mysql database:\n");

    while ((row = mysql_fetch_row(res)) != NULL) {
        printf("%s \n", row[0]);
    }

    mysql_free_result(res);
}

bool MysqlUtil::close() {
    mysql_close(conn);
}
