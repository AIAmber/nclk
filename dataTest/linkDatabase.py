import MySQLdb
import re
import os
import time
import random
import datetime

databaseName = 'NCLK'
username = 'NCLK'
password = 'NCLK'
host = '192.168.1.147'
port = 3306

conn = MySQLdb.connect(host=host, port=port, user=username, passwd=password, db=databaseName)
cur = conn.cursor()

dbNum = cur.execute("SHOW DATABASES")
conn.commit()
print("conn success")
print(dbNum)