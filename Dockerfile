FROM mysql

ADD initdb.sql /docker-entrypoint-initdb.d

EXPOSE 3306